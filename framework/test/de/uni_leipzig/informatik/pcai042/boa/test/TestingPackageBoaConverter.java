/*
 * TestingPackageBoaManager.java
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

package de.uni_leipzig.informatik.pcai042.boa.test;

import java.text.ParseException;
import java.util.ArrayList;

import org.junit.*;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.*;
import de.uni_leipzig.informatik.pcai042.boa.converter.*;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;
import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;

/**
 * Class to Test all of the Converters from the Project
 * 
 * @author Giorgos
 * 
 */
@Category(Converter.class)
public class TestingPackageBoaConverter
{
	private Converter converter;
	private ConverterDate date = new ConverterDate(new ConfigLoader());
	private ConverterLinearMeasure linearmeasure = new ConverterLinearMeasure(new ConfigLoader());
	private ConverterTemperature temperature = new ConverterTemperature(new ConfigLoader());
	private ConverterWeight weight = new ConverterWeight(new ConfigLoader());
	
	// The Lists to be used as tokens
	String type;
	ArrayList<String> tokensDate = new ArrayList<String>();
	ArrayList<String> tokensLinearMeasure = new ArrayList<String>();
	ArrayList<String> tokensTemperature = new ArrayList<String>();
	ArrayList<String> tokensWeight = new ArrayList<String>();
	
	/**
	 * Initialize the Abstract Class and the Test array Lists. Before the Tests
	 * take place.
	 */
	@Before
	public void setUp()
	{
		// REVERT 237 (CONVERTER DATE COMPLETE)
		
		tokensDate.add("23");
		tokensDate.add("07");
		tokensDate.add("1980");
		
		tokensLinearMeasure.add("1");
		tokensLinearMeasure.add("km");
		
		tokensTemperature.add("32");
		tokensTemperature.add("�c");
		// tokensTemperature.add("23 �F");
		
		tokensWeight.add("5");
		tokensWeight.add("kg");
		
	}
	
	/**
	 * Checks if Token is a number. This method works properly, if the return
	 * Value is true.
	 **/
	@Test
	public void testIfNumber()
	{
		
		assertTrue("It is a Number", true == converter.checkIfNumber("4") && converter.checkIfNumber("1234"));
		
	}
	
