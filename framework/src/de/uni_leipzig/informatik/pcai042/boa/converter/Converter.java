/*
 * Converter.java
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
import java.util.HashMap;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;

/**
 * 
 * @author Duc Huy Bui; Christian Kahmann
 */
public abstract class Converter
{
	private HashMap<String, BigDecimal> conversionStandard;
	private HashMap<String, BigDecimal> conversionUnit;
	
	/**
	 * 
	 * 
	 * @param token
	 * @param number
	 */
	public Converter(String token, BigDecimal number)
	{
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	public Converter()
	{
		
	}
	
	/**
	 * 
	 * @param annotation
	 * @return
	 */
	public ArrayList<String> convertUnit(BoaAnnotation annotation)
	{
		
		// null --> placeholder for return
		return null;
	}
	
	/**
	 * Test
	 */
	public static void main(String arg[])
	{
		
	}
}
