/*
 * Goldstandard.java
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

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

/**
 * 
 * @author Simon Suiter
 */
public class SentenceLoader
{
	private ArrayList<BoaSentence> sentences;
	
	private void createSentences(Document doc)
	{
		
		Elements sentenceElems = doc.getRootElement().getFirstChildElement("document")
				.getFirstChildElement("sentences").getChildElements();
		sentences = new ArrayList<BoaSentence>(sentenceElems.size());
		
		for (int i = 0; i < sentenceElems.size(); i++)
		{
			sentences.add(new BoaSentence(new Document((Element) sentenceElems.get(i).copy())));
		}
	}
	
	public SentenceLoader(File file) throws ValidityException, ParsingException, IOException
	{
		createSentences(new Builder().build(file));
	}
	
	public SentenceLoader(Document doc)
	{
		createSentences(doc);
	}
	
	public ArrayList<BoaSentence> getSentences()
	{
		return sentences;
	}
	
	public BoaSentence getSentence(int index)
	{
		return sentences.get(index);
	}
	
	public int getSentenceCount()
	{
		return sentences.size();
	}
	
	public static void main(String[] args)
	{
		SentenceLoader sl = null;
		try
		{
			sl = new SentenceLoader(new File("goldstandard.xml"));
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
		
		if (sl == null)
			return;
		
		for (BoaSentence s : sl.getSentences())
		{
			System.out.println(s.getSentence());
			System.out.println(" Annotations: " + s.getAnnotations().size());
		}
	}
}
