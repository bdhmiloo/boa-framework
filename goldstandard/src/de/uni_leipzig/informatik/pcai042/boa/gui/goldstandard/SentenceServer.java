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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.ParsingException;
import nu.xom.Serializer;
import nu.xom.ValidityException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class handles the input of sentences from a text file and the output of
 * annotated sentences as an XML file. It serves the GUI with sentences and receives the annotations.
 * The class is thread-save.
 * 
 * Input text file: WEB-INF/resources/sentences.txt
 * Every sentence must be in a distinct line.
 * 
 * Output file: WEB-INF/resources/output.xml
 */
public class SentenceServer
{
	private static File path;
	private static File sentenceFile;
	private static File outputFile;
	
	private static Document output = null;
	private static int sentenceCount = 0;
	
	private static final Logger logger = LoggerFactory.getLogger(SentenceServer.class);
	
	/**
	 * Initializes file destination paths and tries to load previous Annotation
	 * from the output file.
	 * 
	 * @param file
	 *            the root destination of all files
	 */
	public static synchronized void init(File file)
	{
		if (!isInitialized())
		{
			path = new File(file.getAbsolutePath() + "/WEB-INF/resources/");
			sentenceFile = new File(path, "sentences.txt");
			outputFile = new File(path, "output.xml");
			
			if (outputFile.exists())
			{
				// load output from file after server is restarted
				try
				{
					output = new Builder().build(outputFile);
				} catch (ValidityException e)
				{
					logger.error(e.getMessage());
				} catch (ParsingException e)
				{
					logger.error(e.getMessage());
				} catch (IOException e)
				{
					logger.error(e.getMessage());
				}
				sentenceCount = output.getRootElement().getFirstChildElement("document")
						.getFirstChildElement("sentences").getChildElements().size();
				logger.info("loaded previous annotationed sentences (" + sentenceCount + ")");
			}
			logger.info("SentenceServer initialized");
		}
	}
	
	/**
	 * Returns whether the server is initialized
	 * 
	 * @return true if already initialized
	 */
	public static boolean isInitialized()
	{
		return path != null;
	}
	
	/**
	 * Called by the GUI to request a new non-annotated sentence.
	 * 
	 * @return the sentence
	 */
	static synchronized BoaSentence getSentence()
	{
		BoaSentence sent = null;
		String sentence;
		while (sent == null)
		{
			sentence = nextSentence(fileToString(sentenceFile));
			if (sentence == null)
				return null;
			aodSentence(sentence, sentenceFile, false);
			try
			{
				// tokens are automatically generated
				sent = new BoaSentence(sentence);
				logger.info("sentence served: \"" + sentence + "\"");
			} catch (IllegalArgumentException e)
			{
				logger.warn("sentence rejected: \"" + sentence + "\"");
			}
		}
		return sent;
	}
	
	/**
	 * Called by the GUI to return an annotated sentence. Saves the annotated
	 * sentence in output file.
	 * 
	 * @param sentence
	 *            the returned sentence
	 */
	static synchronized void returnSentence(BoaSentence sentence)
	{
		Document xmlSentence = sentence.getXmlDoc();
		sentenceCount++;
		
		BoaAnnotation currentAnno;
		Iterator<BoaAnnotation> itAnno = sentence.getAnnotations().iterator();
		// the xml element of our sentence to annotate
		Element sentenceElem = xmlSentence.getRootElement().getFirstChildElement("document")
				.getFirstChildElement("sentences").getFirstChildElement("sentence");
		sentenceElem.getAttribute("id").setValue("" + sentenceCount);
		
		while (itAnno.hasNext())
		{
			currentAnno = itAnno.next();
			
			Element annoElem = new Element("annotation");
			for (String token : currentAnno.getTokens())
			{
				Element tokenElem = new Element("token");
				// inserts id of token; add one since Stanford's ids aren't
				// zero-based
				tokenElem.appendChild("" + (sentence.getTokens().indexOf(token) + 1));
				annoElem.appendChild(tokenElem);
			}
			// add annotation type
			Element annoType = new Element("type");
			annoType.appendChild("" + currentAnno.getType());
			annoElem.appendChild(annoType);
			
			// add annotation to sentence
			sentenceElem.appendChild(annoElem);
		}
		
		if (output == null)
		{
			output = xmlSentence;
		} else
		{
			output.getRootElement().getFirstChildElement("document").getFirstChildElement("sentences")
					.appendChild(sentenceElem.copy());
		}
		
		// save output
		xmlDocToFile(output);
		logger.info("annotated sentence #" + sentenceCount + ": \"" + sentence.getSentence() + "\"");
	}
	
