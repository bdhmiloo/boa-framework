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
import java.util.Locale;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;
import de.uni_leipzig.informatik.pcai042.boa.manager.SentenceLoader;

/**
 * Testing of ConverterDate.
 * 
 * @author Duc Huy Bui
 */
public class ConverterDateAlgo
{
	// TODO load all surface forms from file and remove string arrays later
	String[] monthAbbreviation = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
	String[] ordinalNumbers = { "first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth", "ninth",
			"tenth", "eleventh", "twelfth", "thirteenth", "fourteenth", "fifteenth", "sixteenth", "seventeenth",
			"eighteenth", "nineteenth", "twentieth", "twenty-first", "twenty-second", "twenty-third", "twenty-fourth",
			"twenty-fifth", "twenty-sixth", "twenty-seventh", "twenty-eighth", "twenty-ninth", "thirtieth",
			"thirty-first" };
	String[] dateSeparater = { "/", "-", "'", "~", "." };
	
	/**
	 * Constructor
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
		
		String pattern = null;
		Boolean beginConversion = false;
		
		// get complete annotation
		String stringBufferAnno = "";
		
		for (int j = 0; j < annotation.getTokens().size(); j++)
		{
			if (j == annotation.getTokens().size() - 1)
			{
				stringBufferAnno += annotation.getTokens().get(j);
			} else
			{
				stringBufferAnno += annotation.getTokens().get(j) + ".";
			}
		}
		
		String copyOfStringBuf = stringBufferAnno.replaceAll("/|-|'|~|\\.", "");
		
		// TODO
		// System.out.println("TEST - stringBufferAnno: " + stringBufferAnno);
		
		// annotation consists just of numbers (date separaters do not count)
		if (checkIfNumber(copyOfStringBuf) && stringBufferAnno.length() >= 6)
		{
			stringBufferAnno = stringBufferAnno.replaceAll("/|-|'|~", ".");
			
			String[] datePos = stringBufferAnno.split("\\.");
			
			String day = datePos[0];
			String month = datePos[1];
			String year = datePos[2];
			
			// define date pattern
			if (1 <= Integer.valueOf(day) && Integer.valueOf(day) <= 12 && 12 < Integer.valueOf(month)
					&& Integer.valueOf(month) <= 31)
			{
				pattern = "MM.dd.yyyy";
				String temp = day;
				day = month;
				month = temp;
				
				beginConversion = true;
			} else if (31 < Integer.valueOf(day) && 1 <= Integer.valueOf(month) && Integer.valueOf(month) <= 12
					&& 1 <= Integer.valueOf(year) && Integer.valueOf(year) <= 31)
			{
				pattern = "yyyy.MM.dd";
				String temp2 = day;
				day = year;
				year = temp2;
				
				beginConversion = true;
			} else
			{
				pattern = "dd.MM.yyyy";
				
				beginConversion = true;
			}
			
			// TODO
			// System.out.println("TEST - pattern: " + pattern);
			
		}
		
		// annotation is a number (year)
		else if (checkIfNumber(copyOfStringBuf) && 1 <= stringBufferAnno.length() && stringBufferAnno.length() <= 4)
		{
			beginConversion = false;
			
			// TODO future: expand algorithm if necessary
		}
		
		// annotation consists of some strings and numbers
		else
		{
			stringBufferAnno = stringBufferAnno.replaceAll(",\\.| |on\\.|the\\.|of\\.", "");
			
			// replacing some parts of ordinal numbers
			for (int j = 1; j <= 31; j++)
			{
				stringBufferAnno = stringBufferAnno.replaceAll(j + getOrdinalFor(j), Integer.toString(j));
				
				// absolute cleaning
				stringBufferAnno = stringBufferAnno.replaceAll(j + "th", Integer.toString(j));
				stringBufferAnno = stringBufferAnno.replaceAll(ordinalNumbers[j - 1], Integer.toString(j));
			}
			
			// TODO
			// System.out.println("Test - stringBufferAnno: " +
			// stringBufferAnno);
			
			String[] datePos = stringBufferAnno.split("\\.");
			
			int markMonth = 0;
			
			for (int j = 0; j < datePos.length; j++)
			{
				if (checkIfNumber(datePos[j]) == false)
				{
					if (convertMonthToNumber(datePos[j], monthAbbreviation) != 0)
					{
						datePos[j] = Integer.toString(convertMonthToNumber(datePos[j], monthAbbreviation));
						markMonth = j;
					}
				}
			}
			
			String day, month, year;
			
			if (datePos.length == 3)
			{
				day = datePos[(markMonth + 1) % 3]; // y or d
				month = datePos[markMonth];
				year = datePos[(markMonth + 2) % 3]; // y or d
				
				// define date pattern
				if (31 < Integer.valueOf(day) && 1 <= Integer.valueOf(month) && Integer.valueOf(month) <= 12
						&& 1 <= Integer.valueOf(year) && Integer.valueOf(year) <= 31)
				{
					pattern = "yyyy.MM.dd";
					String temp2 = day;
					day = year;
					year = temp2;
					
					stringBufferAnno = year + "." + month + "." + day;
					beginConversion = true;
				} else
				{
					pattern = "dd.MM.yyyy";
					
					stringBufferAnno = day + "." + month + "." + year;
					beginConversion = true;
				}
				
				// TODO
				// System.out.println("TEST - pattern: " + pattern);
				
			}
		}
		
		if (beginConversion)
		{
			conversionOfDate(list, stringBufferAnno, pattern, dateSeparater, ordinalNumbers);
		}
		
		return list;
	}
	
	/**
	 * Convert a month string to the particular number {1;...;12} which a month
	 * represents.
	 * 
	 * @param month
	 * @param sForms
	 * @return month as number
	 */
	private int convertMonthToNumber(String month, String[] sForms)
	{
		for (int j = 0; j < sForms.length; j++)
		{
			if (month.startsWith(sForms[j]))
			{
				return j + 1;
			}
		}
		
		return 0;
	}
	
