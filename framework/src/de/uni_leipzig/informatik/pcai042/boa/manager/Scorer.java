/*
 * Scorer.java
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

package de.uni_leipzig.informatik.pcai042.boa.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class Scorer
{
	/**
	 * SentenceLoader to get BoaSentences out of Xml
	 */
	SentenceLoader s1;
	/**
	 * Annotate for test
	 */
	Annotater a;
	
	/**
	 * Constructor
	 */
	public Scorer()
	{
		try
		{
			s1 = new SentenceLoader(new File("goldstandard.xml"));
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
		
		if (s1 == null)
			return;
		
		a = new Annotater();
		
	}
	
	/**
	 * get all Sentences out of goldstandard
	 * @return BoaSentences
	 */
	public ArrayList<BoaSentence> getBSentence()
	{
		ArrayList<BoaSentence> withoutAnno = new ArrayList<BoaSentence>();
		int count =s1.getSentenceCount();
		for(int i =0; i<count; i++)
		{
			withoutAnno.add(s1.getSentence(i).deleteSentence(s1.getSentence(i)));
		}
		return withoutAnno;
		
	}
	
	/**
	 * Annotate the Sentences without Annotation
	 * @param withoutAnno, Sentences, with deleted List of Annotations
	 */
	public void annotate(ArrayList<BoaSentence> withoutAnno)
	{
		a.annotate(withoutAnno);
	}
	
}
