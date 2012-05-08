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
import java.util.ArrayList;
import java.util.HashMap;

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
	protected HashMap<String, BigDecimal> conversionStandard;
	protected HashMap<String, BigDecimal> conversionUnit;
	
	/**
	 * Constructor loads all necessary files for unit conversions.
	 * 
	 * @param unit
	 *            choose between WEIGHT, LINEAR_MEASURE, TEMPERATURE or DATE
	 * @param file
	 *            name of file with annotations that should be loaded
	 */
	public Converter(String unit, String file)
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
	protected boolean checkIfNumber(String token)
	{
		int i = 0;
		
		// testing integer
		try
		{
			Integer.parseInt(token);
			i++;
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		
		// testing double
		try
		{
			if (token.contains(","))
				token = token.replace(",", ".");
			Double.parseDouble(token);
			i++;
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		
		if (i == 0)
			return false;
		else
			return true;
	}
	
	/**
	 * Abstract method for loading a file with all surface forms of all unit
	 * types.
	 * 
	 * @param file
	 *            name of file with surface forms of all unit types
	 */
	protected abstract void loadSurfaceForms(String file);
	
	/**
	 * Abstract method for converting units in one annotation.
	 * 
	 * @param annotation
	 *            one annotation
	 * @return list with all surface forms of an unit inclusive all
	 *         corresponding conversions
	 */
	public abstract ArrayList<String> convertUnits(BoaAnnotation annotation);
}
