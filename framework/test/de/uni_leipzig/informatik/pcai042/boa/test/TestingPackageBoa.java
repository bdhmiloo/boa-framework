/*
 * TestingPackageBoa.java
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

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.*;


import nu.xom.ParsingException;
import nu.xom.ValidityException;



import de.uni_leipzig.informatik.pcai042.boa.Scoring;
import de.uni_leipzig.informatik.pcai042.boa.manager.*;

public class TestingPackageBoa
{
	
	String FileString = new String();
	SentenceLoader s1, s2;
	double numberOfSentensces;

	Scoring scoring; 
	@Before
	public void SetUp() throws ValidityException, ParsingException, IOException
	{
		scoring= new Scoring(FileString);
		
		s1 = new SentenceLoader(new File("goldstandard.xml"));
		s2 = new SentenceLoader(new File(FileString));

	}
	
	@Test
	public void testNumberOfSentences()
	{
		numberOfSentensces = s1.getSentenceCount();

	 extracted();
		
	}

	private void extracted()
	{
		assertEquals(numberOfSentensces, scoring.calculateGoldAnno() );
	}
	
	
	
}
