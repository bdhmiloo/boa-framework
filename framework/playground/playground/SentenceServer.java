package playground;
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



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
	 * @return ArrayList<String> tokenized sentence
	 */
	
	static synchronized ArrayList<String> getSentence()
	{
		ArrayList tokens = new ArrayList<String>();
		String sentence = nextSentence(fileToString("c:/users/Bosthorst/Java/test.txt"));	//PLACEHOLDER = your FilePath (later constant) 
				
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
	static synchronized void returnSentence(ArrayList<String> tokens, ArrayList<Annotation> annotations)
	{

	}
	
	// called to return a sentence without annotations
	static synchronized void discardSentence(ArrayList<String> tokens)
	{
		
	}
	
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
			System.out.println(eFile);
		}
		catch(IOException eIO)
		{
			System.out.println(eIO);
		}
		
		
		return "";
	}
	
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
    			
    	}
    
    	return "";
    }
}
