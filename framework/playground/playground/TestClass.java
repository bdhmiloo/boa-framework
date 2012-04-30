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
import de.uni_leipzig.informatik.pcai042.boa.NaiveAlgorithm;
import de.uni_leipzig.informatik.pcai042.boa.SearchThread;
import de.uni_leipzig.informatik.pcai042.boa.SentenceLoader;

public class TestClass
{	
	public static void main(String[] args)
	{		
		int count = 0;
		ArrayList<BoaSentence> sentences = new ArrayList<BoaSentence>();
		SentenceLoader sentenceLoader = null;
		BoaSentence nextSentence;
		
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
		
		SearchThread thread1, thread2, thread3;
		
		try
		{
			//create Searchers
			LabelSearcher lsLinMeasure = new LabelSearcher(Type.LINEAR_MEASURE, "LINEAR_MEASURE_N", new NaiveAlgorithm());
			thread1 = new SearchThread(lsLinMeasure, sentences);
			thread1.start();
			
			/*
			LabelSearcher lsWeight = new LabelSearcher(Type.WEIGHT,"WEIGHT_N", new NaiveAlgorithm());
			thread2 = new SearchThread(lsWeight, sentences);
			thread2.start();
			
			LabelSearcher lsTemp = new LabelSearcher(Type.TEMPERATURE, "TEMPERATURE_N", new NaiveAlgorithm());
			thread3 = new SearchThread(lsTemp, sentences);
			thread3.start();*/
			
			while(thread1.isAlive()/*&&thread2.isAlive()&&thread3.isAlive()*/)
			{
				//wait
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		Iterator<BoaSentence> it = sentences.iterator();
		
		while(it.hasNext())
		{
			count++;
			nextSentence = it.next();
			
			System.out.println("\nSentence " + count + "\n");
			System.out.println(nextSentence.getSentence() + "\n" + nextSentence.getAnnotations().toString());
			
		}
	}
}
