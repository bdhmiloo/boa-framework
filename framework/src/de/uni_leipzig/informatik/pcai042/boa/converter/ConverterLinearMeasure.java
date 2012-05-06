/*
 * ConverterLinearMeasure.java
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
 * 
 * @author Duc Huy Bui; Christian Kahmann
 */
public class ConverterLinearMeasure extends Converter
{
	/**
	 * Constructor loads all necessary files for unit linear measure.
	 * 
	 * @param file
	 *            - name of file with annotations that should be loaded
	 */
	public ConverterLinearMeasure(String file)
	{
		super("LINEAR_MEASURE", file);
	}
	
	/**
	 * 
	 * @param annotation
	 * @return list with all surfaceforms of an unit inclusive all conversions
	 */
	@Override
	public ArrayList<String> convertUnits(BoaAnnotation annotation)
	{
		ArrayList<String> list = new ArrayList<String>();
		String tempUnit = null;
		
		String[] mm = { "nm", "nanometer", "nanometers" };
		String[] cm = { "cm", "centimeter", "centimeters" };
		String[] dm = { "dm", "decimeters", "decimeter" };
		String[] m = { "m", "meter", "meters", "metres" };
		String[] km = { "km", "kilometer", "kilometers" };
		String[] ft = { "ft", "feet", "feet", "'" };
		String[] inch = { "inch", "in", "inches", };
		String[] yard = { "yard", "yards", "yd", "yds" };
		String[] seamile = { "sm", "seamile", "seamiles", "nautic mile", "nautic miles" };
		String[] mile = { "mile", "miles" };
		
		String[] allUnitsOfLinearMeasure = { "mm", "cm", "dm", "m", "km", "mile", "seamile", "yard", "ft", "inch" };
		
		// get all tokens of annotation and choose unit
		for (int i = 0; i < annotation.getTokens().size(); i++)
		{
			int chooseOption = 0;
			
			for (int l = 0; l < mm.length; l++)
			{
				if (mm[l].equals(annotation.getTokens().get(i)))
				{
					tempUnit = "mm";
					chooseOption++;
				}
			}
			if (chooseOption == 0)
				for (int l = 0; l < cm.length; l++)
				{
					if (cm[l].equals(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "cm";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < dm.length; l++)
				{
					if (dm[l].equals(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "dm";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < m.length; l++)
				{
					if (m[l].equals(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "m";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < km.length; l++)
				{
					if (km[l].equals(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "km";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < ft.length; l++)
				{
					if (ft[l].equals(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "ft";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < inch.length; l++)
				{
					if (inch[l].equals(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "inch";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < yard.length; l++)
				{
					if (yard[l].equals(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "yard";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < seamile.length; l++)
				{
					if (seamile[l].equals(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "seamile";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < mile.length; l++)
				{
					if (mile[l].equals(annotation.getTokens().get(i)))
					{
						chooseOption++;
						tempUnit = "mile";
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
			
			BigDecimal a = new BigDecimal(number);
			BigDecimal standard = a.multiply(conversionStandard.get(tempUnit));
			
			// get multiplication factor for conversion
			for (int d = 0; d < allUnitsOfLinearMeasure.length; d++)
			{
				BigDecimal neu = standard.multiply(conversionUnit.get(allUnitsOfLinearMeasure[d]));
				
				if (allUnitsOfLinearMeasure[d].equals("mm"))
				{
					for (int l = 0; l < mm.length; l++)
					{
						list.add(neu + " " + mm[l]);
					}
				}
				if (allUnitsOfLinearMeasure[d].equals("cm"))
				{
					for (int l = 0; l < cm.length; l++)
					{
						list.add(neu + " " + cm[l]);
					}
				}
				if (allUnitsOfLinearMeasure[d].equals("dm"))
				{
					for (int l = 0; l < dm.length; l++)
					{
						list.add(neu + " " + dm[l]);
					}
				}
				if (allUnitsOfLinearMeasure[d].equals("m"))
				{
					for (int l = 0; l < m.length; l++)
					{
						list.add(neu + " " + m[l]);
					}
				}
				if (allUnitsOfLinearMeasure[d].equals("km"))
				{
					for (int l = 0; l < km.length; l++)
					{
						list.add(neu + " " + km[l]);
					}
				}
				if (allUnitsOfLinearMeasure[d].equals("ft"))
				{
					for (int l = 0; l < ft.length; l++)
					{
						list.add(neu + " " + ft[l]);
					}
				}
				if (allUnitsOfLinearMeasure[d].equals("inch"))
				{
					for (int l = 0; l < inch.length; l++)
					{
						list.add(neu + " " + inch[l]);
					}
				}
				if (allUnitsOfLinearMeasure[d].equals("yard"))
				{
					for (int l = 0; l < yard.length; l++)
					{
						list.add(neu + " " + yard[l]);
					}
				}
				if (allUnitsOfLinearMeasure[d].equals("seamile"))
				{
					for (int l = 0; l < seamile.length; l++)
					{
						list.add(neu + " " + seamile[l]);
					}
				}
				if (allUnitsOfLinearMeasure[d].equals("mile"))
				{
					for (int l = 0; l < mile.length; l++)
					{
						list.add(neu + " " + mile[l]);
					}
				}
			}
		}
		
		return list;
	}
	
}
