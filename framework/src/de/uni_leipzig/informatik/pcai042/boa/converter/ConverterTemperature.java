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
	private ConfigLoader load;
	
	/**
	 * Constructor loads all necessary files for unit TEMPERATURE.
	 */
	public ConverterTemperature(ConfigLoader load)
	{
		super("TEMPERATURE", load);
		this.load = load;
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
		// het all tokens and choose unit
		for (int i = 0; i < annotation.size(); i++)
		{
			int chooseOption = 0;
			
			for (int l = 0; l < C.length; l++)
			{
				if (C[l].toString().equalsIgnoreCase(annotation.getToken(i)))
				{
					tempUnit = "celsius";
					chooseOption++;
				}
			}
			if (chooseOption == 0)
				for (int l = 0; l < F.length; l++)
				{
					if (F[l].toString().equalsIgnoreCase(annotation.getToken(i)))
					{
						chooseOption++;
						tempUnit = "fahrenheit";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < K.length; l++)
				{
					if (K[l].toString().equalsIgnoreCase(annotation.getToken(i)))
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
						
						if (check == 0)
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
			
			if (check2 ==1)
			{
				// convert to Kelvin
				BigDecimal tempNumber = new BigDecimal(number);
				String help = conversionStandard.get(tempUnit).toString();
				String help2 = conversionStandard.get(tempUnit + "f").toString();
				
				BigDecimal add = new BigDecimal(help);
				BigDecimal faktor = new BigDecimal(help2);
				BigDecimal standard = tempNumber.add(add).multiply(faktor);
				// convert to other units
				for (int d = 0; d < allUnitsofTemperature.length; d++)
				{
					String help3 = conversionUnit.get(allUnitsofTemperature[d]).toString();
					String help4 = conversionUnit.get(allUnitsofTemperature[d] + "f").toString();
					
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
			else{
				
				BigDecimal tempNumber = new BigDecimal(number);
				BigDecimal tempNumber2 = new BigDecimal(number2);
				String help = conversionStandard.get(tempUnit).toString();
				String help2 = conversionStandard.get(tempUnit + "f").toString();
				
				BigDecimal add = new BigDecimal(help);
				BigDecimal faktor = new BigDecimal(help2);
				BigDecimal standard = tempNumber.add(add).multiply(faktor);
				
				
				BigDecimal standard2 = tempNumber2.add(add).multiply(faktor);
				// convert to other units
				for (int d = 0; d < allUnitsofTemperature.length; d++)
				{
					String help3 = conversionUnit.get(allUnitsofTemperature[d]).toString();
					String help4 = conversionUnit.get(allUnitsofTemperature[d] + "f").toString();
					
					BigDecimal add2 = new BigDecimal(help3);
					BigDecimal faktor2 = new BigDecimal(help4);
					BigDecimal newNumber = standard.multiply(faktor2).add(add2);
					BigDecimal newNumber2 = standard2.multiply(faktor2).add(add2);
					if (allUnitsofTemperature[d].equals("celsius"))
					{
						for (int l = 0; l < C.length; l++)
						{
							list.add(newNumber + "-" +newNumber2+ C[l]);
						}
					}
					if (allUnitsofTemperature[d].equals("fahrenheit"))
					{
						for (int l = 0; l < F.length; l++)
						{
							list.add(newNumber + "-" +newNumber2+ F[l]);
						}
					}
					if (allUnitsofTemperature[d].equals("kelvin"))
					{
						for (int l = 0; l < K.length; l++)
						{
							list.add(newNumber + "-" +newNumber2+ K[l]);
						}
					}
					
				}
			
				
				
			}
		}
		
		return list;
	}
}
