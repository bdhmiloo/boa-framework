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

package playground;

import java.util.ArrayList;

import playground.BoaAnnotation.Type;

public abstract class SearchAlgorithm
{
	//subclasses will have to overwrite this
	public BoaSentence search(BoaSentence sentence, ArrayList<String> surForms, Type annoType)
	{
		return null;
	}
	
	
	public boolean checkIfNumber(String token)
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
}
