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

package playground;

import java.util.ArrayList;

/**
 * An immutable annotation containing its label as list of tokens and its type.
 * 
 * @author Simon Suiter
 */
public class BoaAnnotation
{
	private Type type;
	private ArrayList<String> tokens;
	
	public static enum Type
	{
		DATE, LINEAR_MEASURE, TEMPERATURE, WEIGHT;
	}
	
	public BoaAnnotation(Type type, ArrayList<String> tokens)
	{
		this.type = type;
		this.tokens = tokens;
	}
	
	public Type getType()
	{
		return type;
	}
	
	public ArrayList<String> getTokens()
	{
		return tokens;
	}
	
	/**
	 * Format: token_1 token_2 ... token_n: type
	 */
	@Override
	public String toString()
	{
		String r = "";
		for (String s : tokens)
		{
			r += s;
		}
		return r + ": " + type;
	}
}
