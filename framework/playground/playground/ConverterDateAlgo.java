/*
 * ConverterDateAlgo.java
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import de.uni_leipzig.informatik.pcai042.boa.converter.ConverterDate;
import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;
import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;
import de.uni_leipzig.informatik.pcai042.boa.manager.SentenceLoader;

/**
 * Testing of ConverterDate.
 * 
 * @author Duc Huy Bui
 */
public class ConverterDateAlgo
{
	/**
	 * 
	 */
	public ConverterDateAlgo()
	{
		
	}
	
	/**
	 * Derived method of superclass Converter for unit type DATE.
	 * 
	 * @param annotation
	 *            one annotation comprising at least one token of type DATE
	 * @return list with all surface forms of an unit inclusive all
	 *         corresponding conversions
	 */
	public ArrayList<String> convertUnits(BoaAnnotation annotation)
	{
		ArrayList<String> list = new ArrayList<String>();
		
		// ConfigLoader load = new ConfigLoader();
		
		// TODO load all surfaceforms
		// Set<String> month = load.openConfigSurfaceForms("MONTH".toString());
		// Set<String> day = load.openConfigSurfaceForms("DAY".toString());
		//
		// ArrayList<String> monthList = new ArrayList<String>(month);
		// ArrayList<String> dayList = new ArrayList<String>(day);
		
		// TODO remove string arrays if loading surface forms from file is
		// possible
		String[] month = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		
		// get all tokens of annotation and choose unit
		for (int i = 0; i < annotation.getTokens().size(); i++)
		{
			
			// annotation.getTokens().get(i).replaceAll("'|\"|,|-|\\.|/|~", "");
			
			// TODO test if token is a number
			
			// TODO token is not a number --> must be some kind of string
			
			// TODO convert to other surfaceforms (numbers)
			
			// TODO convert to other surfacforms (strings)
			
		}
		
		return list;
	}
	
	/**
	 * 
	 * 
	 * @param dateString
	 * @param fromPattern
	 * @param toPattern
	 * @return
	 * @throws ParseException
	 */
	private String getDateString(String dateString, String fromPattern, String toPattern) throws ParseException
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fromPattern);
		Date date1 = simpleDateFormat.parse(dateString);
		simpleDateFormat.applyPattern(toPattern);
		
		return simpleDateFormat.format(date1);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ConverterDate conDATE = new ConverterDate();
		
		// result of conversions
		ArrayList<String> result = new ArrayList<String>();
		
		SentenceLoader sentence = null;
		
		// initializes output file
		try
		{
			System.setOut(new PrintStream(new FileOutputStream(new File("testConverterDateAlgo.txt"), true)));
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
