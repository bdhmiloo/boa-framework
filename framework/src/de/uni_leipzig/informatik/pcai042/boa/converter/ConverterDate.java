/*
 * ConverterDate.java
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

package de.uni_leipzig.informatik.pcai042.boa.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;

/**
 * Converter class for unit type DATE.
 * 
 * @author Duc Huy Bui
 */
public class ConverterDate extends Converter
{
	private static final Logger logger = LoggerFactory.getLogger(ConverterDate.class);
	
	// TODO load all surface forms from file and remove string arrays later
	private String[] monthAbbreviation = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov",
			"Dec" };
	private String[] ordinalNumbers = { "first", "second", "third", "fourth", "fifth", "sixth", "seventh", "eighth",
			"ninth", "tenth", "eleventh", "twelfth", "thirteenth", "fourteenth", "fifteenth", "sixteenth",
			"seventeenth", "eighteenth", "nineteenth", "twentieth", "twenty-first", "twenty-second", "twenty-third",
			"twenty-fourth", "twenty-fifth", "twenty-sixth", "twenty-seventh", "twenty-eighth", "twenty-ninth",
			"thirtieth", "thirty-first" };
	private String[] dateSeparater = { "/", "-", "'", "~", "." };
	
	/**
	 * Constructor.
	 * 
	 */
	public ConverterDate()
	{
		super();
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
		Boolean beginStandardConversion = false;
		Boolean beginSpecialConversion = false;
		
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
		
		// some tests
		String numberCheck = stringBufferAnno.replaceAll("/|-|'|~|\\.", "");
		String splitCheck = stringBufferAnno.replaceAll("/|-|'|~|\\.", ".");
		
		String[] splitTest = splitCheck.split("\\.");
		
		// TODO
		// System.out.println("TEST - original stringBufferAnno: " +
		// stringBufferAnno);
		logger.info("Original uncleaned stringBufferAnno = <" + stringBufferAnno + "> loaded.");
		
		// annotation consists just of numbers (date separaters do not count)
		if (checkIfNumber(numberCheck) && 6 <= stringBufferAnno.length() && stringBufferAnno.length() <= 10
				&& splitTest.length == 3)
		{
			logger.info("Date option #1 selected.");
			
			stringBufferAnno = stringBufferAnno.replaceAll("/|-|'|~", ".");
			
			String[] datePos = stringBufferAnno.split("\\.");
			
			String day = datePos[0];
			String month = datePos[1];
			String year = datePos[2];
			
			logger.info("Parameter stringBufferAnno splittet in <" + day + ">, <" + month + ">, <" + year + ">.");
			
			// define date pattern
			if (1 <= Integer.valueOf(day) && Integer.valueOf(day) <= 12 && 12 < Integer.valueOf(month)
					&& Integer.valueOf(month) <= 31)
			{
				pattern = "MM.dd.yyyy";
				String temp = day;
				day = month;
				month = temp;
				
				beginStandardConversion = true;
			} else if (31 < Integer.valueOf(day) && 1 <= Integer.valueOf(month) && Integer.valueOf(month) <= 12
					&& 1 <= Integer.valueOf(year) && Integer.valueOf(year) <= 31)
			{
				pattern = "yyyy.MM.dd";
				String temp2 = day;
				day = year;
				year = temp2;
				
				beginStandardConversion = true;
			} else
			{
				pattern = "dd.MM.yyyy";
				
				beginStandardConversion = true;
			}
			
			// TODO
			// System.out.println("TEST - op1 pattern: " + pattern);
			
			logger.info("Date pattern <" + pattern + "> was created.");
			
		}
		
		// annotation is a number (in this case: a year)
		else if (checkIfNumber(numberCheck) && 1 <= stringBufferAnno.length() && stringBufferAnno.length() <= 4)
		{
			logger.info("Date option #2 selected");
			
			beginStandardConversion = false;
			beginSpecialConversion = true;
			
			// TODO future: expand algorithm if necessary
		}
		
		// annotation consists of some strings and numbers
		else
		{
			logger.info("Date option #3 selected");
			
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
			// System.out.println("Test - op3 stringBufferAnno: " +
			// stringBufferAnno);
			
			logger.info("Cleaned stringBufferAnno - <" + stringBufferAnno + ">.");
			
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
				logger.info("Date option #3.1 selected.");
				
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
					beginStandardConversion = true;
				} else
				{
					pattern = "dd.MM.yyyy";
					
					stringBufferAnno = day + "." + month + "." + year;
					beginStandardConversion = true;
				}
				
				// TODO
				// System.out.println("TEST - op2 pattern: " + pattern);
				logger.info("Date pattern <" + pattern + "> was created.");
				
			} else
			{
				logger.error("<" + stringBufferAnno + "> was not convertable.");
			}
			
			// TODO improvements
		}
		
		// conversion of date formats
		if (beginSpecialConversion)
		{
			logger.info("Special conversion was enabled.");
			
		} else if (beginStandardConversion)
		{
			logger.info("Standard conversion was enabled.");
			
			conversionOfDate(list, stringBufferAnno, pattern, dateSeparater, ordinalNumbers);
		}
		
		return list;
	}
	
	/**
	 * Convert a month string to the particular month number {1;...;12}.
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
			
			list.add(dd + getOrdinalFor(Integer.valueOf(d)) + " " + MMM + " '" + yy);
			list.add(dd + getOrdinalFor(Integer.valueOf(d)) + " " + MMMM + " '" + yy);
			list.add(dd + getOrdinalFor(Integer.valueOf(d)) + " " + MMM + " " + yyyy);
			list.add(dd + getOrdinalFor(Integer.valueOf(d)) + " " + MMMM + " " + yyyy);
			
			list.add(dd + getOrdinalFor(Integer.valueOf(d)) + " " + MMM + ", '" + yy);
			list.add(dd + getOrdinalFor(Integer.valueOf(d)) + " " + MMMM + ", '" + yy);
			list.add(dd + getOrdinalFor(Integer.valueOf(d)) + " " + MMM + ", " + yyyy);
			list.add(dd + getOrdinalFor(Integer.valueOf(d)) + " " + MMMM + ", " + yyyy);
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
		
		list.add("'" + yy + " " + MMM + " " + d);
		list.add("'" + yy + " " + MMMM + " " + d);
		list.add("" + yyyy + " " + MMM + " " + d);
		list.add("" + yyyy + " " + MMMM + " " + d);
		
		list.add("'" + yy + ", " + MMM + " " + d);
		list.add("'" + yy + ", " + MMMM + " " + d);
		list.add("" + yyyy + ", " + MMM + " " + d);
		list.add("" + yyyy + ", " + MMMM + " " + d);
		
		list.add("'" + yy + " " + MMM + " " + d + getOrdinalFor(Integer.valueOf(d)));
		list.add("'" + yy + " " + MMMM + " " + d + getOrdinalFor(Integer.valueOf(d)));
		list.add("" + yyyy + " " + MMM + " " + d + getOrdinalFor(Integer.valueOf(d)));
		list.add("" + yyyy + " " + MMMM + " " + d + getOrdinalFor(Integer.valueOf(d)));
		
		list.add("'" + yy + ", " + MMM + " " + d + getOrdinalFor(Integer.valueOf(d)));
		list.add("'" + yy + ", " + MMMM + " " + d + getOrdinalFor(Integer.valueOf(d)));
		list.add("" + yyyy + ", " + MMM + " " + d + getOrdinalFor(Integer.valueOf(d)));
		list.add("" + yyyy + ", " + MMMM + " " + d + getOrdinalFor(Integer.valueOf(d)));
		
		if (enableDD)
		{
			list.add("'" + yy + " " + MMM + " " + dd);
			list.add("'" + yy + " " + MMMM + " " + dd);
			list.add("" + yyyy + " " + MMM + " " + dd);
			list.add("" + yyyy + " " + MMMM + " " + dd);
			
			list.add("'" + yy + ", " + MMM + " " + dd);
			list.add("'" + yy + ", " + MMMM + " " + dd);
			list.add("" + yyyy + ", " + MMM + " " + dd);
			list.add("" + yyyy + ", " + MMMM + " " + dd);
			
			list.add("'" + yy + " " + MMM + " " + dd + getOrdinalFor(Integer.valueOf(d)));
			list.add("'" + yy + " " + MMMM + " " + dd + getOrdinalFor(Integer.valueOf(d)));
			list.add("" + yyyy + " " + MMM + " " + dd + getOrdinalFor(Integer.valueOf(d)));
			list.add("" + yyyy + " " + MMMM + " " + dd + getOrdinalFor(Integer.valueOf(d)));
			
			list.add("'" + yy + ", " + MMM + " " + dd + getOrdinalFor(Integer.valueOf(d)));
			list.add("'" + yy + ", " + MMMM + " " + dd + getOrdinalFor(Integer.valueOf(d)));
			list.add("" + yyyy + ", " + MMM + " " + dd + getOrdinalFor(Integer.valueOf(d)));
			list.add("" + yyyy + ", " + MMMM + " " + dd + getOrdinalFor(Integer.valueOf(d)));
		}
		
		list.add("'" + yy + " " + MMM + " " + ordinalNumbers[Integer.valueOf(d) - 1]);
		list.add("'" + yy + " " + MMMM + " " + ordinalNumbers[Integer.valueOf(d) - 1]);
		list.add("" + yyyy + " " + MMM + " " + ordinalNumbers[Integer.valueOf(d) - 1]);
		list.add("" + yyyy + " " + MMMM + " " + ordinalNumbers[Integer.valueOf(d) - 1]);
		
		list.add("'" + yy + ", " + MMM + " " + ordinalNumbers[Integer.valueOf(d) - 1]);
		list.add("'" + yy + ", " + MMMM + " " + ordinalNumbers[Integer.valueOf(d) - 1]);
		list.add("" + yyyy + ", " + MMM + " " + ordinalNumbers[Integer.valueOf(d) - 1]);
		list.add("" + yyyy + ", " + MMMM + " " + ordinalNumbers[Integer.valueOf(d) - 1]);
		
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
	
}
