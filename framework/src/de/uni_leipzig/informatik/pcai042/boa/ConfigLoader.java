/*
 * ConfigLoader.java
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ConfigLoader
{
	private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
	
	private static final String sFormsLabel = "S_FORMS";
	private static final String fsFormsLabel = "F_S_FORMS";
	
	/*
	 * Function to return the String representation of unit surface forms from a text file.
	 * The second variable isFalse tells the class whether the method is called by the function
	 * searchUnit in LabelSearcher (isFalse = false) or the searchFalseUnit function which is supposed
	 * to find defective occurrences	public Set<String> openConfigSurfaceForms(File path, boolean isFalse)
     * So either the first or the second line of the configFile will be of interest. 
	 */
	
	public Set<String> openConfigSurfaceForms(File path, boolean isFalse)
	{
		HashSet<String> unity= new HashSet<String>();	
		Properties configForms = new Properties();
		String[]  unityTemp;
		
		try 
		{
			configForms.load(new FileInputStream(path));
		} 
		catch (FileNotFoundException e)
		{	
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	
		// S_FORMS and F_S_FORMS are termorary because they cannot be tested for now .
		if (!isFalse) {unityTemp = configForms.getProperty(sFormsLabel).split(";");}
		else {unityTemp = configForms.getProperty(fsFormsLabel).split(";");}

		
		for(int i=0; i<unityTemp.length;i++) 
		{
			unityTemp[i] = unityTemp[i].trim();
			unity.add(unityTemp[i]);
		}
		return unity;
	}
	
	/**
	 * Function to return a list of pairs of the unit's name and conversion factor into (direction = true) 
	 * or from (direction = false) the basic unit of the corresponding unit type extracted from 
	 * a text file at the given path.
	 * 
	 * @param specific Standard/Unit, first letter must be written large
	 * @param unitName Name of Unit (Weight, Temperature, ...), first letter must be written large
	 */
	
	public HashMap<String, BigDecimal> openConfigConversion(String specific, String unitName)
	{
		Properties properties = new Properties();
		HashMap<String, BigDecimal> conversionMap;
		
		//Try to create the full file name with merging. Only for default path (file in project directory)
		String fileName="conversion"+specific+unitName+".properties";
		
		try
		{
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(fileName));
			properties.load(stream);
			stream.close();
			
			conversionMap = new HashMap<String, BigDecimal>((Map) properties);
			
			return conversionMap;
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		
		return null;
	}
}
