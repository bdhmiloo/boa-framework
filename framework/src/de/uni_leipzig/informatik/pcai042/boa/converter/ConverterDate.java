/*
 * ConverterDate.java
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

package de.uni_leipzig.informatik.pcai042.boa.converter;

import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;

/**
 * Converter class for unit type DATE.
 * 
 * @author Duc Huy Bui
 */
public class ConverterDate extends Converter
{
	/**
	 * Constructor.
	 * 
	 */
	public ConverterDate()
	{
		// TODO add code here
	}

	/**
	 * Derived method of superclass Converter for unit type DATE.
	 * 
	 * @param annotation
	 *            one annotation comprising at least one token of type DATE
	 * @return list with all surface forms of an unit inclusive all
	 *         corresponding conversions
	 */
	@Override
	public ArrayList<String> convertUnits(BoaAnnotation annotation)
	{
		ArrayList<String> list = new ArrayList<String>();
		
		// get all tokens of annotation and choose unit
		for (int i = 0; i < annotation.getTokens().size(); i++)
		{
			
			// annotation.getTokens().get(i).replaceAll("'|\"|,|-|\\.|/|~", "");
			
			// TODO test if token is a number
			
			// TODO token is not a number --> must be some kind of string
			
			// TODO convert to other surfaceforms (numbers)
			
			// TODO convert to other surfacforms (strings)
			
		}
		
		return list;
	}
	
	/**
	 * 
	 * 
	 * @param dateString
	 * @param fromPattern
	 * @param toPattern
	 * @return
	 * @throws ParseException
	 */
	private String getDateString(String dateString, String fromPattern, String toPattern) throws ParseException
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fromPattern);
		Date date1 = simpleDateFormat.parse(dateString);
		simpleDateFormat.applyPattern(toPattern);
		
		return simpleDateFormat.format(date1);
	}
	
}
