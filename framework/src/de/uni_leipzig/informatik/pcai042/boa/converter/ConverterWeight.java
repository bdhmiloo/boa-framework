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
import java.util.List;
import java.util.Set;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;
import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;

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
		ConfigLoader load = new ConfigLoader();
		String tempUnit = null;
		
		// load surface forms
		Set<String> unitNames = load.openConfigSurfaceForms("WEIGHT".toString());
		Set<String> Microgram = load.openConfigSurfaceForms("MICROGRAM".toString());
		Set<String> Milligram = load.openConfigSurfaceForms("MILLIGRAM".toString());
		Set<String> Gram = load.openConfigSurfaceForms("GRAM".toString());
		Set<String> Kilogram = load.openConfigSurfaceForms("KILOGRAM".toString());
		Set<String> Ton = load.openConfigSurfaceForms("TON".toString());
		Set<String> Pound = load.openConfigSurfaceForms("POUND".toString());
		Set<String> Karat = load.openConfigSurfaceForms("KARAT".toString());
		Set<String> Ounze = load.openConfigSurfaceForms("OUNZE".toString());
		
		List<String> list1 = new ArrayList<String>(unitNames);
		Object[] allUnitsOfWeight = list1.toArray();
		
		List<String> list2 = new ArrayList<String>(Microgram);
		Object[] microg = list2.toArray();
		
		List<String> list3 = new ArrayList<String>(Milligram);
		Object[] mg = list3.toArray();
		
		List<String> list4 = new ArrayList<String>(Gram);
		Object[] g = list4.toArray();
		
		List<String> list5 = new ArrayList<String>(Kilogram);
		Object[] kg = list5.toArray();
		
		List<String> list6 = new ArrayList<String>(Ton);
		Object[] t = list6.toArray();
		
		List<String> list7 = new ArrayList<String>(Pound);
		Object[] lbs = list7.toArray();
		
		List<String> list8 = new ArrayList<String>(Karat);
		Object[] Kt = list8.toArray();
		
		List<String> list9 = new ArrayList<String>(Ounze);
		Object[] oz = list9.toArray();
		
	
		// get all tokens of annotation and choose unit
		for (int i = 0; i < annotation.getTokens().size(); i++)
		{
			int chooseOption = 0;
			
			for (int l = 0; l < g.length; l++)
			{
				if (g[l].equals(annotation.getTokens().get(i)))
				{
					tempUnit = "gram";
					chooseOption++;
				}
			}
			if (chooseOption == 0)
				for (int l = 0; l < microg.length; l++)
				{
					if (microg[l].toString().equalsIgnoreCase(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "microgram";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < t.length; l++)
				{
					if (t[l].toString().equalsIgnoreCase(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "ton";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < kg.length; l++)
				{
					if (kg[l].toString().equalsIgnoreCase(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "kilogram";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < Kt.length; l++)
				{
					if (Kt[l].toString().equalsIgnoreCase(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "karat";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < oz.length; l++)
				{
					if (oz[l].toString().equalsIgnoreCase(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "ounze";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < mg.length; l++)
				{
					if (mg[l].toString().equalsIgnoreCase(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "milligram";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < lbs.length; l++)
				{
					if (lbs[l].toString().equalsIgnoreCase(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "pound";
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
				if (allUnitsOfWeight[d].equals("microg"))
				{
					for (int l = 0; l < microg.length; l++)
					{
						list.add(newNumber + " " + microg[l]);
					}
				}
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
