/*
 * NaiveAlgorithm.java
 * 
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 3 of the License, or (at your
 * option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, see <http://www.gnu.org/licenses/>.
 */

package de.uni_leipzig.informatik.pcai042.boa.searcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;
import de.uni_leipzig.informatik.pcai042.boa.manager.BoaSentence;

public class NaiveAlgorithm extends SearchAlgorithm
{
	/**
	 * A set of certain symbols or words that can belong to a number. (such as
	 * "^" in 10^3)
	 */
	private final Set<String> mathSymbols;
	
	/**
	 * A Set of Strings that can be associated to a number.
	 */
	private final Set<String> numbers;
	
	/**
	 * String patterns that are searched for.
	 */
	private final Set<String> surForms;
	
	public NaiveAlgorithm(HashMap<String, Set<String>> config, String annoType)
	{
		super(config, annoType);
		
		mathSymbols = config.get("MATH");
		numbers = config.get("NUMBERS");
		surForms = new HashSet<String>();
		Iterator<String> it = config.keySet().iterator();
		while (it.hasNext())
		{
			String key = it.next();
			if (!key.equals("MATH") && !key.equals("NUMBERS"))
			{
				Iterator<String> setIt = config.get(key).iterator();
				while (setIt.hasNext())
					surForms.add(setIt.next().toLowerCase().replaceAll("\\s", ""));
			}
		}
	}
	
	@Override
	/**
	 * Searches a given BoaSentence for a given Set of String Patterns combined with a number.
	 * The Algorithm can find patterns like
	 * 
	 * 		10 meter
	 *		1.6x10^3 kg
	 *		15lbs
	 *		15 feet 6 inch
	 *		5 million k i l o m e t r e s
	 *		22 � C
	 *		20 - 30 mm
	 *
	 * provided that the non-numerical tokens (here: meter, kg, lbs, feet, inch, kilometres, �C, mm) are elements
	 * of the Set<String> surForms and the mathematical expressions (here: x, ^, million, -) are elements
	 * of the Set<String> mathSymbols.
	 * 
	 * Patterns that consist of 2 sub patterns (like 15 feet 6 inch) have to be stored in surForms as one 
	 * element separated by "&&" (e.g. feet&&inch).
	 * 
	 * It is important to use a different instance of NaiveAlgorithm for each annotation type! as it will use the same
	 * label for any BoaAnnotation created.
	 */
	public void search(BoaSentence sentence)
	{
		String currentToken = "", dsfNumber = "", dsfUnit = "", dsfTemp = "";
		String match, suffix, prefix, part;
		String[] parts;
		int position, positionDsf, count = 0;
		Boolean replaced = false; // need this when a token representing a
									// number is replaced with another token
									// representing a number
									// replacement isn't actually needed but
									// makes algorithm faster
		
		Iterator<String> tokenIt = sentence.iterator();
		
		while (tokenIt.hasNext())
		{
			if (!replaced)
			{
				currentToken = tokenIt.next();
				count++;
			}
			// System.out.println(currentToken + " " + count);
			
			replaced = false;
			
			// Step one a): Check if any token is a number.
			if (this.checkIfNumber(currentToken))
			{
				// Step two a): Find the corresponding unit. It can be assumed
				// that (if it exists) it will be
				// located in the part of the sentence right from the numerical
				// value.
				Iterator<String> findMatch = sentence.iterator();
				
				// go to first token after currentToken
				for (int i = 0; i < count; i++)
				{
					if (findMatch.hasNext())
						match = findMatch.next();
				}
				
				// for each token right from currentToken
				while (findMatch.hasNext())
				{
					match = findMatch.next();
					
					// if it is a number there wont be any Annotations based on
					// currentToken as
					// any further occurring units will be related to match
					// instead
					if (this.checkIfNumber(match))
					{
						while (!currentToken.equals(match))
						{
							if (!tokenIt.hasNext())
								break;
							currentToken = tokenIt.next();
							count++;
							replaced = true;
						}
						break;
					}
					// else check if match is a mathematical symbol
					else if (mathSymbols.contains(match.toLowerCase()))
					{
						StringBuilder combinedNumber = new StringBuilder();
						combinedNumber.append(currentToken);
						combinedNumber.append(";" + match);
						
						while (findMatch.hasNext())
						{
							part = findMatch.next();
							
							if (checkIfNumber(part) || mathSymbols.contains(part))
							{
								combinedNumber.append(";" + part);
							} else
							{
								match = part;
								while (!tokenIt.next().equals(match))
									count++;
								
								currentToken = combinedNumber.toString();
								count++;
								replaced = true;
								
								break;
							}
						}
					}
					
					// check if match is part of a combined surface form
					// this has to be checked first, else there might occur 2
					// Annotations
					// for same label, 1 for uncombined and 1 for combined
					if (this.isPrefix(match + "&&", surForms))
					{
						Iterator<String> dsfFinder = sentence.iterator();
						
						for (int j = 0; j < count + 2; j++)
						{
							if (dsfFinder.hasNext())
								dsfNumber = dsfFinder.next();
						}
						
						if (this.checkIfNumber(dsfNumber) && dsfFinder.hasNext())
						{
							dsfUnit = dsfFinder.next();
							
							if (surForms.contains(match + "&&" + dsfUnit))
							{
								// make Annotation
								ArrayList<String> annoTokens = new ArrayList<String>();
								parts = currentToken.split(";");
								
								for (int i = 0; i < parts.length; i++)
								{
									annoTokens.add(parts[i]);
								}
								
								annoTokens.add(match);
								annoTokens.add(dsfNumber);
								annoTokens.add(dsfUnit);
								
								sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
								
								currentToken = tokenIt.next();
								currentToken = tokenIt.next();
								count += 2;
								
								break;
							}
						}
					}
					
					// checks if match is equal to any known surface form of the
					// units we look for
					if (surForms.contains(match.toLowerCase()))
					{
						// create Annotation
						ArrayList<String> annoTokens = new ArrayList<String>();
						parts = currentToken.split(";");
						
						for (int i = 0; i < parts.length; i++)
						{
							annoTokens.add(parts[i]);
						}
						
						annoTokens.add(match);
						
						sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
						break;
						
						// this is for tests only
						// System.out.println("Annotation: " + currentToken +
						// " " + match + " Type: " + annoType);
					}
					// else check if match is the prefix of any surface form
					else if (this.isPrefix(match.toLowerCase(), surForms))
					{
						ArrayList<String> annoTokens = new ArrayList<String>();
						parts = currentToken.split(";");
						
						for (int i = 0; i < parts.length; i++)
						{
							annoTokens.add(parts[i]);
						}
						
						annoTokens.add(match);
						
						StringBuilder stringBuilder = new StringBuilder();
						stringBuilder.append(match);
						
						while (this.isPrefix(stringBuilder.toString().toLowerCase(), surForms))
						{
							// if yes, melt with next token (if it exists) and
							// check again
							if (!findMatch.hasNext())
								break;
							suffix = findMatch.next();
							annoTokens.add(suffix);
							stringBuilder.append(suffix);
							
							if (surForms.contains(stringBuilder.toString().toLowerCase()))
							{
								// create Annotation
								sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
								
								while (!currentToken.equals(suffix))
								{
									if (!tokenIt.hasNext())
										break;
									currentToken = tokenIt.next();
									count++;
								}
								
								break;
							}
						}
					} else
						break;
				}
			} else
			{
				// Step one b): Check if same token starts with number instead
				position = this.checkIfStartsWithNumber(currentToken);
				
				if (position != -1)
				{
					// Step two b): Split token in prefix (number) and suffix
					// (word) and test if suffix is a surface form of the
					// searched units
					prefix = currentToken.substring(0, position - 1);
					suffix = currentToken.substring(position - 1);
					
					// checking if combined surface form
					if (this.isPrefix(suffix + "&&", surForms))
					{
						Iterator<String> dsfFinder = sentence.iterator();
						
						for (int j = 0; j < count + 1; j++)
						{
							if (dsfFinder.hasNext())
								dsfNumber = dsfFinder.next();
						}
						
						if (this.checkIfNumber(dsfNumber) && dsfFinder.hasNext())
						{
							dsfUnit = dsfFinder.next();
							
							if (surForms.contains(suffix + "&&" + dsfUnit))
							{
								// make Annotation
								ArrayList<String> annoTokens = new ArrayList<String>();
								
								annoTokens.add(prefix+suffix);
								//annoTokens.add(suffix);
								annoTokens.add(dsfNumber);
								annoTokens.add(dsfUnit);
								
								sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
								break;
							}
						} else
						{
							positionDsf = this.checkIfStartsWithNumber(dsfNumber);
							if (positionDsf != -1)
							{
								dsfTemp = dsfNumber;
								dsfNumber = dsfTemp.substring(0, positionDsf - 1);
								dsfUnit = dsfTemp.substring(positionDsf - 1);
								
								if (surForms.contains(suffix + "&&" + dsfUnit))
								{
									// make Annotation
									ArrayList<String> annoTokens = new ArrayList<String>();
									
									annoTokens.add(prefix+suffix);
									//annoTokens.add(suffix);
									annoTokens.add(dsfNumber+dsfUnit);
									//annoTokens.add(dsfUnit);
									
									sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
									break;
								}
							}
						}
					}
					// checking if ordinary surface form
					if (surForms.contains(suffix.toLowerCase()))
					{
						// make Annotation
						ArrayList<String> annoTokens = new ArrayList<String>();
						annoTokens.add(prefix+suffix);
						//annoTokens.add(suffix);
						
						sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
					}
				}
			}
		}
		
		return;
	}
	