	/**
	 * Convert dates to other dates with different surface forms.
	 * 
	 * @param list
	 * @param stringBufferAnno
	 * @param pattern
	 * @throws ParseException
	 */
	private void conversionOfDate(ArrayList<String> list, String stringBufferAnno, String pattern,
			String[] dateSeparater, String[] ordinalNumbers) throws ParseException
	{
		String d = getDateString(stringBufferAnno, pattern, "d");
		String dd = null;
		String M = getDateString(stringBufferAnno, pattern, "M");
		String MM = null;
		String MMM = getDateString(stringBufferAnno, pattern, "MMM");
		String MMMM = getDateString(stringBufferAnno, pattern, "MMMM");
		String yy = getDateString(stringBufferAnno, pattern, "yy");
		String yyyy = getDateString(stringBufferAnno, pattern, "yyyy");
		
		// add "0" to day or to month in case of one-digit numbers
		Boolean enableDD = false, enableMM = false;
		
		if (1 <= Integer.valueOf(d) && Integer.valueOf(d) <= 9)
		{
			dd = "0" + d;
			enableDD = true;
		}
		if (1 <= Integer.valueOf(M) && Integer.valueOf(M) <= 9)
		{
			MM = "0" + M;
			enableMM = true;
		}
		
		// convert to other surface forms (just numbers)
		for (int p = 0; p < dateSeparater.length; p++)
		{
			// pattern DAY.MONTH.YEAR
			
			list.add(d + dateSeparater[p] + M + dateSeparater[p] + yy);
			list.add(d + dateSeparater[p] + M + dateSeparater[p] + yyyy);
			
			if (enableDD)
			{
				list.add(dd + dateSeparater[p] + M + dateSeparater[p] + yy);
				list.add(dd + dateSeparater[p] + M + dateSeparater[p] + yyyy);
			}
			if (enableMM)
			{
				list.add(d + dateSeparater[p] + MM + dateSeparater[p] + yy);
				list.add(d + dateSeparater[p] + MM + dateSeparater[p] + yyyy);
			}
			if (enableDD && enableMM)
			{
				list.add(dd + dateSeparater[p] + MM + dateSeparater[p] + yy);
				list.add(dd + dateSeparater[p] + MM + dateSeparater[p] + yyyy);
			}
			
			// pattern MONTH.DAY.YEAR
			
			list.add(M + dateSeparater[p] + d + dateSeparater[p] + yy);
			list.add(M + dateSeparater[p] + d + dateSeparater[p] + yyyy);
			
			if (enableDD)
			{
				list.add(M + dateSeparater[p] + dd + dateSeparater[p] + yy);
				list.add(M + dateSeparater[p] + dd + dateSeparater[p] + yyyy);
			}
			if (enableMM)
			{
				list.add(MM + dateSeparater[p] + d + dateSeparater[p] + yy);
				list.add(MM + dateSeparater[p] + d + dateSeparater[p] + yyyy);
			}
			if (enableDD && enableMM)
			{
				list.add(MM + dateSeparater[p] + dd + dateSeparater[p] + yy);
				list.add(MM + dateSeparater[p] + dd + dateSeparater[p] + yyyy);
			}
			
			// pattern YEAR.MONTH.DAY
			
			list.add(yy + dateSeparater[p] + M + dateSeparater[p] + d);
			list.add(yyyy + dateSeparater[p] + M + dateSeparater[p] + d);
			
			if (enableDD)
			{
				list.add(yy + dateSeparater[p] + M + dateSeparater[p] + dd);
				list.add(yyyy + dateSeparater[p] + M + dateSeparater[p] + dd);
			}
			if (enableMM)
			{
				list.add(yy + dateSeparater[p] + MM + dateSeparater[p] + d);
				list.add(yyyy + dateSeparater[p] + MM + dateSeparater[p] + d);
			}
			if (enableDD && enableMM)
			{
				list.add(yy + dateSeparater[p] + MM + dateSeparater[p] + dd);
				list.add(yyyy + dateSeparater[p] + MM + dateSeparater[p] + dd);
			}
		}
		
		// TODO add more surface forms here if necessary
		
		// convert to other surface forms (strings and numbers)
		
		// pattern DAY.MONTH.YEAR
		
		list.add(d + " " + MMM + " '" + yy);
		list.add(d + " " + MMMM + " '" + yy);
		list.add(d + " " + MMM + " " + yyyy);
		list.add(d + " " + MMMM + " " + yyyy);
		
		list.add(d + " " + MMM + ", '" + yy);
		list.add(d + " " + MMMM + ", '" + yy);
		list.add(d + " " + MMM + ", " + yyyy);
		list.add(d + " " + MMMM + ", " + yyyy);
		
		list.add(d + getOrdinalFor(Integer.valueOf(d)) + " " + MMM + " '" + yy);
		list.add(d + getOrdinalFor(Integer.valueOf(d)) + " " + MMMM + " '" + yy);
		list.add(d + getOrdinalFor(Integer.valueOf(d)) + " " + MMM + " " + yyyy);
		list.add(d + getOrdinalFor(Integer.valueOf(d)) + " " + MMMM + " " + yyyy);
		
		list.add(d + getOrdinalFor(Integer.valueOf(d)) + " " + MMM + ", '" + yy);
		list.add(d + getOrdinalFor(Integer.valueOf(d)) + " " + MMMM + ", '" + yy);
		list.add(d + getOrdinalFor(Integer.valueOf(d)) + " " + MMM + ", " + yyyy);
		list.add(d + getOrdinalFor(Integer.valueOf(d)) + " " + MMMM + ", " + yyyy);
		
		if (enableDD)
		{
			list.add(dd + " " + MMM + " '" + yy);
			list.add(dd + " " + MMMM + " '" + yy);
			list.add(dd + " " + MMM + " " + yyyy);
			list.add(dd + " " + MMMM + " " + yyyy);
			
			list.add(dd + " " + MMM + ", '" + yy);
			list.add(dd + " " + MMMM + ", '" + yy);
			list.add(dd + " " + MMM + ", " + yyyy);
			list.add(dd + " " + MMMM + ", " + yyyy);
			
			list.add(dd + getOrdinalFor(Integer.valueOf(dd)) + " " + MMM + " '" + yy);
			list.add(dd + getOrdinalFor(Integer.valueOf(dd)) + " " + MMMM + " '" + yy);
			list.add(dd + getOrdinalFor(Integer.valueOf(dd)) + " " + MMM + " " + yyyy);
			list.add(dd + getOrdinalFor(Integer.valueOf(dd)) + " " + MMMM + " " + yyyy);
			
			list.add(dd + getOrdinalFor(Integer.valueOf(dd)) + " " + MMM + ", '" + yy);
			list.add(dd + getOrdinalFor(Integer.valueOf(dd)) + " " + MMMM + ", '" + yy);
			list.add(dd + getOrdinalFor(Integer.valueOf(dd)) + " " + MMM + ", " + yyyy);
			list.add(dd + getOrdinalFor(Integer.valueOf(dd)) + " " + MMMM + ", " + yyyy);
		}
		
		list.add(ordinalNumbers[Integer.valueOf(d) - 1] + " " + MMM + " '" + yy);
		list.add(ordinalNumbers[Integer.valueOf(d) - 1] + " " + MMMM + " '" + yy);
		list.add(ordinalNumbers[Integer.valueOf(d) - 1] + " " + MMM + " " + yyyy);
		list.add(ordinalNumbers[Integer.valueOf(d) - 1] + " " + MMMM + " " + yyyy);
		
		list.add(ordinalNumbers[Integer.valueOf(d) - 1] + " " + MMM + ", '" + yy);
		list.add(ordinalNumbers[Integer.valueOf(d) - 1] + " " + MMMM + ", '" + yy);
		list.add(ordinalNumbers[Integer.valueOf(d) - 1] + " " + MMM + ", " + yyyy);
		list.add(ordinalNumbers[Integer.valueOf(d) - 1] + " " + MMMM + ", " + yyyy);
		
		list.add(ordinalNumbers[Integer.valueOf(d) - 1] + " of " + MMM + " '" + yy);
		list.add(ordinalNumbers[Integer.valueOf(d) - 1] + " of " + MMMM + " '" + yy);
		list.add(ordinalNumbers[Integer.valueOf(d) - 1] + " of " + MMM + " " + yyyy);
		list.add(ordinalNumbers[Integer.valueOf(d) - 1] + " of " + MMMM + " " + yyyy);
		
		// pattern MONTH.DAY.YEAR
		list.add(MMM + " " + d + " '" + yy);
		list.add(MMMM + " " + d + " '" + yy);
		list.add(MMM + " " + d + " " + yyyy);
		list.add(MMMM + " " + d + " " + yyyy);
		
		list.add(MMM + " " + d + ", '" + yy);
		list.add(MMMM + " " + d + ", '" + yy);
		list.add(MMM + " " + d + ", " + yyyy);
		list.add(MMMM + " " + d + ", " + yyyy);
		
		list.add(MMM + " " + d + getOrdinalFor(Integer.valueOf(d)) + " '" + yy);
		list.add(MMMM + " " + d + getOrdinalFor(Integer.valueOf(d)) + " '" + yy);
		list.add(MMM + " " + d + getOrdinalFor(Integer.valueOf(d)) + " " + yyyy);
		list.add(MMMM + " " + d + getOrdinalFor(Integer.valueOf(d)) + " " + yyyy);
		
		list.add(MMM + " " + d + getOrdinalFor(Integer.valueOf(d)) + ", '" + yy);
		list.add(MMMM + " " + d + getOrdinalFor(Integer.valueOf(d)) + ", '" + yy);
		list.add(MMM + " " + d + getOrdinalFor(Integer.valueOf(d)) + ", " + yyyy);
		list.add(MMMM + " " + d + getOrdinalFor(Integer.valueOf(d)) + ", " + yyyy);
		
		if (enableDD)
		{
			list.add(MMM + " " + dd + " '" + yy);
			list.add(MMMM + " " + dd + " '" + yy);
			list.add(MMM + " " + dd + " " + yyyy);
			list.add(MMMM + " " + dd + " " + yyyy);
			
			list.add(MMM + " " + dd + ", '" + yy);
			list.add(MMMM + " " + dd + ", '" + yy);
			list.add(MMM + " " + dd + ", " + yyyy);
			list.add(MMMM + " " + dd + ", " + yyyy);
			
			list.add(MMM + " " + dd + getOrdinalFor(Integer.valueOf(d)) + " '" + yy);
			list.add(MMMM + " " + dd + getOrdinalFor(Integer.valueOf(d)) + " '" + yy);
			list.add(MMM + " " + dd + getOrdinalFor(Integer.valueOf(d)) + " " + yyyy);
			list.add(MMMM + " " + dd + getOrdinalFor(Integer.valueOf(d)) + " " + yyyy);
			
			list.add(MMM + " " + dd + getOrdinalFor(Integer.valueOf(d)) + ", '" + yy);
			list.add(MMMM + " " + dd + getOrdinalFor(Integer.valueOf(d)) + ", '" + yy);
			list.add(MMM + " " + dd + getOrdinalFor(Integer.valueOf(d)) + ", " + yyyy);
			list.add(MMMM + " " + dd + getOrdinalFor(Integer.valueOf(d)) + ", " + yyyy);
		}
		
		list.add(MMM + " " + ordinalNumbers[Integer.valueOf(d) - 1] + " '" + yy);
		list.add(MMMM + " " + ordinalNumbers[Integer.valueOf(d) - 1] + " '" + yy);
		list.add(MMM + " " + ordinalNumbers[Integer.valueOf(d) - 1] + " " + yyyy);
		list.add(MMMM + " " + ordinalNumbers[Integer.valueOf(d) - 1] + " " + yyyy);
		
		list.add(MMM + " " + ordinalNumbers[Integer.valueOf(d) - 1] + ", '" + yy);
		list.add(MMMM + " " + ordinalNumbers[Integer.valueOf(d) - 1] + ", '" + yy);
		list.add(MMM + " " + ordinalNumbers[Integer.valueOf(d) - 1] + ", " + yyyy);
		list.add(MMMM + " " + ordinalNumbers[Integer.valueOf(d) - 1] + ", " + yyyy);
		
		list.add(MMM + " the " + ordinalNumbers[Integer.valueOf(d) - 1] + " '" + yy);
		list.add(MMMM + " the " + ordinalNumbers[Integer.valueOf(d) - 1] + " '" + yy);
		list.add(MMM + " the " + ordinalNumbers[Integer.valueOf(d) - 1] + " " + yyyy);
		list.add(MMMM + " the " + ordinalNumbers[Integer.valueOf(d) - 1] + " " + yyyy);
		
		list.add(MMM + " the " + ordinalNumbers[Integer.valueOf(d) - 1] + ", '" + yy);
		list.add(MMMM + " the " + ordinalNumbers[Integer.valueOf(d) - 1] + ", '" + yy);
		list.add(MMM + " the " + ordinalNumbers[Integer.valueOf(d) - 1] + ", " + yyyy);
		list.add(MMMM + " the " + ordinalNumbers[Integer.valueOf(d) - 1] + ", " + yyyy);
		
		list.add(MMM + ", the " + ordinalNumbers[Integer.valueOf(d) - 1] + ", '" + yy);
		list.add(MMMM + ", the " + ordinalNumbers[Integer.valueOf(d) - 1] + ", '" + yy);
		list.add(MMM + ", the " + ordinalNumbers[Integer.valueOf(d) - 1] + ", " + yyyy);
		list.add(MMMM + ", the " + ordinalNumbers[Integer.valueOf(d) - 1] + ", " + yyyy);
		
		// pattern YEAR.MONTH.DAY
		
		// TODO add more surface forms here if necessary
		
		// TODO remove eventually duplicate date surface forms
	}
	
