/*
 * Annotation.java
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

package de.uni_leipzig.informatik.pcai042.boa.manager;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * An immutable annotation containing its label as list of tokens and its type.
 * 
 * @author Simon Suiter
 */
public class BoaAnnotation implements Iterable<String>
{
	private String type;
	private ArrayList<String> tokens;
	
	public BoaAnnotation(String type, ArrayList<String> tokens)
	{
		this.type = type;
		this.tokens = tokens;
	}
	
	public String getType()
	{
		return type;
	}
	
	public String getToken(int index)
	{
		return tokens.get(index);
	}
	
	public int size()
	{
		return tokens.size();
	}
	
	@Override
	public Iterator<String> iterator()
	{
		return tokens.iterator();
	}
	
	/**
	 * Format: token_1 token_2 ... token_n: type
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (String s : tokens)
		{
			sb.append(s);
			sb.append(" ");
		}
		sb.setCharAt(sb.length() - 1, ':');
		sb.append(type);
		return sb.toString();
	}
}
