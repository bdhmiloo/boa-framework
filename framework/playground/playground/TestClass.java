/*
 * TestClass.java
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

import java.util.ArrayList;

import playground.BoaAnnotation.Type;

public class TestClass
{
	public static void main(String[] args)
	{
		String testString = "Bush ran for 52 yards on 12 carries and scored	a touchdown, and added three catches for 22 yards,"
				+ " he also ran like 50 miles during the match and hated the way I messed him up with this sentence 23.4 meters (yeah, this doesn't make sense)!";
		//create BoaSentence for tests
		BoaSentence testSentence = new BoaSentence(testString);
		
		//create ArrayList of surfFroms to search for
		ArrayList<String> testList = new ArrayList<String>();
		testList.add("meters");
		testList.add("miles");
		testList.add("yards");
		
		//create Algo
		SearchAlgorithm testAlgo = new NaiveAlgorithm();
		
		testAlgo.search(testSentence, testList, Type.LINEAR_MEASURE);
		System.out.println(testSentence.getAnnotations().toString());
	}

}
