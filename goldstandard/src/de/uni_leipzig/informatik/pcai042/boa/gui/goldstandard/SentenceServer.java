/*
 * SentenceServer.java
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

package de.uni_leipzig.informatik.pcai042.boa.gui.goldstandard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class SentenceServer
{
	private static File path;
	private static File sentenceFile;
	private static File outputFile;
	
	public static synchronized void init(File file)
	{
		if (!isInitialized())
		{
			path = new File(file.getAbsolutePath() + "/WEB-INF/resources/");
			sentenceFile = new File(path, "sentences.txt");
			outputFile = new File(path, "output.xml");
		}
	}
	
	public static boolean isInitialized()
	{
		return path != null;
	}
	
	static synchronized BoaSentence getSentence()
	{
		String sentence = nextSentence(fileToString(sentenceFile));
		// TODO sentence can be empty
		
		// TODO remove sentence from file
		
		BoaSentence sent = null;
		while (sent == null)
		{
			try
			{
				// tokens are automatically generated
				sent = new BoaSentence(sentence);
			} catch (IllegalArgumentException e)
			{
				e.printStackTrace();
				// TODO: handle exception thrown when Stanford couldn't tokenize the sentence properly
			}
		}
		return sent;
	}
	
	static synchronized void returnSentence(BoaSentence sentence)
	{
		// the xml containing all sentences
		Document output = null;
		// the xml containing the current sentence
		Document xmlSentence = sentence.getXmlDoc();
		
		boolean firstSentence = !outputFile.exists();
		if (!firstSentence)
		{
			// load output from file
			try
			{
				output = new Builder().build(outputFile);
			} catch (ValidityException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParsingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		BoaAnnotation currentAnno;
		Iterator<BoaAnnotation> itAnno = sentence.getAnnotations().iterator();
		// the xml element of our sentence to annotate
		Element sentenceElem = xmlSentence.getRootElement().getFirstChildElement("document")
				.getFirstChildElement("sentences").getFirstChildElement("sentence");
		
		while (itAnno.hasNext())
		{
			currentAnno = itAnno.next();
			
			if (currentAnno.getType() != null) // only necessary if we
												// create
												// empty annotations
			{
				Element annoElem = new Element("annotation");
				for (String token : currentAnno.getTokens())
				{
					Element tokenElem = new Element("token");
					// inserts id of token; add one since Stanford's ids aren't zero-based
					tokenElem.appendChild("" + (sentence.getTokens().indexOf(token) + 1));
					// TODO add type
					annoElem.appendChild(tokenElem);
				}
				sentenceElem.appendChild(annoElem);
			}
		}
		
		if (firstSentence)
		{
			output = xmlSentence;
		} else
		{
			output.getRootElement().getFirstChildElement("document").getFirstChildElement("sentences")
					.appendChild(sentenceElem.copy());
		}
		
		// TODO save output
	}
	
	static synchronized void discardSentence(BoaSentence sentence)
	{
		// TODO implement
	}
	
	/**
	 * Auxiliary Function: Converts the content of a text file into a String
	 * 
	 * @param FilePath
	 *            Path of the text file
	 * @return text content of the text file as a String
	 */
	private static String fileToString(File file)
	{
		String text = "";
		int nextchar;
		
		try
		{
			FileReader reader = new FileReader(file);
			do
			{
				nextchar = reader.read();
				if (nextchar != -1)
					text += (char) (nextchar);
				
			} while (nextchar != -1);
			
			while (text.startsWith("\n"))
			{
				text = text.substring(1);
			}
			
			reader.close();
			return text;
		} catch (FileNotFoundException eFile)
		{
			eFile.printStackTrace();
		} catch (IOException eIO)
		{
			eIO.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * Auxiliary Function: Returns the first line of a String (waits for first
	 * line break).
	 * 
	 * @param text
	 *            input String
	 * @return sentence part of the input String until first occurrence of "\n"
	 */
	private static String nextSentence(String text)
	{		
		return text.indexOf('\n') > -1 ? text.substring(0, text.indexOf('\n')) : "";
	}
}
