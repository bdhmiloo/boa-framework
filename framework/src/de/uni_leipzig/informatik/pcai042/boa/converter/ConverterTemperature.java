/*
 * ConverterTemperature.java
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
import java.util.List;
import java.util.Set;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;
import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;

/**
 * Converter class for unit type TEMPERATURE.
 * 
 * @author
 */
public class ConverterTemperature extends Converter
{
	/**
	 * Constructor loads all necessary files for unit TEMPERATURE.
	 */
	public ConverterTemperature()
	{
		super("TEMPERATURE");
	}
	
	/**
	 * Derived method of superclass Converter for unit type TEMPERATURE.
	 * 
	 * @param annotation
	 *            one annotation comprising at least one token of type
	 *            TEMPERATURE
	 * @return list with all surface forms of an unit inclusive all
	 *         corresponding conversions
	 */
	@Override
	public ArrayList<String> convertUnits(BoaAnnotation annotation)
	{
		ArrayList<String> list = new ArrayList<String>();
		ConfigLoader load = new ConfigLoader();
		String tempUnit = null;
		
		// load surface forms
		Set<String> unitNames = load.openConfigSurfaceForms("TEMPERATURE".toString());
		Set<String> Celsius = load.openConfigSurfaceForms("CELSIUS".toString());
		Set<String> Fahrenheit = load.openConfigSurfaceForms("FAHRENHEIT".toString());
		Set<String> Kelvin = load.openConfigSurfaceForms("KELVIN".toString());
		
		List<String> list1 = new ArrayList<String>(unitNames);
		list1.remove("MATH");
		list1.remove("NUMBERS");
		Object[] allUnitsofTemperature = new Object[list1.size()];
		for (int i = 0; i < allUnitsofTemperature.length; i++)
		{
			allUnitsofTemperature[i] = list1.get(i).toLowerCase();
		}
		
		List<String> list2 = new ArrayList<String>(Celsius);
		Object[] C = list2.toArray();
		
		List<String> list3 = new ArrayList<String>(Fahrenheit);
		Object[] F = list3.toArray();
		
		List<String> list4 = new ArrayList<String>(Kelvin);
		Object[] K = list4.toArray();
		
		for (int i = 0; i < annotation.getTokens().size(); i++)
		{
			int chooseOption = 0;
			
			for (int l = 0; l < C.length; l++)
			{
				if (C[l].toString().equalsIgnoreCase(annotation.getTokens().get(i)))
				{
					tempUnit = "celsius";
					chooseOption++;
				}
			}
			if (chooseOption == 0)
				for (int l = 0; l < F.length; l++)
				{
					if (F[l].toString().equalsIgnoreCase(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "fahrenheit";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < K.length; l++)
				{
					if (K[l].toString().equalsIgnoreCase(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "kelvin";
					}
				}
			
		}
		
		// option was selected
		if (tempUnit != null)
		{
			double number = 0;
			
			// looking for any numbers in annotation
			for (int w = 0; w < annotation.getTokens().size(); w++)
			{
				if (checkIfNumber(annotation.getTokens().get(w)))
				{
					String token = annotation.getTokens().get(w);
					
					if (token.contains("+"))
						token = token.replace("+", "");
					if (token.contains(","))
						token = token.replace(",", ".");
					if (token.contains("."))
						number = Double.parseDouble(token);
					else
					{
						number = (double) Integer.parseInt(token);
					}
				}
			}
			
			BigDecimal tempNumber = new BigDecimal(number);
			String help = conversionStandard.get(tempUnit).toString();
			String help2 =conversionStandard.get(tempUnit+"f").toString();
			
			BigDecimal add = new BigDecimal(help);
			BigDecimal faktor = new BigDecimal(help2);
			BigDecimal standard = tempNumber.add(add).multiply(faktor);
			
			for (int d = 0; d < allUnitsofTemperature.length; d++)
			{
				String help3 = conversionUnit.get(allUnitsofTemperature[d]).toString();
				String help4 = conversionUnit.get(allUnitsofTemperature[d]+"f").toString();
				
				BigDecimal add2 = new BigDecimal(help3);
				BigDecimal faktor2 = new BigDecimal(help4);
				BigDecimal newNumber = standard.multiply(faktor2).add(add2);
				
				if (allUnitsofTemperature[d].equals("celsius"))
				{
					for (int l = 0; l < C.length; l++)
					{
						list.add(newNumber + " " + C[l]);
					}
				}
				if (allUnitsofTemperature[d].equals("fahrenheit"))
				{
					for (int l = 0; l < F.length; l++)
					{
						list.add(newNumber + " " + F[l]);
					}
				}
				if (allUnitsofTemperature[d].equals("kelvin"))
				{
					for (int l = 0; l < K.length; l++)
					{
						list.add(newNumber + " " + K[l]);
					}
				}
				
			}
			
		}
		
		return list;
	}
}
 