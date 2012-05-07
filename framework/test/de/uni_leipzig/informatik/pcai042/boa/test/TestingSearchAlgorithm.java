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
import de.uni_leipzig.informatik.pcai042.boa.manager.BoaSentence;
import de.uni_leipzig.informatik.pcai042.boa.searcher.SearchAlgorithm;
import org.junit.*;


/**
 * 
 * @author Giorgos
 *
 */
public class TestingSearchAlgorithm extends TestCase
{
   private SearchAlgorithm searching;
   private  String token = "4" ;
   private String positioncheck = "1233.4x";
   private  Set<String> words = new HashSet<String>();
   private String tokenword = "kg";
  
   
   
   @Before
   public void setUp()
   {
       searching = new SearchAlgorithm()
       {
    	   public void search(BoaSentence sentence){}
       };
       
       words.add("3");
	   words.add("kg");
	   words.add("kilogramm");
	   words.add("In");
    } 
   /**
    *Checks if Token is a number. This method works properly, if the return Value is true.
    **/
   @Test
   public void testIfNumber() 
   {
	   
	   
	 assertTrue("It is a Number", true == searching.checkIfNumber(token) );
   
   }
   
   /**
    *Checks the Position of the first non numeric Character. 
    **/
   @Test
   public void testIfStartsWithANumber() 
   {
	   
	 assertEquals( testPosition(positioncheck) ,searching.checkIfStartsWithNumber(positioncheck));
   
   }
   
   
   
   /**
    * Test if a certain word is a Prefix 
    * It does not work now, checking if the method has is returns the right values
    **/
   @Test
   public void testIfPrefix()
   {
	   assertTrue( true == searching.isPrefix(tokenword, words));
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
	  
	   for (i=0; 1<=token.length(); i++)
		{
			m =token.substring(0, i+1);
			if (isNumeric(m)== false)
			break;
	    }
	   return (i+1);
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
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
}
