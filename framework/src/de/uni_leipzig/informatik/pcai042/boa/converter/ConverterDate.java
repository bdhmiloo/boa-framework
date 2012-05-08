/*
 * ConverterDate.java
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

/**
 * Converter class for unit type DATE.
 * 
 * @author
 */
public class ConverterDate extends Converter
{
	/**
	 * Constructor.
	 * 
	 */
	public ConverterDate()
	{
		// TODO add code here
	}
	
	@Override
	protected void loadSurfaceForms(String file)
	{
		// do nothing, because DATE does not need to load surface forms
	}
	
	/**
	 * Derived method of superclass Converter for unit type DATE.
	 * 
	 * @param annotation
	 *            one annotation
	 * @return list with all surface forms of an unit inclusive all
	 *         corresponding conversions
	 */
	@Override
	public ArrayList<String> convertUnits(BoaAnnotation annotation)
	{
		ArrayList<String> list = new ArrayList<String>();
		
		// TODO add code here
		
		return list;
	}
	
}
