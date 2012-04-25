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
import java.util.Iterator;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

import de.uni_leipzig.informatik.pcai042.boa.BoaAnnotation.Type;
import de.uni_leipzig.informatik.pcai042.boa.BoaSentence;
import de.uni_leipzig.informatik.pcai042.boa.LabelSearcher;
import de.uni_leipzig.informatik.pcai042.boa.LabelSearcherLinearMeasure;
import de.uni_leipzig.informatik.pcai042.boa.LabelSearcherTemperature;
import de.uni_leipzig.informatik.pcai042.boa.LabelSearcherWeight;
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
		
		//create Searchers
		LabelSearcher lsLinMeasure = new LabelSearcherLinearMeasure(Type.LINEAR_MEASURE,"SF_LINEAR_MEASURE.properties");
		LabelSearcher lsWeight = new LabelSearcherWeight(Type.WEIGHT,"SF_WEIGHT.properties");
		LabelSearcher lsTemp = new LabelSearcherTemperature(Type.TEMPERATURE, "SF_TEMPERATURE.properties");
		
		Iterator<BoaSentence> it = sentences.iterator();
		
		while(it.hasNext())
		{
			count++;
			
			testSentence = it.next();
			
			//System.out.println(testString);
			
			System.out.println("\nSentence " + count + "\n" + testSentence);
		
			//lsLinMeasure.searchUnit(testSentence);
			lsWeight.searchUnit(testSentence);
			//lsTemp.searchUnit(testSentence);
			System.out.println(testSentence.getAnnotations().toString());
			
		}
	}
}
