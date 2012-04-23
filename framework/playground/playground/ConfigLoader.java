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

package playground;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

// i write down what i have done, to be proved by the person responsible.(Giorgos)
import java.util.Properties;
import java.io.PrintWriter;
import java.io.StringWriter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ConfigLoader
{
	private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
	
	/*
	 * Function to return the String representation of unit surface forms from a text file.
	 * The second variable isFalse tells the class whether the method is called by the function
	 * searchUnit in LabelSearcher (isFalse = false) or the searchFalseUnit function which is supposed
	 * to find defective occurrences of the corresponding units (isFalse = true).
	 * So either the first or second line of the configFile will be of interest. 
	 */
	
	public ArrayList<String> openConfigSurfaceForms(File path, boolean isFalse)
	{
	
		ArrayList<String> unity= new ArrayList<String>();	
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
		if (isFalse) {unityTemp = configForms.getProperty("S_FORMS").split(";");}
		else {unityTemp = configForms.getProperty("F_S_FORMS").split(";");}

		
		for(int i=0; i<unityTemp.length;i++) 
		{
			unityTemp[i] = unityTemp[i].trim().toLowerCase();
			unity.add(unityTemp[i]);
		}
		return unity;
		
	}
	
	
	/*
	 * Function to return a list of pairs of the unit's name and conversion factor into (direction = true) 
	 * or from (direction = false) the basic unit of the corresponding unit type extracted from 
	 * a text file at the given path.
	 */
	
	public HashMap<String, BigDecimal> openConfigConversion(File path, boolean direction)
	{
		return null;
	}
}
