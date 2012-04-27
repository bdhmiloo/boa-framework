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

package de.uni_leipzig.informatik.pcai042.boa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;


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
	 */
	public Converter()
	{
		// TODO Auto-generated constructor stub
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
}
