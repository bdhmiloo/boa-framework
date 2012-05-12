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
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

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
	 * Checks whether token is a number or not.
	 * 
	 * @param token
	 *            one token
	 * @return true - if token is a number, false - if token is not a number
	 */
	public boolean checkIfNumber(String token)
	{
		try
		{
			Integer.parseInt(token);
			return true;
		} catch (NumberFormatException e)
		{
			// TODO catch exception or log
		}
		
		try
		{
			if (token.contains(","))
				token = token.replace(",", ".");
			Double.parseDouble(token);
			return true;
		} catch (NumberFormatException e)
		{
			// TODO catch exception or log
		}
		return false;
	}
	
	/**
	 * Derived method of superclass Converter for unit type DATE.
	 * 
	 * @param annotation
	 *            one annotation comprising at least one token of type DATE
	 * @return list with all surface forms of an unit inclusive all
	 *         corresponding conversions
	 * @throws ParseException
	 */
	public ArrayList<String> convertUnits(BoaAnnotation annotation) throws ParseException
	{
		ArrayList<String> list = new ArrayList<String>();
		
		ConfigLoader load = new ConfigLoader();
		
		// TODO load all surfaceforms
		
		// TODO remove string arrays if loading surface forms from file is
		// possible
		String[] monthList = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		String[] monthAbbreviation = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov",
				"Dec" };
		String[] dateSeparater = { "/" , "|" , "-" , "'" , "~" , "."};
		
		// get all tokens of annotation and choose unit
		for (int i = 0; i < annotation.getTokens().size(); i++)
		{
			String currentToken = annotation.getTokens().get(i);
			String copyOfToken = currentToken.replaceAll("\\W", "");
			// replaceAll("'|\"|,|-|\|.|/|~", "");
			
			// date pattern
			String pattern = null;
			
			// position of d(ay), M(onth) or y(ear) in date pattern
			String day = null, month = null, year = null;
			
			if (checkIfNumber(copyOfToken))
			{
				// token consists just of numbers
				currentToken.replaceAll("\\W", ".");
				
				String[] datePos = currentToken.split(".");
				
				day = datePos[1];
				month = datePos[2];
				year = datePos[3];
				
				// define date pattern
				if (1 <= Integer.valueOf(day) && Integer.valueOf(day) <= 12 && 
					12 < Integer.valueOf(month) && Integer.valueOf(month) <= 31)
				{
					pattern = "MM.dd.yyyy";
					String temp = day;
					day = month;
					month = temp;
				} 
				else if (31 < Integer.valueOf(day) && 
						1 <= Integer.valueOf(month) && Integer.valueOf(month) <= 12 && 
						1 <= Integer.valueOf(year) && Integer.valueOf(year) <= 31)
				{
					pattern = "yyyy.MM.dd";
					String temp2 = day;
					day = year;
					year = temp2;
					
				} 
				else
				{
					pattern = "dd.MM.yyyy";
				}
				
			} else
			{
				// token consists of several strings
				
			}
			
			Format formatter;
			
			// TODO convert to other surfaceforms (numbers)
			
			
			// TODO convert to other surfacforms (strings)
			
		}
		
		return list;
	}
	
	/**
	 * 
	 * 
	 * @author Daniel Gerber
	 * @see
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
	 * 
	 * 
	 * @author Daniel Gerber
	 * @see
	 * @param value
	 * @return
	 */
	private String getOrdinalFor(int value)
	{
		int hundredRemainder = value % 100;
		int tenRemainder = value % 10;
		if (hundredRemainder - tenRemainder == 10)
		{
			return "th";
		}
		
		switch (tenRemainder)
		{
			case 1:
				return "st";
			case 2:
				return "nd";
			case 3:
				return "rd";
			default:
				return "th";
		}
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
