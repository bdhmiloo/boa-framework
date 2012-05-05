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

import java.io.File;
import java.io.IOException;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;
import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;
import de.uni_leipzig.informatik.pcai042.boa.manager.SentenceLoader;

/**
 * Abstract class
 * 
 * @author Duc Huy Bui; Christian Kahmann
 */
public abstract class Converter
{
	private static HashMap<String, BigDecimal> conversionStandard;
	private static HashMap<String, BigDecimal> conversionUnit;
	
	SentenceLoader sentence;
	
	/**
	 * Constructor loads all necessary files for unit conversion.
	 * 
	 * @param unit
	 *            - String: WEIGHT, LINEAR MEASURE, TEMPERATURE or DATE
	 * @param file
	 *            - String: name of file with annotations that should be loaded
	 */
	public Converter(String unit, String file)
	{
		conversionStandard = new ConfigLoader().openConfigConversion("Standard", unit);
		conversionUnit = new ConfigLoader().openConfigConversion("Unit", unit);
		
		try
		{
			sentence = new SentenceLoader(new File(file));
		} catch (ValidityException e)
		{
			e.printStackTrace();
		} catch (ParsingException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		if (sentence == null)
			return;
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
	 * @return true - if token is a number, false - if token is not a number
	 */
	protected static boolean checkIfNumber(String token)
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
	 * 
	 * @param annotation
	 * @return list with all surfaceforms of an unit inclusive all conversion
	 */
	public ArrayList<String> convertUnit(BoaAnnotation annotation)
	{
		ArrayList<String> list = new ArrayList<String>();
		
		
		

		return list;
	}
}
