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
		sentences = new ArrayList<BoaSentence>();
		Elements sentenceElems = doc.getRootElement().getFirstChildElement("document")
				.getFirstChildElement("sentences").getChildElements();
		
		for (int i = 0; i < sentenceElems.size(); i++)
		{
			ArrayList<String> tokens = new ArrayList<String>();
			Elements tokenElems = sentenceElems.get(i).getFirstChildElement("tokens").getChildElements("token");
			for (int j = 0; j < tokenElems.size(); j++)
			{
				tokens.add(tokenElems.get(j).getFirstChildElement("word").getValue());
			}
			
			ArrayList<BoaAnnotation> annotations = new ArrayList<BoaAnnotation>();
			Elements annotationElems = sentenceElems.get(i).getChildElements("annotation");
			for (int j = 0; j < annotationElems.size(); j++)
			{
				ArrayList<String> annotationTokens = new ArrayList<String>();
				Elements annotationTokenElems = annotationElems.get(j).getChildElements("token");
				for (int k = 0; k < annotationTokenElems.size(); k++)
				{
					annotationTokens.add(tokens.get(Integer.parseInt(annotationTokenElems.get(k).getValue()) - 1));
				}
				annotations.add(new BoaAnnotation(BoaAnnotation.Type.valueOf(annotationElems.get(j)
						.getFirstChildElement("type").getValue()), annotationTokens));
			}
			
			sentences.add(new BoaSentence(tokens, annotations));
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
