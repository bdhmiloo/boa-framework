/*
 * SearchAlgorithm.java
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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaSentence;
import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;
import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation.Type;

/**
 * Abstract. Searches BoaSentence for patterns related to the elements
 * of the Set<String> surForms. Also provides it subclasses with auxiliary
 * functions to detect String representations of numbers.
 * @author Jakob M.
 *
 */

public abstract class SearchAlgorithm
{
	/**
	 * String patterns that are searched for.
	 */
	
	protected Set<String> surForms;
	
	/**
	 * The label found patterns get when annotated. 
	 */
	
	protected Type annoType;
	
	/**
	 * A Set of Strings that can be associated to a number.
	 */
	
	private Set<String> numbers;
	
	/**
	 * Constructor. Does NOT initialize all attributes necessary to 
	 * successfully use the class! (annoType and surForms still need to be set
	 * after this)
	 */
	
	public SearchAlgorithm()
	{
		numbers = new ConfigLoader().openConfigSurfaceForms("NUMBERS");
	}
	
	/**
	 * Constructor. Initializes all attributes.
	 * @param surfaceForms Set of String patterns to search for 
	 * @param annoType type of the BoaAnnotations that are added
	 */
	
	public SearchAlgorithm(Set<String> surfaceForms, Type annoType)
	{
		try
		{
			this.annoType = annoType;
			surForms = new HashSet<String>();
			surForms.addAll(surfaceForms);
			numbers = new ConfigLoader().openConfigSurfaceForms("NUMBERS");
		}
		catch(NullPointerException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Searches sentence for occurrences of certain patterns.
	 * The nature of this patterns is defined in each subclass.
	 * On success objects of type BoaAnnotation will be added to sentence.
	 * @param sentence
	 */
	//subclasses will have to overwrite this
	public abstract void search(BoaSentence sentence);
	
	/**
	 * Checks if a String representation of a token is a number
	 * @param token
	 * @return true if it can be parsed to an integer or double value, else false
	 */
	
	
	public boolean checkIfNumber(String token)
	{	
		try
		{
			if(token.endsWith("f")) return numbers.contains(token); //else f at end of String is interpreted as "float"
			if(token.contains(",")) token = token.replace(",", ".");
			Double.parseDouble(token);
			return true;
		}
		catch(NumberFormatException e){}
				
		return numbers.contains(token.toLowerCase());
	}
	
	
	/**
	 * Checks if a String representation of a token starts with a number.
	 * @param token
	 * @return -1 if no combined number else position of first non-numerical char
	 */
	
	public int checkIfStartsWithNumber(String token)
	{
		String prefix;
		int length = 1;
		int position = -1;
		
		try
		{
			while(length < token.length())
			{
				prefix = token.substring(0, length);
				length++;
				
				if(checkIfNumber(prefix)) position = length;
			}
		}
		catch(NumberFormatException e){}
		
		return position;
	}
	
	/**
	 * Checks if a token is a prefix of any word in a certain set of words
	 * @param token
	 * @param words
	 * @return true if prefix, else false
	 */
	
	public boolean isPrefix(String token, Set<String> words)
	{
		Iterator<String> it = words.iterator();
		String nextWord;
		
		while(it.hasNext())
		{
			nextWord = it.next();
			if(nextWord.length() > token.length())
			{
				if(token.equals(nextWord.substring(0, token.length()))) return true;
			}
		}
		
		return false;
	}

	public void setSurForms(Set<String> surfaceForms)
	{
		surForms = new HashSet<String>();
		surForms.addAll(surfaceForms);
	}

	public void setAnnoType(Type annoType2)
	{
		annoType = annoType2;
	}
	
	public Type getAnnoType()
	{
		return annoType;
	}
}
