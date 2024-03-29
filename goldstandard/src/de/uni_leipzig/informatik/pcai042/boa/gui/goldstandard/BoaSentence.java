/*
 * BoaSentenece.java
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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import nu.xom.Document;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

/**
 * Contains various immutable representations of a Sentence, and its
 * Annotations.
 * 
 * @author Simon Suiter
 */
public class BoaSentence
{
	private String sentence;
	private ArrayList<String> tokens;
	private ArrayList<BoaAnnotation> annotations;
	/**
	 * the StanfordCoreNLP output of the sentence
	 */
	private Document xmlDoc;
	
	private static StanfordCoreNLP pipeline = null;
	
	/**
	 * Tokenizes the sentence and generates an XML representation.
	 * 
	 * @param sentence
	 * @throws IllegalArgumentException
	 *             thrown when StanfordCoreNLP couldn't tokenize the sentence
	 *             properly
	 */
	public BoaSentence(String sentence) throws IllegalArgumentException
	{
		this.sentence = sentence;
		annotations = new ArrayList<BoaAnnotation>();
		tokens = new ArrayList<String>();
		
		// double checked locking
		if (pipeline == null)
			initPipeline();
		synchronized (pipeline)
		{
			// generate tokens with StanfordCoreNLP
			Annotation document = new Annotation(sentence);
			pipeline.annotate(document);
			
			List<CoreMap> sentences = document.get(SentencesAnnotation.class);
			
			// since we always deal with single sentences, there can be only one
			// sentence in the output
			// otherwise StanfordCoreNLP had problems to tokenize the sentence
			if (sentences.size() != 1)
			{
				throw new IllegalArgumentException();
			}
			
			for (CoreLabel token : sentences.get(0).get(TokensAnnotation.class))
			{
				// this is the text of the token
				String word = token.originalText();
				tokens.add(word);
			}
			xmlDoc = pipeline.annotationToDoc(document);
		}
	}
	
	private static synchronized void initPipeline()
	{
		if (pipeline == null)
		{
			Properties props = new Properties();
			props.put("annotators", "tokenize, ssplit");
			pipeline = new StanfordCoreNLP(props);
		}
	}
	
	public String getSentence()
	{
		return sentence;
	}
	
	public ArrayList<String> getTokens()
	{
		return tokens;
	}
	
	public ArrayList<BoaAnnotation> getAnnotations()
	{
		return annotations;
	}
	
	public Document getXmlDoc()
	{
		return xmlDoc;
	}
}