	/**
	 * Get a certain pattern from a date format.
	 * 
	 * @author Daniel Gerber
	 * @param dateString
	 * @param fromPattern
	 * @param toPattern
	 * @return value of <code>toPattern</code>
	 * @throws ParseException
	 */
	private String getDateString(String dateString, String fromPattern, String toPattern) throws ParseException
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fromPattern, Locale.ENGLISH);
		Date date1 = simpleDateFormat.parse(dateString);
		simpleDateFormat.applyPattern(toPattern);
		
		return simpleDateFormat.format(date1);
	}
	
	/**
	 * Gets the particular ending for a number in order to be an ordinal number.
	 * 
	 * @author Daniel Gerber
	 * @param value
	 *            number which should get a particular ending in order to be an
	 *            ordinal number
	 * @return particular ending of an ordinal number for consigned parameter
	 *         <code>value</code>
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
	
	// ============================================================================================================
	
	/**
	 * Just testing ConverterDateAlgo.
	 * 
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException
	{
		ConverterDateAlgo conDATE = new ConverterDateAlgo();
		
		// result of conversions
		ArrayList<String> result = new ArrayList<String>();
		
		SentenceLoader sentence = null;
		
		// initializes output file
		// try
		// {
		// System.setOut(new PrintStream(new FileOutputStream(new
		// File("testConverterDateAlgo.txt"), true)));
		// } catch (FileNotFoundException e)
		// {
		// e.printStackTrace();
		// }
		
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
				// result.add("sentence:" + i + "  anno:" + k);
				// result.add("________________________");
				
				// conversion DATE
				if (sentence.getSentence(i).getAnnotations().get(k).getType().toString() == "DATE")
				{
					result.addAll(conDATE.convertUnits(sentence.getSentence(i).getAnnotations().get(k)));
					count++;
				}
				
				// result.add("________________________");
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