	/**
	 * Called by the GUI to return a discarded sentence. Puts the sentence back
	 * in sentence pool.
	 * 
	 * @param sentence
	 *            the returned sentence
	 */
	static synchronized void discardSentence(BoaSentence sentence)
	{
		aodSentence(sentence.getSentence(), sentenceFile, true);
		logger.info("discarded sentence: \"" + sentence.getSentence() + "\"");
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
		int nextchar;
		StringBuilder stringBuilder = new StringBuilder();
		
		try
		{
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			do
			{
				nextchar = reader.read();
				if (nextchar != -1)
					stringBuilder.append((char) (nextchar));
				
			} while (nextchar != -1);
			
			reader.close();
			return stringBuilder.toString().trim();
		} catch (FileNotFoundException e)
		{
			logger.error(e.getMessage());
		} catch (IOException e)
		{
			logger.error(e.getMessage());
		}
		
		return "";
	}
	
	/**
	 * Returns the first line of a String or null if String is empty.
	 * 
	 * @param text
	 *            input String
	 * @return first line of input String or null
	 */
	private static String nextSentence(String text)
	{
		if (text.isEmpty())
			return null;
		return text.indexOf('\n') > -1 ? text.substring(0, text.indexOf('\n')) : text;
	}
	
	/**
	 * aod = add (if direction = true) or delete (if direction = false) sentence
	 * is added as first line of the file when on delete, all occurrences of the
	 * sentence are removed from the file
	 * 
	 * @param sentence
	 * @param oldFile
	 * @param direction
	 */
	private static void aodSentence(String sentence, File oldFile, Boolean direction)
	{
		String text, newText;
		
		try
		{
			text = fileToString(oldFile);
			
			if (direction)
				newText = sentence + "\n" + text;
			else
				newText = text.replace(sentence, "");
			
			// backup old text to temporary file
			File tempFile = new File(path, "sentences.txt.tmp");
			OutputStreamWriter tempWriter = new OutputStreamWriter(new FileOutputStream(tempFile), "UTF-8");
			tempWriter.write(newText);
			tempWriter.close();
			
			if (!tempFile.renameTo(sentenceFile))
			{
				logger.error("renaming of temporary sententece file unsuccesful");
			}
		} catch (FileNotFoundException e)
		{
			logger.error(e.getMessage());
		} catch (IOException e)
		{
			logger.error(e.getMessage());
		}
	}
	
	/**
	 * Pretty prints a xml document to output file.
	 * 
	 * @param doc
	 *            The document to print
	 */
	private static void xmlDocToFile(Document doc)
	{
		try
		{
			File tmpOutputFile = new File(path, "output.xml.tmp");
			FileOutputStream outputStream = new FileOutputStream(tmpOutputFile);
			Serializer serializer = new Serializer(outputStream, "UTF-8");
			serializer.setIndent(4);
			serializer.write(doc);
			outputStream.close();
			if (!tmpOutputFile.renameTo(outputFile))
			{
				logger.error("renaming of temporary output file unsuccesful");
			}
		} catch (FileNotFoundException e)
		{
			logger.error(e.getMessage());
		} catch (UnsupportedEncodingException e)
		{
			logger.error(e.getMessage());
		} catch (IOException e)
		{
			logger.error(e.getMessage());
		}
	}
}
