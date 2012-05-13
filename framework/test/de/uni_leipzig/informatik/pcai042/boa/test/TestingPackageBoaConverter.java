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

import java.util.ArrayList;

import org.junit.*;
import org.junit.experimental.categories.Category;


import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import de.uni_leipzig.informatik.pcai042.boa.converter.*;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;

@Category(Converter.class)
public class TestingPackageBoaConverter
{
	private Converter converter;
	private ConverterDate date = new ConverterDate();
	private ConverterLinearMeasure linearmessure = new ConverterLinearMeasure();
	private ConverterTemperature temperature = new ConverterTemperature();
	private ConverterWeight weight = new ConverterWeight();
		
	BoaAnnotation.Type type;
	ArrayList<String> tokens = new ArrayList<String>();

	
	
	@Before
	   public void setUp()
	   {
		
		BoaAnnotation annotation = new BoaAnnotation(type,tokens);


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
		 // assertNotNull(weight.convertUnits(annotation) && date.convertUnits(annotation) && temperature.convertUnits(annotation) && linearmeasure.convertUnits(annotation));
	   }
	 
	 
	 
	 
	 @Test
	 public void testLinearMeasureList()
	 {
		 ArrayList <String> list = new ArrayList<String>();
		
		//TO DO
	 }
	
	 @Test 
	 public void testWeightList()

	 {
		 //TO DO
	 }
	 
	 @Test 
	 public void testDateList()

	 {
		 //TO DO
	 }
	 
	 @Test 
	 public void testTemperatureList()

	 {
		 //assert
	 }
	 
	
	 
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
