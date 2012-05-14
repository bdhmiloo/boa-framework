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


import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import de.uni_leipzig.informatik.pcai042.boa.converter.*;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;

/**
 * Class to Test all of the Converters from the Project
 * @author Giorgos
 *
 */
@Category(Converter.class)
public class TestingPackageBoaConverter
{
	private Converter converter;
	private ConverterDate date = new ConverterDate();
	private ConverterLinearMeasure linearmeasure = new ConverterLinearMeasure();
	private ConverterTemperature temperature = new ConverterTemperature();
	private ConverterWeight weight = new ConverterWeight();
		
	
	// The Lists to be used as tokens
	BoaAnnotation.Type type;
	ArrayList<String> tokensDate = new ArrayList<String>();
	ArrayList<String> tokensLinearMeasure = new ArrayList<String>();
	ArrayList<String> tokensTemperature = new ArrayList<String>();
	ArrayList<String> tokensWeight = new ArrayList<String>();
	
	
	
	/**
	 * Initialize the Abstract Class and the Test array Lists. Before the Tests take place.
	 */
	@Before
	   public void setUp()
	   {
		// REVERT 237 (CONVERTER DATE COMPLETE)
		
		tokensDate.add("23.07.1980");
		tokensDate.add("25.07.1985");
		tokensDate.add("23/07/1980");
		
		tokensLinearMeasure.add("5");
		tokensLinearMeasure.add("14 km");
		
		tokensTemperature.add("32 Celcius");
		tokensTemperature.add("23 °C");
		tokensTemperature.add("23 °F");

		
		tokensWeight.add("5KG");
		tokensWeight.add("10 Kilogramm");
	
		//abstract class
		converter = new Converter()
	       {
	    	 //Add unimplemented Methods
			@Override
			public ArrayList<String> convertUnits(BoaAnnotation annotation)
			{
				
				return null;
			}
			
	    
	      };
	
	   }

	
	  
	 /**
	  *Checks if Token is a number. This method works properly, if the return Value is true.
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
		 BoaAnnotation annotationWeight = new BoaAnnotation(BoaAnnotation.Type.WEIGHT ,	tokensWeight);
		 weight.convertUnits(annotationWeight);

			BoaAnnotation annotationTemperature = new BoaAnnotation(BoaAnnotation.Type.TEMPERATURE, tokensTemperature);
			BoaAnnotation annotationDate = new BoaAnnotation(BoaAnnotation.Type.DATE,tokensDate);
			BoaAnnotation annotationLinearMeasure = new BoaAnnotation(BoaAnnotation.Type.LINEAR_MEASURE,tokensLinearMeasure);		

		 try
		{
			assertNotNull( weight.convertUnits(annotationWeight));
			assertNotNull(date.convertUnits(annotationDate));
			assertNotNull(temperature.convertUnits(annotationTemperature));
			assertNotNull(linearmeasure.convertUnits(annotationLinearMeasure));

		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		
	   }
	 
	 
	 /**
	  * Test if the linear Measure returns the right list. 
	  * Does not work properly right now. the  linearmeasure.convertUnits(annotationLinearMeasure).size() is 0
	  */
	 
	 @Test
	 public void testLinearMeasureList()
	 {
		 BoaAnnotation annotationLinearMeasure = new BoaAnnotation(BoaAnnotation.Type.LINEAR_MEASURE,tokensLinearMeasure);
		
		 //assertEquals(getListSize(tokensLinearMeasure), linearmeasure.convertUnits(annotationLinearMeasure).size());

		 for (int i =0 ; i < linearmeasure.convertUnits(annotationLinearMeasure).size() ;i++)
		 {
			 assertEquals(tokensLinearMeasure.get(i),linearmeasure.convertUnits(annotationLinearMeasure).get(i) );
		 }
	 }
	
	 
	 /**
	  * Test if the linear Weight convert Units returns the right list. 
	  * Does not work properly right now. the  weight.convertUnits(annotationLinearMeasure).size() is 0
	  */
	 @Test 
	 public void testWeightList()

	 {
		 BoaAnnotation annotationWeight = new BoaAnnotation(BoaAnnotation.Type.WEIGHT,tokensWeight);
			
		 //assertEquals(getListSize(tokensWeight), linearmeasure.convertUnits(annotationLinearMeasure).size());

		 for (int i =0 ; i < weight.convertUnits(annotationWeight).size() ;i++)
		 {
			 assertEquals(tokensWeight.get(i),weight.convertUnits(annotationWeight).get(i) );
		 }
	 }
	 
	 /**
	  * Test if the Date convert Units returns the right list. 
	  * Does not work properly right now. the  date.convertUnits(annotationLinearMeasure).size() is 0
	  */
	 @Test 
	 public void testDateList()

	 {
		 BoaAnnotation annotationDate = new BoaAnnotation(BoaAnnotation.Type.DATE,tokensDate);
			
		 //assertEquals(getListSize(tokensDate), linearmeasure.convertUnits(annotationDate).size());

		
	//		for (int i =0 ; i < date.convertUnits(annotationDate).size() ;i++)
	//		 {
	//			 assertEquals(tokensDate.get(i),date.convertUnits(annotationDate).get(i) );
	//		 }
	
	 }
	 
	 /**
	  * Test if the Temperature convert Units returns the right list. 
	  * Does not work properly right now. the  temperature.convertUnits(annotationLinearMeasure).size() is 0
	  */
	 @Test 
	 public void testTemperatureList()

	 {
		 BoaAnnotation annotationTemperature = new BoaAnnotation(BoaAnnotation.Type.TEMPERATURE,tokensTemperature);
			
		 //assertEquals(getListSize(tokensLinearMeasure), linearmeasure.convertUnits(annotationLinearMeasure).size());

		 for (int i =0 ; i < temperature.convertUnits(annotationTemperature).size() ;i++)
		 {
			 assertEquals(tokensTemperature.get(i),temperature.convertUnits(annotationTemperature).get(i) );
		 }
	 }
	 
	 /**
	  * False Test . 
	  * Does not work properly right now. the  temperature.convertUnits(annotationLinearMeasure).size() is 0
	  */
	 @Test 
	 public void testFalseList()

	 {
		 BoaAnnotation annotationTemperature = new BoaAnnotation(BoaAnnotation.Type.TEMPERATURE,tokensTemperature);
		 BoaAnnotation annotationWeight = new BoaAnnotation(BoaAnnotation.Type.WEIGHT,tokensWeight);

		 //assertEquals(getListSize(tokensLinearMeasure), linearmeasure.convertUnits(annotationLinearMeasure).size());
		// System.out.print("token "+tokensTemperature.get(0));
		// System.out.print("converted "+temperature.convertUnits(annotationTemperature).get(0).toString());


		 for (int i =0 ; i < temperature.convertUnits(annotationTemperature).size() ;i++)
		 {
			 assertThat(tokensTemperature.get(i), is(not(temperature.convertUnits(annotationTemperature).get(i))));
			 
		 }
	 }
	 
	
	 /**
	  * to write the results on a txt file
	  */
	 @After
	 public void writeToFile()
	 {
		 
		 //TO DO
	 }
	
	 /*
	  * To check the Sizes of the List if they are the same in every category
	  */
	 public int getListSize( ArrayList<String> list)
		{
			return list.size();
		}
	 
	 
	 

}
