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
	private ConfigLoader load;
	
	/**
	 * Constructor loads all necessary files for unit WEIGHT.
	 */
	public ConverterWeight(ConfigLoader load)
	{
		super("WEIGHT", load);
		this.load = load;
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
		list1.remove("MATH");
		list1.remove("NUMBERS");
		Object[] allUnitsOfWeight = new Object[list1.size()];
		for (int i = 0; i < allUnitsOfWeight.length; i++)
		{
			allUnitsOfWeight[i] = list1.get(i).toLowerCase();
		}
		
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
		for (int i = 0; i < annotation.size(); i++)
		{
			int chooseOption = 0;
			
			for (int l = 0; l < g.length; l++)
			{
				if (g[l].equals(annotation.getToken(i)))
				{
					tempUnit = "gram";
					chooseOption++;
				}
			}
			if (chooseOption == 0)
				for (int l = 0; l < microg.length; l++)
				{
					if (microg[l].toString().equalsIgnoreCase(annotation.getToken(i)))
					{
						chooseOption++;
						tempUnit = "microgram";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < t.length; l++)
				{
					if (t[l].toString().equalsIgnoreCase(annotation.getToken(i)))
					{
						chooseOption++;
						tempUnit = "ton";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < kg.length; l++)
				{
					if (kg[l].toString().equalsIgnoreCase(annotation.getToken(i)))
					{
						chooseOption++;
						tempUnit = "kilogram";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < Kt.length; l++)
				{
					if (Kt[l].toString().equalsIgnoreCase(annotation.getToken(i)))
					{
						chooseOption++;
						tempUnit = "karat";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < oz.length; l++)
				{
					if (oz[l].toString().equalsIgnoreCase(annotation.getToken(i)))
					{
						chooseOption++;
						tempUnit = "ounze";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < mg.length; l++)
				{
					if (mg[l].toString().equalsIgnoreCase(annotation.getToken(i)))
					{
						chooseOption++;
						tempUnit = "milligram";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < lbs.length; l++)
				{
					if (lbs[l].toString().equalsIgnoreCase(annotation.getToken(i)))
					{
						chooseOption++;
						tempUnit = "pound";
					}
				}
		}
		
		// option was selected if any weight-unit could have been found
		if (tempUnit != null)
		{
			double number = 0;
			double number2 = 0;
			int check2 = 0;
			// looking for any numbers in annotation
			for (int w = 0; w < annotation.size(); w++)
			{
				if (checkIfNumber(annotation.getToken(w)))
				{
					check2++;
					if (check2 < 2)
					{
						String token = annotation.getToken(w);
						int check = 0;
						if (token.equals("one"))
						{
							check++;
							number = 1;
							
						}
						if (token.equals("two"))
						{
							check++;
							number = 2;
							
						}
						if (token.equals("three"))
						{
							check++;
							number = 3;
							
						}
						if (token.equals("four"))
						{
							check++;
							number = 4;
							
						}
						if (token.equals("five"))
						{
							check++;
							number = 5;
							
						}
						if (token.equals("six"))
						{
							check++;
							number = 6;
							
						}
						if (token.equals("seven"))
						{
							check++;
							number = 7;
							
						}
						if (token.equals("eight"))
						{
							check++;
							number = 8;
							
						}
						if (token.equals("nine"))
						{
							check++;
							number = 9;
							
						}
						if (token.equals("ten"))
						{
							check++;
							number = 10;
							
						}
						if (token.equals("eleven"))
						{
							check++;
							number = 11;
							
						}
						if (token.equals("twelve"))
						{
							check++;
							number = 12;
							
						}
						if (token.equals("thirteen"))
						{
							check++;
							number = 13;
							
						}
						if (token.equals("fourteen"))
						{
							check++;
							number = 14;
							
						}
						if (token.equals("fifteen"))
						{
							check++;
							number = 15;
							
						}
						
						if (token.equals("sixteen"))
						{
							check++;
							number = 16;
							
						}
						if (token.equals("seventeen"))
						{
							check++;
							number = 17;
							
						}
						if (token.equals("eighteen"))
						{
							check++;
							number = 18;
							
						}
						if (token.equals("nineteen"))
						{
							check++;
							number = 19;
							
						}
						if (token.equals("twenty"))
						{
							check++;
							number = 20;
							
						}
						if (token.equals("thirty"))
						{
							check++;
							number = 30;
							
						}
						if (token.equals("fourty"))
						{
							check++;
							number = 40;
							
						}
						if (token.equals("fifty"))
						{
							check++;
							number = 50;
							
						}
						if (token.equals("sixty"))
						{
							check++;
							number = 60;
							
						}
						if (token.equals("seventy"))
						{
							check++;
							number = 70;
							
						}
						if (token.equals("eighty"))
						{
							check++;
							number = 80;
							
						}
						if (token.equals("ninety"))
						{
							check++;
							number = 90;
							
						}
						
						if (check != 0)
						{
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
					} else
					{
						String token = annotation.getToken(w);
						int check = 0;
						if (token.equals("one"))
						{
							check++;
							number2 = 1;
							
						}
						if (token.equals("two"))
						{
							check++;
							number2 = 2;
							
						}
						if (token.equals("three"))
						{
							check++;
							number2 = 3;
							
						}
						if (token.equals("four"))
						{
							check++;
							number2 = 4;
							
						}
						if (token.equals("five"))
						{
							check++;
							number2 = 5;
							
						}
						if (token.equals("six"))
						{
							check++;
							number2 = 6;
							
						}
						if (token.equals("seven"))
						{
							check++;
							number2 = 7;
							
						}
						if (token.equals("eight"))
						{
							check++;
							number2 = 8;
							
						}
						if (token.equals("nine"))
						{
							check++;
							number2 = 9;
							
						}
						if (token.equals("ten"))
						{
							check++;
							number2 = 10;
							
						}
						if (token.equals("eleven"))
						{
							check++;
							number2 = 11;
							
						}
						if (token.equals("twelve"))
						{
							check++;
							number2 = 12;
							
						}
						if (token.equals("thirteen"))
						{
							check++;
							number2 = 13;
							
						}
						if (token.equals("fourteen"))
						{
							check++;
							number2 = 14;
							
						}
						if (token.equals("fifteen"))
						{
							check++;
							number2 = 15;
							
						}
						
						if (token.equals("sixteen"))
						{
							check++;
							number2 = 16;
							
						}
						if (token.equals("seventeen"))
						{
							check++;
							number2 = 17;
							
						}
						if (token.equals("eighteen"))
						{
							check++;
							number2 = 18;
							
						}
						if (token.equals("nineteen"))
						{
							check++;
							number2 = 19;
							
						}
						if (token.equals("twenty"))
						{
							check++;
							number2 = 20;
							
						}
						if (token.equals("thirty"))
						{
							check++;
							number2 = 30;
							
						}
						if (token.equals("fourty"))
						{
							check++;
							number2 = 40;
							
						}
						if (token.equals("fifty"))
						{
							check++;
							number2 = 50;
							
						}
						if (token.equals("sixty"))
						{
							check++;
							number2 = 60;
							
						}
						if (token.equals("seventy"))
						{
							check++;
							number2 = 70;
							
						}
						if (token.equals("eighty"))
						{
							check++;
							number2 = 80;
							
						}
						if (token.equals("ninety"))
						{
							check++;
							number2 = 90;
							
						}
						
						if (check == 0)
						{
							if (token.contains("+"))
								token = token.replace("+", "");
							if (token.contains(","))
								token = token.replace(",", ".");
							if (token.contains("."))
								number2 = Double.parseDouble(token);
							else
							{
								number2 = (double) Integer.parseInt(token);
							}
						}
						
					}
				}
			}
			if (check2 == 1)
			{
				BigDecimal tempNumber = new BigDecimal(number);
				BigDecimal standard = tempNumber.multiply(conversionStandard.get(tempUnit));
				
				// get multiplication factor for conversion
				for (int d = 0; d < allUnitsOfWeight.length; d++)
				{
					BigDecimal newNumber = standard.multiply(conversionUnit.get(allUnitsOfWeight[d]));
					
					if (allUnitsOfWeight[d].equals("kilogram"))
					{
						for (int l = 0; l < kg.length; l++)
						{
							list.add(newNumber + " " + kg[l]);
						}
					}
					if (allUnitsOfWeight[d].equals("microgram"))
					{
						for (int l = 0; l < microg.length; l++)
						{
							list.add(newNumber + " " + microg[l]);
						}
					}
					if (allUnitsOfWeight[d].equals("gram"))
					{
						for (int l = 0; l < g.length; l++)
						{
							list.add(newNumber + " " + g[l]);
						}
					}
					if (allUnitsOfWeight[d].equals("ton"))
					{
						for (int l = 0; l < t.length; l++)
						{
							list.add(newNumber + " " + t[l]);
						}
					}
					if (allUnitsOfWeight[d].equals("karat"))
					{
						for (int l = 0; l < Kt.length; l++)
						{
							list.add(newNumber + " " + Kt[l]);
						}
					}
					if (allUnitsOfWeight[d].equals("ounze"))
					{
						for (int l = 0; l < oz.length; l++)
						{
							list.add(newNumber + " " + oz[l]);
						}
					}
					if (allUnitsOfWeight[d].equals("pound"))
					{
						for (int l = 0; l < lbs.length; l++)
						{
							list.add(newNumber + " " + lbs[l]);
						}
					}
					if (allUnitsOfWeight[d].equals("milligram"))
					{
						for (int l = 0; l < mg.length; l++)
						{
							list.add(newNumber + " " + mg[l]);
						}
					}
				}
			} else
			{
				BigDecimal tempNumber = new BigDecimal(number);
				BigDecimal standard = tempNumber.multiply(conversionStandard.get(tempUnit));
				
				BigDecimal tempNumber2 = new BigDecimal(number2);
				BigDecimal standard2 = tempNumber2.multiply(conversionStandard.get(tempUnit));
				
				// get multiplication factor for conversion
				for (int d = 0; d < allUnitsOfWeight.length; d++)
				{
					BigDecimal newNumber = standard.multiply(conversionUnit.get(allUnitsOfWeight[d]));
					
					BigDecimal newNumber2 = standard2.multiply(conversionUnit.get(allUnitsOfWeight[d]));
					if (allUnitsOfWeight[d].equals("kilogram"))
					{
						for (int l = 0; l < kg.length; l++)
						{
							list.add(newNumber + "-" +newNumber2+ kg[l]);
						}
					}
					if (allUnitsOfWeight[d].equals("microgram"))
					{
						for (int l = 0; l < microg.length; l++)
						{
							list.add(newNumber + "-" +newNumber2+ microg[l]);
						}
					}
					if (allUnitsOfWeight[d].equals("gram"))
					{
						for (int l = 0; l < g.length; l++)
						{
							list.add(newNumber + "-" +newNumber2+ g[l]);
						}
					}
					if (allUnitsOfWeight[d].equals("ton"))
					{
						for (int l = 0; l < t.length; l++)
						{
							list.add(newNumber + "-" +newNumber2+ t[l]);
						}
					}
					if (allUnitsOfWeight[d].equals("karat"))
					{
						for (int l = 0; l < Kt.length; l++)
						{
							list.add(newNumber + "-"+newNumber2 + Kt[l]);
						}
					}
					if (allUnitsOfWeight[d].equals("ounze"))
					{
						for (int l = 0; l < oz.length; l++)
						{
							list.add(newNumber + "-"+newNumber2 + oz[l]);
						}
					}
					if (allUnitsOfWeight[d].equals("pound"))
					{
						for (int l = 0; l < lbs.length; l++)
						{
							list.add(newNumber + "-"+newNumber2 + lbs[l]);
						}
					}
					if (allUnitsOfWeight[d].equals("milligram"))
					{
						for (int l = 0; l < mg.length; l++)
						{
							list.add(newNumber + "-"+newNumber2 + mg[l]);
						}
					}
				}
			}
			
		}
		
		return list;
	}
	
}