	/**
	 * Checks if a String representation of a token is a number
	 * 
	 * @param token
	 * @return true if it can be parsed to an integer or double value, else
	 *         false
	 */
	public boolean checkIfNumber(String token)
	{
		try
		{
			if (token.endsWith("f"))
				return numbers.contains(token); // else f at end of String is
												// interpreted as "float"
			if (token.contains(","))
				token = token.replace(",", ".");
			Double.parseDouble(token);
			return true;
		} catch (NumberFormatException e)
		{
		}
		return numbers.contains(token.toLowerCase());
	}
	
	/**
	 * Checks if a String representation of a token starts with a number.
	 * 
	 * @param token
	 * @return -1 if no combined number else position of first non-numerical
	 *         char
	 */
	public int checkIfStartsWithNumber(String token)
	{
		String prefix;
		int length = 1;
		int position = -1;
		
		try
		{
			while (length < token.length())
			{
				prefix = token.substring(0, length);
				length++;
				
				if (checkIfNumber(prefix))
					position = length;
			}
		} catch (NumberFormatException e)
		{
		}
		
		return position;
	}
	
	/**
	 * Checks if a token is a prefix of any word in a certain set of words
	 * 
	 * @param token
	 * @param words
	 * @return true if prefix, else false
	 */
	public boolean isPrefix(String token, Set<String> words)
	{
		Iterator<String> it = words.iterator();
		String nextWord;
		
		while (it.hasNext())
		{
			nextWord = it.next();
			if (nextWord.length() > token.length())
			{
				if (token.equals(nextWord.substring(0, token.length())))
					return true;
			}
		}
		
		return false;
	}
}
