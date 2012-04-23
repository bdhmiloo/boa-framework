/*
 * TestClass.java
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import playground.BoaAnnotation.Type;

public class TestClass
{
	private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
	
	public static void main(String[] args)
	{
		String testString, next, last;
		int count = 0;
		testString = fileToString(new File("Test.txt"));
		
		//create Algo
		SearchAlgorithm testAlgo = new NaiveAlgorithm();
		
		//create HashSets of surfFroms to search for
		//this will be replaced as soon as ConfigLoader works
		//1: LINEAR_MEASURE
		Set<String> testSetLM = new HashSet<String>();
		testSetLM.add("meters");
		testSetLM.add("metres");
		testSetLM.add("miles");
		testSetLM.add("yards");
		testSetLM.add("km");
		testSetLM.add("m");
		testSetLM.add("millimeters");
		testSetLM.add("millimetres");
		testSetLM.add("mm");
		testSetLM.add("centimeters");
		testSetLM.add("centimetres");
		testSetLM.add("cm");
		testSetLM.add("nanometers");
		testSetLM.add("nanometres");
		testSetLM.add("nm");
		testSetLM.add("inches");
		testSetLM.add("ft");
		testSetLM.add("feet");
		
		//2: WEIGHT
		Set<String> testSetW = new HashSet<String>();
		testSetW.add("kg");
		testSetW.add("kilogram");
		testSetW.add("tons");
		testSetW.add("ounze");
		testSetW.add("pounds");
		testSetW.add("labs");
		testSetW.add("lbs");
		
		//3: TEMPERATURE
		Set<String> testSetT = new HashSet<String>();
		testSetT.add("Kelvin");
		testSetT.add("K");
		testSetT.add("°C");
		testSetT.add("°F");
		
		do
		{
			count++;
			
			next = nextSentence(testString);
			last = testString;
			if(!testString.equals(next))testString = testString.substring(next.length());
			if(testString.startsWith("\n")) testString = testString.substring(1);
			
			//System.out.println(testString);
			
			System.out.println("\nSentence " + count + "\n" + next);
			
			//create BoaSentence for tests
			BoaSentence testSentence = new BoaSentence(next);
		
			testAlgo.search(testSentence, testSetLM, Type.LINEAR_MEASURE);
			testAlgo.search(testSentence, testSetW, Type.WEIGHT);
			testAlgo.search(testSentence, testSetT, Type.TEMPERATURE);
			System.out.println(testSentence.getAnnotations().toString());
			
		}while(!testString.equals(last));
		
		//System.out.println("400km " + testAlgo.checkIfCombinedNumber("400km") + "\n1a" + testAlgo.checkIfCombinedNumber("1a")); 
	}
	
	private static String fileToString(File file)
	{
		int nextchar;
		StringBuilder stringBuilder = new StringBuilder();
		
		try
		{
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			do
			{
				nextchar = reader.read();
				if (nextchar != -1)
					stringBuilder.append((char) (nextchar));
				
			} while (nextchar != -1);
			
			reader.close();
			return stringBuilder.toString().trim();
		} catch (FileNotFoundException e)
		{
			logger.error(e.getMessage());
		} catch (IOException e)
		{
			logger.error(e.getMessage());
		}
		
		return "";
	}
	
	private static String nextSentence(String text)
	{
		if (text.isEmpty())
			return null;
		return text.indexOf('\n') > -1 ? text.substring(0, text.indexOf('\n')) : text;
	}

}
