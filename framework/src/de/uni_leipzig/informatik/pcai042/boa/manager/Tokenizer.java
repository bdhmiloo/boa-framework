/*
 * Tokenizer.java
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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class Tokenizer
{	
	private StanfordCoreNLP pipeline;
	
	public Tokenizer()
	{
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit");
		pipeline = new StanfordCoreNLP(props);
	}
	
	public List<CoreMap> annotate(String text)
	{
		Annotation document = new Annotation(text);
		pipeline.annotate(document);
		return document.get(SentencesAnnotation.class);
	}
	
	public CoreMap annotateSingleSentence(String sentence) throws IllegalArgumentException
	{
		List<CoreMap> list = annotate(sentence);
		if (list.size() != 1)
		{
			throw new IllegalArgumentException("Passed string is not a single sentence.");
		}
		return list.get(0);
	}
	
	public ArrayList<BoaSentence> tokenize(String text)
	{
		List<CoreMap> sentences = annotate(text);
		ArrayList<BoaSentence> list = new ArrayList<BoaSentence>(sentences.size());
		
		for (CoreMap sentence : sentences)
		{
			list.add(new BoaSentence(sentence));
		}
		return list;
	}
	
	public BoaSentence tokenizeSingleSentence(String sentence) throws IllegalArgumentException
	{
		return new BoaSentence(annotateSingleSentence(sentence));
	}
}
