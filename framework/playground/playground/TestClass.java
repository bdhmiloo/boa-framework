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

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaSentence;
import de.uni_leipzig.informatik.pcai042.boa.manager.SentenceLoader;
import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation.Type;
import de.uni_leipzig.informatik.pcai042.boa.searcher.LabelSearcher;
import de.uni_leipzig.informatik.pcai042.boa.searcher.NaiveAlgorithm;
import de.uni_leipzig.informatik.pcai042.boa.searcher.SearchThread;

public class TestClass
{	
	public static void main(String[] args)
	{		
		int count = 0, annoCount = 0, lmCount = 0, wCount = 0, tCount = 0, hilfsCount = 0;
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
		
		/*SearchThread thread1, thread2, thread3;
		
		try
		{
			//create Searchers
			LabelSearcher lsLinMeasure = new LabelSearcher(Type.LINEAR_MEASURE, "LINEAR_MEASURE_N", new NaiveAlgorithm());
			thread1 = new SearchThread(lsLinMeasure, sentences);
			thread1.start();
			
			
			LabelSearcher lsWeight = new LabelSearcher(Type.WEIGHT,"WEIGHT_N", new NaiveAlgorithm());
			thread2 = new SearchThread(lsWeight, sentences);
			thread2.start();
			
			LabelSearcher lsTemp = new LabelSearcher(Type.TEMPERATURE, "TEMPERATURE_N", new NaiveAlgorithm());
			thread3 = new SearchThread(lsTemp, sentences);
			thread3.start();
			
			while(thread1.isAlive()&&thread2.isAlive()&&thread3.isAlive())
			{
				//wait
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}*/
		
		Iterator<BoaSentence> it = sentences.iterator();
		
		while(it.hasNext())
		{
			count++;
			nextSentence = it.next();
			annoCount-= nextSentence.getAnnotations().size();
			hilfsCount+= nextSentence.getAnnotations().size();
			
			LabelSearcher lsLinMeasure = new LabelSearcher(Type.LINEAR_MEASURE, "LINEAR_MEASURE_N", new NaiveAlgorithm());
			LabelSearcher lsWeight = new LabelSearcher(Type.WEIGHT,"WEIGHT_N", new NaiveAlgorithm());
			LabelSearcher lsTemp = new LabelSearcher(Type.TEMPERATURE, "TEMPERATURE_N", new NaiveAlgorithm());
			
			lsLinMeasure.searchUnit(nextSentence);
			lmCount+= nextSentence.getAnnotations().size();
			
			lsWeight.searchUnit(nextSentence);
			wCount+= nextSentence.getAnnotations().size();
			
			lsTemp.searchUnit(nextSentence);
			
			annoCount+= nextSentence.getAnnotations().size();
			
			System.out.println("\nSentence " + count + "\n");
			System.out.println(nextSentence.getSentence() + "\n" + nextSentence.getAnnotations().toString());		
		}
		
		lmCount = -hilfsCount + lmCount;
		wCount = -hilfsCount - lmCount + wCount;
		tCount = annoCount - lmCount - wCount;
		
		System.out.println("\nHinzugefügte Annotationen (Gesamt): " + annoCount);
		System.out.println("Davon:\n" + lmCount + " Linear Measure\n" + wCount + " Weight \n" + tCount + " Temperature");
	}
}
