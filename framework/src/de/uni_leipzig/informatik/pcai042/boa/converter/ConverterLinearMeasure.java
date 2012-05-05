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

import java.util.ArrayList;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;

public class ConverterLinearMeasure extends Converter
{
	private String[] mm = { "nm", "nanometer", "nanometers" };
	private String[] cm = { "cm", "centimeter", "centimeters" };
	private String[] dm = { "dm", "decimeters", "decimeter" };
	private String[] m = { "m", "meter", "meters", "metres" };
	private String[] km = { "km", "kilometer", "kilometers" };
	private String[] ft = { "ft", "feet", "feet", "'" };
	private String[] inch = { "inch", "in", "inches", };
	private String[] yard = { "yard", "yards", "yd", "yds" };
	private String[] seamile = { "sm", "seamile", "seamiles", "nautic mile", "nautic miles" };
	private String[] mile = { "mile", "miles" };
	
	private String[] help4 = { "mm", "cm", "dm", "m", "km", "mile", "seamile", "yard", "ft", "inch" };
	
	/**
	 * Constructor loads all necessary files for unit weight.
	 * 
	 * @param unit
	 * @param file
	 */
	public ConverterLinearMeasure(String unit, String file)
	{
		super();
	}

	@Override
	public ArrayList<String> convertUnit(BoaAnnotation annotation)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
