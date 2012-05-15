/*
 * TestConverter.java
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import de.uni_leipzig.informatik.pcai042.boa.converter.ConverterDate;
import de.uni_leipzig.informatik.pcai042.boa.converter.ConverterLinearMeasure;
import de.uni_leipzig.informatik.pcai042.boa.converter.ConverterTemperature;
import de.uni_leipzig.informatik.pcai042.boa.converter.ConverterWeight;
import de.uni_leipzig.informatik.pcai042.boa.manager.SentenceLoader;

/**
 * Testing functionality of all Converter classes focused on convertUnits(..)
 * methods.
 * 
 * @author Duc Huy Bui; Christian Kahmann
 */
public class TestConverter
{
	
	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException
	{
		ConverterWeight conWEIGHT = new ConverterWeight();
		ConverterLinearMeasure conLINEAR_MEASURE = new ConverterLinearMeasure();
		ConverterTemperature conTEMPERATURE = new ConverterTemperature();
		ConverterDate conDATE = new ConverterDate();
		
		// result of conversions
		ArrayList<String> result = new ArrayList<String>();
		
		SentenceLoader sentence = null;
		
		// initializes output file
		try
		{
			System.setOut(new PrintStream(new FileOutputStream(new File("testConverter.txt"), true)));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		// load annotations
		try
		{
			sentence = new SentenceLoader(new File("goldstandard.xml"));
		} catch (ValidityException e)
		{
			e.printStackTrace();
		} catch (ParsingException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		if (sentence == null)
			return;
		
		int count = 0;
		
		// get all sentences
		for (int i = 0; i < sentence.getSentenceCount(); i++)
		{
			
			// get all annotations
			for (int k = 0; k < sentence.getSentence(i).getAnnotations().size(); k++)
			{
				result.add("sentence:" + i + "  anno:" + k);
				result.add("________________________");
				
				// conversion WEIGHT
				if (sentence.getSentence(i).getAnnotations().get(k).getType().toString() == "WEIGHT")
				{
					// TODO test method here
					result.addAll(conWEIGHT.convertUnits(sentence.getSentence(i).getAnnotations().get(k)));
					count++;
				}
				
				// conversion LINEAR MEASURE
				if (sentence.getSentence(i).getAnnotations().get(k).getType().toString() == "LINEAR_MEASURE")
				{
					// TODO test method here
					result.addAll(conLINEAR_MEASURE.convertUnits(sentence.getSentence(i).getAnnotations().get(k)));
					count++;
				}
				
				// conversion TEMPERATURE
				if (sentence.getSentence(i).getAnnotations().get(k).getType().toString() == "TEMPERATURE")
				{
					// TODO test method here
					result.addAll(conTEMPERATURE.convertUnits(sentence.getSentence(i).getAnnotations().get(k)));
					count++;
				}
				
				// conversion DATE
				if (sentence.getSentence(i).getAnnotations().get(k).getType().toString() == "DATE")
				{
					// TODO test method here
					result.addAll(conDATE.convertUnits(sentence.getSentence(i).getAnnotations().get(k)));
					count++;
				}
				result.add("________________________");
			}
		}
		
		// Printout to file 'testConverter.txt'
		Iterator<String> it = result.iterator();
		
		while (it.hasNext())
		{
			System.out.println(it.next());
		}
		
		System.out.println(count + " annotations of 981 in total could be processed");
		System.out.println(result.size() + " surface forms could be loaded");
	}
	
}
