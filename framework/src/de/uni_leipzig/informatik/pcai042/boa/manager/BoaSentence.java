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

package de.uni_leipzig.informatik.pcai042.boa.manager;

import java.util.ArrayList;

import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
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
	private ArrayList<Integer> beginPos;
	private ArrayList<Integer> endPos;
	private ArrayList<BoaAnnotation> annotations;
	private Element xmlElem = null;
	
	/**
	 * Tokenizes a sentence.
	 * 
	 * @param sentence
	 *            the original text of the sentence
	 * @throws IllegalArgumentException
	 *             thrown when sentence is a text not containing a single
	 *             sentence.
	 */
	public BoaSentence(String sentence) throws IllegalArgumentException
	{
		this(sentence, new Tokenizer());
	}
	
	/**
	 * Tokenizes a sentence by using a certain {@link Tokenizer}.
	 * 
	 * @param sentence
	 *            the original text of the sentence
	 * @param tokenizer
	 *            the tokenizer
	 * @throws IllegalArgumentException
	 *             thrown when sentence is a text not containing a single
	 *             sentence.
	 */
	public BoaSentence(String sentence, Tokenizer tokenizer) throws IllegalArgumentException
	{
		this(tokenizer.annotateSingleSentence(sentence));
	}
	
	/**
	 * Creates a sentence from a CoreMap returned by a {@link Tokenizer}.
	 * 
	 * @param sentence
	 *            the original text of the sentence
	 * @param coreMap
	 *            the CoreMap
	 */
	public BoaSentence(CoreMap coreMap)
	{
		sentence = coreMap.get(CoreAnnotations.TextAnnotation.class);
		tokens = new ArrayList<String>(coreMap.get(TokensAnnotation.class).size());
		beginPos = new ArrayList<Integer>(coreMap.get(TokensAnnotation.class).size());
		endPos = new ArrayList<Integer>(coreMap.get(TokensAnnotation.class).size());
		for (CoreLabel token : coreMap.get(TokensAnnotation.class))
		{
			String word = token.originalText();
			tokens.add(word);
			beginPos.add(token.beginPosition());
			endPos.add(token.endPosition());
		}
		annotations = new ArrayList<BoaAnnotation>();
	}
	
	/**
	 * Creates a sentence from a {@link nu.xom.Element} object that contains the
	 * sentence's XML representation in the following form:
	 * 
	 * <pre>
	 * {@code
	 * <tokens>
	 *     <token id="1">
	 *         <word>someString</word>
	 *         <CharacterOffsetBegin>someOffset</CharacterOffsetBegin>
	 *         <CharacterOffsetEnd>anotherOffset</CharacterOffsetEnd>
	 *     </token>
	 *     <!-- additional tokens -->
	 * </tokens>
	 * <annotation>
	 *     <token>someTokenId</token>
	 *     <token>anotherId</token>
	 *     <type>BoaAnnotation.Type</type>
	 * </annotation>
	 * <!-- additional annotations -->
	 * }
	 * </pre>
	 * 
	 * @param elem
	 *            the sentence element
	 * @see BoaSentence#getXmlElem()
	 */
	public BoaSentence(Element elem)
	{
		Elements tokenElems = elem.getFirstChildElement("tokens").getChildElements("token");
		tokens = new ArrayList<String>(tokenElems.size());
		beginPos = new ArrayList<Integer>(tokenElems.size());
		endPos = new ArrayList<Integer>(tokenElems.size());
		
		for (int i = 0; i < tokenElems.size(); i++)
		{
			tokens.add(tokenElems.get(i).getFirstChildElement("word").getValue());
			beginPos.add(Integer.parseInt(tokenElems.get(i).getFirstChildElement("CharacterOffsetBegin").getValue()));
			endPos.add(Integer.parseInt(tokenElems.get(i).getFirstChildElement("CharacterOffsetEnd").getValue()));
		}
		
		Elements annotationElems = elem.getChildElements("annotation");
		annotations = new ArrayList<BoaAnnotation>(annotationElems.size());
		for (int i = 0; i < annotationElems.size(); i++)
		{
			ArrayList<String> annotationTokens = new ArrayList<String>();
			Elements annotationTokenElems = annotationElems.get(i).getChildElements("token");
			for (int k = 0; k < annotationTokenElems.size(); k++)
			{
				annotationTokens.add(tokens.get(Integer.parseInt(annotationTokenElems.get(k).getValue()) - 1));
			}
			annotations.add(new BoaAnnotation(BoaAnnotation.Type.valueOf(annotationElems.get(i)
					.getFirstChildElement("type").getValue()), annotationTokens));
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tokens.size() - 1; i++)
		{
			sb.append(tokens.get(i));
			for (int j = endPos.get(i); j < beginPos.get(i + 1); j++)
			{
				sb.append(" ");
			}
		}
		sb.append(tokens.get(tokens.size() - 1));
		sentence = sb.toString();
	}
	
	private BoaSentence()
	{
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
	
	/**
	 * Returns the zero-based id of a token
	 * 
	 * @param token
	 * @return
	 */
	public int getTokenId(String token)
	{
		for (int i = 0; i < tokens.size(); i++)
		{
			if (token == tokens.get(i))
				return i;
		}
		throw new IllegalArgumentException("Argument is not a token of this sentence.");
	}
	
	public int getTokenBeginPos(String token)
	{
		return beginPos.get(getTokenId(token));
	}
	
	public int getTokenEndPos(String token)
	{
		return endPos.get(getTokenId(token));
	}
	
	/**
	 * Returns a {@link nu.xom.Element} representing the sentence.
	 * 
	 * @return the XML element
	 * @see BoaSentence#BoaSentence(Element)
	 */
	public Element getXmlElem()
	{
		if (xmlElem == null)
		{
			xmlElem = new Element("sentence");
			Element tokensElem = new Element("tokens");
			for (int i = 0; i < tokens.size(); i++)
			{
				Element tokenElem = new Element("token");
				tokenElem.addAttribute(new Attribute("id", "" + (i + 1)));
				Element wordElem = new Element("word");
				wordElem.appendChild(tokens.get(i));
				Element beginPosElem = new Element("CharacterOffsetBegin");
				beginPosElem.appendChild(beginPos.get(i).toString());
				Element endPosElem = new Element("CharacterOffsetEnd");
				endPosElem.appendChild(endPos.get(i).toString());
				tokenElem.appendChild(wordElem);
				tokenElem.appendChild(beginPosElem);
				tokenElem.appendChild(endPosElem);
				tokensElem.appendChild(tokenElem);
			}
			xmlElem.appendChild(tokensElem);
		} else
		{
			Elements annos = xmlElem.getChildElements("annotation");
			for (int i = 0; i < annos.size(); i++)
			{
				xmlElem.removeChild(annos.get(i));
			}
		}
		
		for (BoaAnnotation annotation : annotations)
		{
			Element annoElem = new Element("annotation");
			for (String token : annotation.getTokens())
			{
				Element tokenElem = new Element("token");
				// inserts id of token; start with 1 since Stanford's ids aren't
				// zero-based
				tokenElem.appendChild("" + (getTokenId(token) + 1));
				annoElem.appendChild(tokenElem);
			}
			Element annoType = new Element("type");
			annoType.appendChild("" + annotation.getType());
			annoElem.appendChild(annoType);
			
			xmlElem.appendChild(annoElem);
		}
		return xmlElem;
	}
	
	/**
	 * Creates a copy of the sentence with an empty list of annotations.
	 * 
	 * @return a new instance of BoaSentence
	 */
	public BoaSentence copy()
	{
		BoaSentence bs = new BoaSentence();
		bs.sentence = sentence;
		bs.tokens = tokens;
		bs.beginPos = beginPos;
		bs.endPos = endPos;
		bs.annotations = new ArrayList<BoaAnnotation>();
		return bs;
	}
}
