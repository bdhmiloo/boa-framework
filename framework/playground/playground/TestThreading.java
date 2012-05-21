/*
 * TestThreading.java
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
import java.io.FileNotFoundException;
import java.io.FileReader;


import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;
import de.uni_leipzig.informatik.pcai042.boa.manager.BoaSentence;
import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;

import de.uni_leipzig.informatik.pcai042.boa.threading.SimpleStreamDistributor;

public class TestThreading
{
	
	public static void main(String[] args)
	{
		
		/*SentenceLoader sl = null;
		try
		{
			sl = new SentenceLoader(new File("goldstandard.xml"));
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
		ArrayList<BoaSentence> sentences = new ArrayList<BoaSentence>();
		for (BoaSentence sentence : sl.getSentences())
		{
			sentences.add(sentence.copy());
		}
		
		SimpleSentenceDistributor distr = new SimpleSentenceDistributor(sentences, 2, 50, new ConfigLoader());*/
		
		SimpleStreamDistributor distr = null;
		try
		{
			distr = new SimpleStreamDistributor(new FileReader(new File("test.txt")), 2,  new ConfigLoader());
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long start = System.currentTimeMillis(); 
		distr.run();
		while (!distr.hasFinished());
		
		int annoCountDate = 0;
		int annoCountWeight = 0;
		int annoCountTemperature = 0;
		int annoCountLinearM = 0;
		for (BoaSentence sentence : distr.getResult())
		{
			System.out.println(sentence.getSentence());
			for (BoaAnnotation anno : sentence.getAnnotations())
			{
				System.out.print("    " + anno.toString() + ",");
				if (anno.getType().equals("DATE"))
				{
					annoCountDate++;
				}
				if (anno.getType().equals("WEIGHT"))
				{
					annoCountWeight++;
				}
				if (anno.getType().equals("TEMPERATURE"))
				{
					annoCountTemperature++;
				}
				if (anno.getType().equals("LINEAR_MEASURE"))
				{
					annoCountLinearM++;
				}
				
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Weight " + annoCountWeight);
		System.out.println("Temperature " + annoCountTemperature);
		System.out.println("Date " + annoCountDate);
		System.out.println("LinearMeasure " + annoCountLinearM);
		System.out.println();
		System.out.println("time: " + (System.currentTimeMillis() - start));
	}
}
