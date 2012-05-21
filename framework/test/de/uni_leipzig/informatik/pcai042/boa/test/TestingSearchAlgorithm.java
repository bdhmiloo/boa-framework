/**
 * TestingSearchAlgorithm.java
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

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import de.uni_leipzig.informatik.pcai042.boa.searcher.NaiveAlgorithm;
import de.uni_leipzig.informatik.pcai042.boa.searcher.SearcherFactory;

import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;
import org.junit.*;


/**
 * 
 * @author Giorgos
 *
 */
public class TestingSearchAlgorithm extends TestCase
{
   private NaiveAlgorithm searching;
   private String annoType;
   private  Set<String> words = new HashSet<String>();
   private SearcherFactory sf = new SearcherFactory(new ConfigLoader());
   
  
   
   @Before
   public void setUp()
   {
       searching = (NaiveAlgorithm) sf.newSearcher("WEIGHT");
       
     
       
       words.add("3");
	   words.add("kgme");
	   words.add("kilogramm");
	   words.add("In");
    } 
   /**
    *Checks if Token is a number. This method works properly, if the return Value is true.
    **/
   
  /* @Test 
   public void testConstructor()
   {
	   assertEquals()
	   searching2 = new SearchAlgorithm();
   }*/
    
   
   @Test
   public void testIfNumber() 
   {
	   
	 assertTrue("It is a Number", true == searching.checkIfNumber("4") && searching.checkIfNumber("1234"));

   }
   /**
    *Checks if Token is a number. This method works properly, if the return Value is false.
    **/
   @Test
   public void testIfNumberFalse() 
   {
	   
	 assertFalse("It is a Number", false == searching.checkIfNumber("23f") && searching.checkIfNumber("e3"));

   }
   
   
   /**
    *Checks the Position of the first non numeric Character. 
    **/
   @Test
   public void testIfStartsWithANumber() 
   {
	   
	 assertEquals( testPosition("1233.4x") ,searching.checkIfStartsWithNumber("1233.4x"));
   
   }
   
   /**
    *Checks the Position of the first non numeric Character. 
    **/
   @Test
   public void testIfStartsWithANumberFalse() 
   {
	   
	 assertEquals( testPosition("xxr") ,searching.checkIfStartsWithNumber("xxr"));
   
   }
   
   
   
   
   
   
   /**
    * Test if a certain word is a Prefix 
    * It does not work now, checking if the method has is returns the right values
    **/
   @Test
   public void testIfPrefix()
   {
	   assertTrue( true == searching.isPrefix("kg", words));
   }
 
 /**
  * This method is combined with the isNumeric(String str) Method to define the position of the first non digit
  * @param token  the Sting who will be cut to characters for the checking of the digits
  * @return the position of the first non digit in the String.
  */
   public int testPosition(String token)
   {
	  String m;
	  int i;
	  
	   for (i=-1; i<=token.length(); i++)
		{
			m =token.substring(0, i+2);
			if (isNumeric(m)== false)
			break;
	    }
	   // for the fall that the sting starts with a Character
	   if (i == -1) return i;
	   return (i+2);
	}
   
   
   /**
    * This method is combined with the testPosition(String token) Method to define the position of the first non digit
    * @param str String to be checked
    * @return true if digit or false if not digit
    */
   public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    //double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
   
   
   /**
    * Test if the Constructors work via testing the get Methods
    */
 
   @Test
	public void testConstructor() 
	 {
	   assertEquals(annoType, searching.getAnnoType());
	 }
   
   
}
