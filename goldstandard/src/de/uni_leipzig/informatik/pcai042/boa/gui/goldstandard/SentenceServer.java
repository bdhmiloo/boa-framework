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
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

// serves sentences to goldstandard applications and saves annotations
// calls from different sessions are synchronized
public class SentenceServer
{
	/**
	 * Reads first line of a text file and splits the extracted string into tokens.
	 * 
	 * @return tokens ArrayList<String> representing the tokenized sentence
	 */
	
	static synchronized ArrayList<String> getSentence()
	{
		ArrayList<String> tokens = new ArrayList<String>();
		String sentence = nextSentence(fileToString("PLACEHODLER"));	//PLACEHOLDER = your FilePath (later constant) 
				
		//initializing Stanford pipeline
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		
		Annotation document = new Annotation(sentence);
		pipeline.annotate(document);
	
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		
		for (CoreMap currentSentence : sentences)
		{
			for (CoreLabel token : currentSentence.get(TokensAnnotation.class))
			{
				// this is the text of the token
				String word = token.get(TextAnnotation.class);
				tokens.add(word);
			}
		}
		
		return tokens;
	}
	
	// called to return an annotated sentence  
	static synchronized void returnSentence(ArrayList<String> tokens, ArrayList<de.uni_leipzig.informatik.pcai042.boa.gui.goldstandard.Annotation> annotations)
	{
		//initializing Stanford pipeline
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		
		//merging the tokens into a String
		String text = "";
		Iterator<String> it = tokens.iterator();
		
		while(it.hasNext())
		{
			text+=it.next()+" ";
		}
		
		//creating Stanford annotations
		Annotation document = new Annotation(text);
		pipeline.annotate(document);
		
		//print xml file
		String filePath = "sentenceID.xml";	//to do: adding the Path of the directory + introducing a sentence ID of some kind
		
		try
		{
			pipeline.xmlPrint(document, new FileWriter(filePath));	//
			
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//still to implement: method to insert our Annotations into the XML
		
		String annotationBlock = "<ANNOTATIONS>";
		text = fileToString(filePath);
		de.uni_leipzig.informatik.pcai042.boa.gui.goldstandard.Annotation currentAnno = new de.uni_leipzig.informatik.pcai042.boa.gui.goldstandard.Annotation(null, null);
		
		Iterator<de.uni_leipzig.informatik.pcai042.boa.gui.goldstandard.Annotation> itAnno = annotations.iterator();
		while(itAnno.hasNext())
		{
			currentAnno = itAnno.next();
			
			if(currentAnno.getType()!=null)	//only necessary if we create empty annotations
			{
				//now our Annotation class needs to be modified so a token that occurs more than once in a sentence can definitely be identified
				//best is to convert the ArrayList<String> tokens into an ArrayList<(String, int)> where the integer describes which occurrence
				//of the String the token resembles
			}
		}
		
	}
	
	// called to return a sentence without annotations
	static synchronized void discardSentence(ArrayList<String> tokens)
	{
		
	}
	
	/**
	 * Auxiliary Function: Converts the content of a text file into a String
	 * @param FilePath Path of the text file
	 * @return text content of the text file as a String
	 */
	
	private static String fileToString(String FilePath)
	{
		File file = new File(FilePath);
		String text = "";
		int nextchar;
		
		try
		{
			FileReader reader = new FileReader(file);
			do
			{
				nextchar = reader.read();
				if(nextchar!=-1) text+= (char)(nextchar);

			}while(nextchar!= -1);
			
    		while(text.startsWith("\n"))
    		{
    			text = text.substring(1);
    		}			
			
			reader.close();		
			return text;
		}
		catch(FileNotFoundException eFile)
		{
			eFile.printStackTrace();
		}
		catch(IOException eIO)
		{
			eIO.printStackTrace();
		}
		
		
		return "";
	}
	
	/**
	 * Auxiliary Function: Returns the first line of a String (waits for first line break). 
	 * @param text input String
	 * @return sentence part of the input String until first occurrence of "\n"
	 */
	
    private static String nextSentence(String text)
    {
    	try
    	{
    		String sentence="";
    		int i =0;
    		
    		while(text.charAt(i)!='\n')
    		{
    			sentence+=text.charAt(i);
    			i++;

    			if(i == text.length()) break;
    		}
    	
           return sentence;
    	}
    	catch(ArrayIndexOutOfBoundsException eA)
    	{
    		eA.printStackTrace();
    	}
    
    	return "";
    }
}
