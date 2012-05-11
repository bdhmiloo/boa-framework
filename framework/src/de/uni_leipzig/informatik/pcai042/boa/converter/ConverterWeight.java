/*
 * ConverterWeight.java
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

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;

/**
 * Converter class for unit type WEIGHT.
 * 
 * @author Christian Kahmann; Duc Huy Bui
 */
public class ConverterWeight extends Converter
{
	/**
	 * Constructor loads all necessary files for unit WEIGHT.
	 */
	public ConverterWeight()
	{
		super("WEIGHT");
	}
	
	/**
	 * Derived method for loading a file with all surface forms of WEIGHT.
	 * 
	 * @param file
	 *            name of file with surface forms of all unit types
	 */
	@Override
	protected void loadSurfaceForms(String file)
	{
		// TODO add code here
		
	}
	
	/**
	 * Derived method of superclass Converter for unit type WEIGHT.
	 * 
	 * @param annotation
	 *            one annotation comprising at least one token of type WEIGHT
	 * @return list with all surface forms of an unit inclusive all
	 *         corresponding conversions
	 */
	@Override
	public ArrayList<String> convertUnits(BoaAnnotation annotation)
	{
		ArrayList<String> list = new ArrayList<String>();
		String tempUnit = null;
		
//		String[] microg = { "µg", "microgram" };
		String[] mg = { "mg", "milligram", "milligrams" };
		String[] g = { "g", "gram" };
		String[] kg = { "kg", "kilogram", "kilo", "kg." };
		String[] t = { "t", "ton", "metric ton", "metric tons", "tons" };
		String[] lbs = { "lbs", "lb", "labs", "pound", "pounds" };
		String[] oz = { "oz", "ounze" };
		String[] Kt = { "Kt", "carat" };
		
		String[] allUnitsOfWeight = { /*"µm" ,*/ "mg", "g", "t", "kg", "lbs", "oz", "Kt" };
		
		// get all tokens of annotation and choose unit
		for (int i = 0; i < annotation.getTokens().size(); i++)
		{
			int chooseOption = 0;
			
			for (int l = 0; l < g.length; l++)
			{
				if (g[l].equals(annotation.getTokens().get(i)))
				{
					tempUnit = "g";
					chooseOption++;
				}
			}
//			if (chooseOption == 0)
//				for (int l = 0; l < microg.length; l++)
//				{
//					if (microg[l].equals(annotation.getTokens().get(i)))
//					{
//						chooseOption++;
//						tempUnit = "µg";
//					}
//				}
			if (chooseOption == 0)
				for (int l = 0; l < t.length; l++)
				{
					if (t[l].equals(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "t";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < kg.length; l++)
				{
					if (kg[l].equals(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "kg";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < Kt.length; l++)
				{
					if (Kt[l].equals(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "Kt";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < oz.length; l++)
				{
					if (oz[l].equals(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "oz";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < mg.length; l++)
				{
					if (mg[l].equals(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "mg";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < lbs.length; l++)
				{
					if (lbs[l].equals(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "lbs";
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
			BigDecimal standard = tempNumber.multiply(conversionStandard.get(tempUnit));
			
			// get multiplication factor for conversion
			for (int d = 0; d < allUnitsOfWeight.length; d++)
			{
				BigDecimal newNumber = standard.multiply(conversionUnit.get(allUnitsOfWeight[d]));
				
				if (allUnitsOfWeight[d].equals("kg"))
				{
					for (int l = 0; l < kg.length; l++)
					{
						list.add(newNumber + " " + kg[l]);
					}
				}
//				if (allUnitsOfWeight[d].equals("µg"))
//				{
//					for (int l = 0; l < microg.length; l++)
//					{
//						list.add(newNumber + " " + microg[l]);
//					}
//				}
				if (allUnitsOfWeight[d].equals("g"))
				{
					for (int l = 0; l < g.length; l++)
					{
						list.add(newNumber + " " + g[l]);
					}
				}
				if (allUnitsOfWeight[d].equals("t"))
				{
					for (int l = 0; l < t.length; l++)
					{
						list.add(newNumber + " " + t[l]);
					}
				}
				if (allUnitsOfWeight[d].equals("Kt"))
				{
					for (int l = 0; l < Kt.length; l++)
					{
						list.add(newNumber + " " + Kt[l]);
					}
				}
				if (allUnitsOfWeight[d].equals("oz"))
				{
					for (int l = 0; l < oz.length; l++)
					{
						list.add(newNumber + " " + oz[l]);
					}
				}
				if (allUnitsOfWeight[d].equals("lbs"))
				{
					for (int l = 0; l < lbs.length; l++)
					{
						list.add(newNumber + " " + lbs[l]);
					}
				}
				if (allUnitsOfWeight[d].equals("mg"))
				{
					for (int l = 0; l < mg.length; l++)
					{
						list.add(newNumber + " " + mg[l]);
					}
				}
			}
		}
		
		return list;
	}
	
}
