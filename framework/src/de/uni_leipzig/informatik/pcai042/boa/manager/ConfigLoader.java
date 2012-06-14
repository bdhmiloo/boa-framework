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

package de.uni_leipzig.informatik.pcai042.boa.manager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigLoader
{
	private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
	
	private final File root;
	private Properties configForms;
	private HashMap<String, HashMap<String, BigDecimal>> conversions;
	
	/**
	 * Constructs a ConfigLoader searching for files in the working directory.
	 */
	public ConfigLoader()
	{
		this(new File("."));
	}
	
	/**
	 * Constructs a ConfigLoader searching for files passed directory.
	 * 
	 * @param file
	 *            the directory to search for files
	 */
	public ConfigLoader(File file)
	{
		root = file;
		configForms = new Properties();
		conversions = new HashMap<String, HashMap<String, BigDecimal>>();
		try
		{
			configForms.load(new InputStreamReader(new FileInputStream(new File(root, "sForms.properties"))));
		} catch (FileNotFoundException e)
		{
			logger.error("Main config file \"sForms.properties\" was not found.");
		} catch (IOException e)
		{
			logger.error("Main config file \"sForms.properties\" could not be loaded.");
		}
	}
	
	/**
	 * @param label
	 *            name of the line that the surface forms are listed (in the
	 *            .properties file)
	 * @return Set of Strings
	 */
	public Set<String> openConfigSurfaceForms(String label)
	{
		HashSet<String> unity = new HashSet<String>();
		String[] unityTemp;
		
		String property = configForms.getProperty(label);
		if (property != null)
		{
			unityTemp = property.split(";");
			
			for (int i = 0; i < unityTemp.length; i++)
			{
				unityTemp[i] = unityTemp[i].trim();
				if (!unityTemp[i].isEmpty())
					unity.add(unityTemp[i]);
			}
		} else
		{
			logger.warn("Could not find property with key \"{}\".", label);
		}
		return unity;
	}
	
	/**
	 * Function to return a list of pairs of the unit's name and conversion
	 * factor into (direction = true) or from (direction = false) the basic unit
	 * of the corresponding unit type extracted from a text file at the given
	 * path.
	 * 
	 * @param specific
	 *            Standard/Unit, first letter must be written large
	 * @param unitName
	 *            Name of Unit (Weight, Temperature, ...), first letter must be
	 *            written large
	 */
	public HashMap<String, BigDecimal> openConfigConversion(String specific, String unitName)
	{
		HashMap<String, BigDecimal> conversionMap;
		
		// Try to create the full file name with merging. Only for default path
		// (file in project directory)
		String fileName = "conversion" + specific + unitName + ".properties";
		
		conversionMap = conversions.get(fileName);
		
		if (conversionMap != null)
		{
			return conversionMap;
		}
		try
		{
			Properties properties = new Properties();
			BufferedInputStream stream = new BufferedInputStream(new FileInputStream(new File(root, fileName)));
			properties.load(stream);
			stream.close();
			
			conversionMap = new HashMap<String, BigDecimal>();
			for (String key : properties.stringPropertyNames())
			{
				conversionMap.put(key, new BigDecimal(properties.getProperty(key).trim()));
			}
			
			return conversionMap;
		} catch (NumberFormatException e)
		{
			logger.error("NumberFormatException when creating BigDecimal from " + fileName);
		} catch (IOException e)
		{
			logger.error("Config file \"{}\" was not found.", fileName);
		}
		
		return null;
	}
	
	/**
	 * Returns the String representation of a value from a labeled property.
	 * 
	 * @param label
	 * @return value of the labeled property
	 */
	public String returnValue(String label)
	{
		return configForms.getProperty(label);
	}
}
