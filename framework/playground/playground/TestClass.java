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
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import de.uni_leipzig.informatik.pcai042.boa.BoaAnnotation.Type;
import de.uni_leipzig.informatik.pcai042.boa.BoaSentence;
import de.uni_leipzig.informatik.pcai042.boa.ConfigLoader;
import de.uni_leipzig.informatik.pcai042.boa.NaiveAlgorithm;
import de.uni_leipzig.informatik.pcai042.boa.SearchAlgorithm;
import de.uni_leipzig.informatik.pcai042.boa.SentenceLoader;

public class TestClass
{	
	public static void main(String[] args)
	{
		int count = 0;
		ArrayList<BoaSentence> sentences = new ArrayList<BoaSentence>();
		BoaSentence testSentence;
		SentenceLoader sentenceLoader = null;
		try
		{
			sentenceLoader = new SentenceLoader(new File("goldstandard.xml"));
		} catch (ValidityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParsingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sentences = sentenceLoader.getSentences();
		
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
		testSetW.add("g");
		testSetW.add("gram");
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
		
		Iterator<BoaSentence> it = sentences.iterator();
		
		while(it.hasNext())
		{
			count++;
			
			testSentence = it.next();
			
			//System.out.println(testString);
			
			System.out.println("\nSentence " + count + "\n" + testSentence);
		
			testAlgo.search(testSentence, testSetLM, Type.LINEAR_MEASURE);
			testAlgo.search(testSentence, testSetW, Type.WEIGHT);
			testAlgo.search(testSentence, testSetT, Type.TEMPERATURE);
			System.out.println(testSentence.getAnnotations().toString());
			
		}
		
		//System.out.println("400km " + testAlgo.checkIfCombinedNumber("400km") + "\n1a" + testAlgo.checkIfCombinedNumber("1a")); 
	}

}
