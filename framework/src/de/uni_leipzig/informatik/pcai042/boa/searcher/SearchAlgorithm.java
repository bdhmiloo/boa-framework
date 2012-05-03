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
import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation.Type;

public abstract class SearchAlgorithm
{
	protected Set<String> surForms;
	protected Type annoType;
	
	public SearchAlgorithm(){}
	
	public SearchAlgorithm(Set<String> surfaceForms, Type annoType)
	{
		try
		{
			this.annoType = annoType;
			surForms = new HashSet<String>();
			surForms.addAll(surfaceForms);
		}
		catch(NullPointerException e)
		{
			//do sth
		}
	}
	
	/**
	 * Searches sentence for occurrences of certain patterns.
	 * The nature of this patterns is defined in each subclass.
	 * On success objects of type BoaAnnotation will be added to sentence.
	 * @param sentence
	 */
	//subclasses will have to overwrite this
	public void search(BoaSentence sentence)
	{
		return;
	}
	
	/**
	 * Checks if a String representation of a token is a number
	 * @param token
	 * @return true if it can be parsed to an integer or double value, else false
	 */
	
	
	protected boolean checkIfNumber(String token)
	{	
		try
		{
			Integer.parseInt(token);
			return true;
		}
		catch(NumberFormatException e){}
		try
		{
			if(token.contains(",")) token = token.replace(",", ".");
			Double.parseDouble(token);
			return true;
		}
		catch(NumberFormatException e){}
				
		return false;
	}
	
	
	/**
	 * Checks if a String representation of a token starts with a number.
	 * @param token
	 * @return -1 if no combined number else position of first non-numerical char
	 */
	
	protected int checkIfStartsWithNumber(String token)
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
	
	protected boolean isPrefix(String token, Set<String> words)
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
}
