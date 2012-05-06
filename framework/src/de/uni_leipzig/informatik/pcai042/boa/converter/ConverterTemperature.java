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

import java.util.ArrayList;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;

public class ConverterTemperature extends Converter
{
	/**
	 * Constructor loads all necessary files for unit temperature.
	 * 
	 * @param file
	 *            - name of file with annotations that should be loaded
	 */
	public ConverterTemperature(String file)
	{
		super("TEMPERATURE", file);
	}

	/**
	 * 
	 * @param annotation
	 * @return list with all surfaceforms of an unit inclusive all conversions
	 */
	@Override
	public ArrayList<String> convertUnit(BoaAnnotation annotation)
	{
		ArrayList<String> list = new ArrayList<String>();
		String tempUnit = null;
		
		String[] K = {};
		String[] C = {};
		String[] F = {};
		
		String[] allUnitsOfTemperature = { "K", "C", "F" };
		
		// add algo here
		
		
		return list;
	}
}
