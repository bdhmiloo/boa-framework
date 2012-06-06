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
import java.util.List;
import java.util.Set;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;
import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;

/**
 * Converter class for unit type LINEAR_MEASURE.
 * 
 * @author Christian Kahmann; Duc Huy Bui
 */
public class ConverterLinearMeasure extends Converter
{
	private ConfigLoader load;
	
	/**
	 * Constructor loads all necessary files for unit LINEAR_MEASURE.
	 */	
	public ConverterLinearMeasure(ConfigLoader load)
	{
		super("LINEAR_MEASURE", load);
		this.load = load;
	}
	
	/**
	 * Derived method of superclass Converter for unit type LINEAR_MEASURE.
	 * 
	 * @param annotation
	 *            one annotation comprising at least one token of type
	 *            LINEAR_MEASURE
	 * @return list with all surface forms of an unit inclusive all
	 *         corresponding conversions
	 */
	@Override
	public ArrayList<String> convertUnits(BoaAnnotation annotation)
	{
		ArrayList<String> list = new ArrayList<String>();
		String tempUnit = null;
		
		// load surface forms
		Set<String> unitNames = load.openConfigSurfaceForms("LINEAR_MEASURE".toString());
		Set<String> Micrometer = load.openConfigSurfaceForms("MICROMETER".toString());
		Set<String> Millimeter = load.openConfigSurfaceForms("MILLIMETER".toString());
		Set<String> Centimeter = load.openConfigSurfaceForms("CENTIMETER".toString());
		Set<String> Decimeter = load.openConfigSurfaceForms("DECIMETER".toString());
		Set<String> Meter = load.openConfigSurfaceForms("METER".toString());
		Set<String> Kilometer = load.openConfigSurfaceForms("KILOMETER".toString());
		Set<String> Mile = load.openConfigSurfaceForms("MILE".toString());
		Set<String> Yard = load.openConfigSurfaceForms("YARD".toString());
		Set<String> SeaMile = load.openConfigSurfaceForms("SEAMILE".toString());
		Set<String> Inch = load.openConfigSurfaceForms("INCH".toString());
		Set<String> Feet = load.openConfigSurfaceForms("FEET".toString());
		
		List<String> list1 = new ArrayList<String>(unitNames);
		list1.remove("MATH");
		list1.remove("NUMBERS");
		Object[] allUnitsOfLinearMeasure = new Object[list1.size()];
		for (int i = 0; i < allUnitsOfLinearMeasure.length; i++)
		{
			allUnitsOfLinearMeasure[i] = list1.get(i).toLowerCase();
		}
		
		List<String> list2 = new ArrayList<String>(Micrometer);
		Object[] microm = list2.toArray();
		
		List<String> list3 = new ArrayList<String>(Millimeter);
		Object[] mm = list3.toArray();
		
		List<String> list4 = new ArrayList<String>(Centimeter);
		Object[] cm = list4.toArray();
		
		List<String> list5 = new ArrayList<String>(Decimeter);
		Object[] dm = list5.toArray();
		
		List<String> list6 = new ArrayList<String>(Meter);
		Object[] m = list6.toArray();
		
		List<String> list7 = new ArrayList<String>(Kilometer);
		Object[] km = list7.toArray();
		
		List<String> list8 = new ArrayList<String>(Mile);
		Object[] mile = list8.toArray();
		
		List<String> list9 = new ArrayList<String>(Yard);
		Object[] yard = list9.toArray();
		
		List<String> list10 = new ArrayList<String>(SeaMile);
		Object[] seamile = list10.toArray();
		
		List<String> list11 = new ArrayList<String>(Inch);
		Object[] inch = list11.toArray();
		
		List<String> list12 = new ArrayList<String>(Feet);
		Object[] ft = list12.toArray();
		
		/*
		 * String[] microm = { "µm", "micrometer", "micrometre" }; String[] mm =
		 * { "mm", "millimeter", "millimeters" }; String[] cm = { "cm",
		 * "centimeter", "centimeters" }; String[] dm = { "dm", "decimeters",
		 * "decimeter" }; String[] m = { "m", "meter", "meters", "metres" };
		 * String[] km = { "km", "kilometer", "kilometers" }; String[] inch = {
		 * "inch", "in", "inches", }; String[] ft = { "ft", "feet", "feet", "'"
		 * }; String[] yard = { "yard", "yards", "yd", "yds" }; String[] mile =
		 * { "mile", "miles" }; String[] seamile = { "sm", "seamile",
		 * "seamiles", "nautic mile", "nautic miles" };
		 * 
		 * String[] allUnitsOfLinearMeasure = { "µm", "mm", "cm", "dm", "m",
		 * "km", "mile", "seamile", "yard", "ft", "inch" };
		 */
		// get all tokens of annotation and choose unit
		for (int i = 0; i < annotation.size(); i++)
		{
			int chooseOption = 0;
			
			for (int l = 0; l < microm.length; l++)
			{
				if (microm[l].equals(annotation.getToken(i)))
				{
					tempUnit = "microm";
					chooseOption++;
				}
			}
			if (chooseOption == 0)
				for (int l = 0; l < mm.length; l++)
				{
					if (mm[l].equals(annotation.getToken(i)))
					{
						chooseOption++;
						tempUnit = "mm";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < cm.length; l++)
				{
					if (cm[l].equals(annotation.getToken(i)))
					{
						chooseOption++;
						tempUnit = "cm";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < dm.length; l++)
				{
					if (dm[l].equals(annotation.getToken(i)))
					{
						chooseOption++;
						tempUnit = "dm";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < m.length; l++)
				{
					if (m[l].equals(annotation.getToken(i)))
					{
						chooseOption++;
						tempUnit = "m";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < km.length; l++)
				{
					if (km[l].equals(annotation.getToken(i)))
					{
						chooseOption++;
						tempUnit = "km";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < ft.length; l++)
				{
					if (ft[l].equals(annotation.getToken(i)))
					{
						chooseOption++;
						tempUnit = "ft";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < inch.length; l++)
				{
					if (inch[l].equals(annotation.getToken(i)))
					{
						chooseOption++;
						tempUnit = "inch";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < yard.length; l++)
				{
					if (yard[l].equals(annotation.getToken(i)))
					{
						chooseOption++;
						tempUnit = "yard";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < seamile.length; l++)
				{
					if (seamile[l].equals(annotation.getToken(i)))
					{
						chooseOption++;
						tempUnit = "seamile";
					}
				}
			if (chooseOption == 0)
				for (int l = 0; l < mile.length; l++)
				{
					if (mile[l].equals(annotation.getToken(i)))
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
			for (int w = 0; w < annotation.size(); w++)
			{
				if (checkIfNumber(annotation.getToken(w)))
				{
					String token = annotation.getToken(w);
					
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
			for (int d = 0; d < allUnitsOfLinearMeasure.length; d++)
			{
				BigDecimal newNumber = standard.multiply(conversionUnit.get(allUnitsOfLinearMeasure[d]));
				
				 if (allUnitsOfLinearMeasure[d].equals("micrometer"))
				 {
				 for (int l = 0; l < microm.length; l++)
				 {
				 list.add(newNumber + " " + microm[l]);
				 }
				 }
				if (allUnitsOfLinearMeasure[d].equals("millimeter"))
				{
					for (int l = 0; l < mm.length; l++)
					{
						list.add(newNumber + " " + mm[l]);
					}
				}
				if (allUnitsOfLinearMeasure[d].equals("centimeterm"))
				{
					for (int l = 0; l < cm.length; l++)
					{
						list.add(newNumber + " " + cm[l]);
					}
				}
				if (allUnitsOfLinearMeasure[d].equals("decimeter"))
				{
					for (int l = 0; l < dm.length; l++)
					{
						list.add(newNumber + " " + dm[l]);
					}
				}
				if (allUnitsOfLinearMeasure[d].equals("meter"))
				{
					for (int l = 0; l < m.length; l++)
					{
						list.add(newNumber + " " + m[l]);
					}
				}
				if (allUnitsOfLinearMeasure[d].equals("kilometer"))
				{
					for (int l = 0; l < km.length; l++)
					{
						list.add(newNumber + " " + km[l]);
					}
				}
				if (allUnitsOfLinearMeasure[d].equals("feet"))
				{
					for (int l = 0; l < ft.length; l++)
					{
						list.add(newNumber + " " + ft[l]);
					}
				}
				if (allUnitsOfLinearMeasure[d].equals("inch"))
				{
					for (int l = 0; l < inch.length; l++)
					{
						list.add(newNumber + " " + inch[l]);
					}
				}
				if (allUnitsOfLinearMeasure[d].equals("yard"))
				{
					for (int l = 0; l < yard.length; l++)
					{
						list.add(newNumber + " " + yard[l]);
					}
				}
				if (allUnitsOfLinearMeasure[d].equals("seamile"))
				{
					for (int l = 0; l < seamile.length; l++)
					{
						list.add(newNumber + " " + seamile[l]);
					}
				}
				if (allUnitsOfLinearMeasure[d].equals("mile"))
				{
					for (int l = 0; l < mile.length; l++)
					{
						list.add(newNumber + " " + mile[l]);
					}
				}
			}
		}
		
		return list;
	}
	
}
