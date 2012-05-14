/*
 * Converter.java
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

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;
import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;

/**
 * Abstract class for converting units. Each unit type (WEIGHT, LINEAR_MEASURE,
 * TEMPERATURE, DATE) is a subclass of Converter.
 * 
 * @author Duc Huy Bui; Christian Kahmann
 */
public abstract class Converter
{
	// private static final Logger logger =
	// LoggerFactory.getLogger(Converter.class);
	
	protected HashMap<String, BigDecimal> conversionStandard;
	protected HashMap<String, BigDecimal> conversionUnit;
	
	/**
	 * Constructor loads all necessary files for unit conversions.
	 * 
	 * @param unit
	 *            choose between WEIGHT, LINEAR_MEASURE, TEMPERATURE or DATE
	 */
	public Converter(String unit)
	{
		conversionStandard = new ConfigLoader().openConfigConversion("Standard", unit);
		conversionUnit = new ConfigLoader().openConfigConversion("Unit", unit);
	}
	
	/**
	 * Constructor.
	 */
	public Converter()
	{
		
	}
	
	/**
	 * Checks whether token is a number or not.
	 * 
	 * @param token
	 *            one token
	 * @return true - if token is a number, false - if token is not a number
	 */
	public boolean checkIfNumber(String token)
	{
		Set<String> numbers = new ConfigLoader().openConfigSurfaceForms("NUMBERS");
		
		try
		{
			Integer.parseInt(token);
			return true;
		} catch (NumberFormatException e)
		{
			// TODO catch exception or log
		}
		try
		{
			// if(token.endsWith("f")) return numbers.contains(token); //else f
			// at end of String is interpreted as "float", similar i as
			// imaginary
			if (token.contains(","))
				token = token.replace(",", ".");
			Double.parseDouble(token);
			return true;
		} catch (NumberFormatException e)
		{
			// TODO catch exception or log
		}
		return false;
		// return numbers.contains(token.toLowerCase());
	}
	
	/**
	 * Abstract method for converting units in one annotation.
	 * 
	 * @param annotation
	 *            one annotation
	 * @return list with all surface forms of an unit inclusive all
	 *         corresponding conversions
	 * @throws ParseException 
	 */
	public abstract ArrayList<String> convertUnits(BoaAnnotation annotation) throws ParseException;
}