	/**
	 * Check if all of the Converters return something
	 */
	@Test
	public void testConvertUnits()
	{
		BoaAnnotation annotationWeight = new BoaAnnotation("WEIGHT", tokensWeight);
		weight.convertUnits(annotationWeight);
		
		BoaAnnotation annotationTemperature = new BoaAnnotation("TEMPERATURE", tokensTemperature);
		BoaAnnotation annotationDate = new BoaAnnotation("DATE", tokensDate);
		BoaAnnotation annotationLinearMeasure = new BoaAnnotation("LINEAR_MEASURE", tokensLinearMeasure);
		
		try
		{
			assertNotNull(weight.convertUnits(annotationWeight));
			assertNotNull(date.convertUnits(annotationDate));
			assertNotNull(temperature.convertUnits(annotationTemperature));
			assertNotNull(linearmeasure.convertUnits(annotationLinearMeasure));
			
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Test if the linear Measure stores the right values for the conversion of
	 * 1 KM And if the list contains all of the 12 different ways of describing
	 * the linear measures
	 */
	
	@Test
	public void testLinearMeasureList()
	{
		BoaAnnotation annotationLinearMeasure = new BoaAnnotation("LINEAR_MEASURE", tokensLinearMeasure);
		assertTrue(linearmeasure.convertUnits(annotationLinearMeasure).contains("1093.6133000 yard"));
		assertTrue(linearmeasure.convertUnits(annotationLinearMeasure).contains("39370.00 inch"));
		assertTrue(linearmeasure.convertUnits(annotationLinearMeasure).contains("0.621371192000 miles"));
		assertTrue(linearmeasure.convertUnits(annotationLinearMeasure).contains("0.5399000 seamiles"));
		
		assertEquals(12, linearmeasure.convertUnits(annotationLinearMeasure).size());
	}
	
	/**
	 * Test if the linear Weight convert Units returns all the right values of
	 * the conversion of 5 KG And if the list contains all of the 31 different
	 * ways of describing the linear measures
	 */
	@Test
	public void testWeightList()
	
	{
		BoaAnnotation annotationWeight = new BoaAnnotation("WEIGHT", tokensWeight);
		
		assertTrue(weight.convertUnits(annotationWeight).contains("176.3698095 oz"));
		assertTrue(weight.convertUnits(annotationWeight).contains("11.02311310 pound"));
		assertTrue(weight.convertUnits(annotationWeight).contains("0.005 tonnes"));
		assertTrue(weight.convertUnits(annotationWeight).contains("25000 kt"));
		assertTrue(weight.convertUnits(annotationWeight).contains("5000000 mg"));
		assertTrue(weight.convertUnits(annotationWeight).contains("5000 g"));
		assertTrue(weight.convertUnits(annotationWeight).contains("11.02311310 lbs"));
		
		assertEquals(31, weight.convertUnits(annotationWeight).size());
		
	}
	
	/**
	 * Test if the Date convert Units returns all of the different ways to write
	 * a Date. the test-Dates ware choosed randomly.
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testDateList() throws ParseException
	
	{
		BoaAnnotation annotationDate = new BoaAnnotation("DATE", tokensDate);
		
		assertTrue(date.convertUnits(annotationDate).contains("July the twenty-third, 1980"));
		assertTrue(date.convertUnits(annotationDate).contains("1980, July twenty-third"));
		assertTrue(date.convertUnits(annotationDate).contains("1980, Jul twenty-third"));
		assertTrue(date.convertUnits(annotationDate).contains("1980, July 23rd"));
		assertTrue(date.convertUnits(annotationDate).contains("23rd July, 1980"));
		assertTrue(date.convertUnits(annotationDate).contains("July 23rd, 1980"));
		assertTrue(date.convertUnits(annotationDate).contains("July twenty-third, 1980"));
		
	}
	
	/**
	 * Test if the Temperature convert Units returns the right list. Does not
	 * work properly right now. the
	 * temperature.convertUnits(annotationLinearMeasure).size() is 0
	 */
	@Test
	public void testTemperatureList()
	
	{
		BoaAnnotation annotationTemperature = new BoaAnnotation("TEMPERATURE", tokensTemperature);
		assertTrue(temperature.convertUnits(annotationTemperature).contains("32.00 degreecelsius"));
		assertTrue(temperature.convertUnits(annotationTemperature).contains("305.15 kelvin"));
		assertTrue(temperature.convertUnits(annotationTemperature).contains("305.15 degreeskelvin"));
		assertTrue(temperature.convertUnits(annotationTemperature).contains("89.600 f"));
		assertTrue(temperature.convertUnits(annotationTemperature).contains("89.600 �f"));
		assertTrue(temperature.convertUnits(annotationTemperature).contains("89.600 degreefahrenheit"));
		
		assertEquals(18, temperature.convertUnits(annotationTemperature).size());
	}
	
	/**
	 * False Test .check the conversion Lists with false Values
	 * 
	 * @throws ParseException
	 */
	@Test
	public void testFalseList() throws ParseException
	
	{
		BoaAnnotation annotationTemperature = new BoaAnnotation("TEMPERATURE", tokensTemperature);
		BoaAnnotation annotationWeight = new BoaAnnotation("WEIGHT", tokensWeight);
		BoaAnnotation annotationDate = new BoaAnnotation("DATE", tokensDate);
		BoaAnnotation annotationLinearMeasure = new BoaAnnotation("LINEAR_MEASURE", tokensLinearMeasure);
		
		assertFalse(temperature.convertUnits(annotationTemperature).contains("32.00 �h"));
		assertFalse(temperature.convertUnits(annotationTemperature).contains("32.01 �c"));
		assertFalse(temperature.convertUnits(annotationTemperature).contains("45 �f"));
		assertFalse(temperature.convertUnits(annotationTemperature).contains("degreecelcius"));
		
		assertFalse(date.convertUnits(annotationDate).contains("Jyly the twenty-third, 1980"));
		assertFalse(date.convertUnits(annotationDate).contains("July the twenty third, 1980"));
		assertFalse(date.convertUnits(annotationDate).contains("23rd July, 198"));
		assertFalse(date.convertUnits(annotationDate).contains("23..7.1980"));
		
		assertFalse(weight.convertUnits(annotationWeight).contains("5 Kilogramm"));
		assertFalse(weight.convertUnits(annotationWeight).contains("5 milligram"));
		assertFalse(weight.convertUnits(annotationWeight).contains("176.3699095 oz"));
		assertFalse(weight.convertUnits(annotationWeight).contains("5009 gran"));
		
		assertFalse(linearmeasure.convertUnits(annotationLinearMeasure).contains("1093.6133000 Yard"));
		assertFalse(linearmeasure.convertUnits(annotationLinearMeasure).contains("1093.6233000 yard"));
		assertFalse(linearmeasure.convertUnits(annotationLinearMeasure).contains("0.5399000 Seamile"));
		assertFalse(linearmeasure.convertUnits(annotationLinearMeasure).contains("0.5399000 miles"));
	}
}
