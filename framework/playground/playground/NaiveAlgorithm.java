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

package playground;

import java.util.ArrayList;
import java.util.Iterator;

import playground.BoaAnnotation.Type;

public class NaiveAlgorithm extends SearchAlgorithm
{
	public BoaSentence search(BoaSentence sentence, ArrayList<String> surForms, Type annoType)
	{
		String currentToken = "";
		String currentSurf, match;
		Boolean replaced = false;	//need this when a token representing a number is replaced with another token representing a number
									//replacement isn't actually needed but makes algorithm a little faster
		
		//Step one: Check if any token is a number.
		Iterator<String> tokenIt = sentence.getTokens().iterator();
		
		while(tokenIt.hasNext())
		{
			if(!replaced)currentToken = tokenIt.next();
			replaced = false;
			
			if(this.checkIfNumber(currentToken))
			{
				//Step two: Find the corresponding unit. It can be assumed that (if it exists) it will be
				//located in the part of the sentence right from the numerical value. 
				Iterator<String> findMatch = sentence.getTokens().iterator();
				
				//go to first token after currentToken
				do
				{
					match = findMatch.next();
				}while(!match.equals(currentToken));
				
				//for each token right from currentToken
				while(findMatch.hasNext())
				{
					match = findMatch.next();
					//if it is a number there wont be any Annotations based on currentToken as
					//any further occurring units will be related to match instead
					if(this.checkIfNumber(match))
					{
						while(!currentToken.equals(match))
						{
							currentToken = tokenIt.next();
							replaced = true;
						}
						break;
					}
					
					//checks if match is equal to any known surface form of the units we look for
					Iterator<String> surfIt = surForms.iterator();
					
					while(surfIt.hasNext())
					{
						currentSurf = surfIt.next();
						//System.out.println("Test if " + match +" matches with " + currentSurf);
						if(currentSurf.equals(match))
						{
							//create Annotation
							ArrayList<String> annoTokens = new ArrayList<String>();
							annoTokens.add(currentToken);
							annoTokens.add(match);
							
							sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
							
							//this is for tests only
							//System.out.println("Annotation: " + currentToken + " " + match + " Type: " + annoType);
						}
					}			
				}
			}
		}
			
		return sentence;
	}
	
}
