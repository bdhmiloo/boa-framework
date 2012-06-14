/*
 * AnnotatorThread.java
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

package de.uni_leipzig.informatik.pcai042.boa.threading;

import java.util.List;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaSentence;
import de.uni_leipzig.informatik.pcai042.boa.manager.Tokenizer;
import de.uni_leipzig.informatik.pcai042.boa.searcher.Annotator;
import de.uni_leipzig.informatik.pcai042.boa.searcher.SearcherFactory;

/**
 * A thread that creates its own Searchers, retrieves data from a Distributor
 * and annotates it. If a Thread is used to tokenize the input,
 * Distributor.getText is called, otherwise Distributor.getSentences is called.
 * It stops as soon as it receives null from the Distributor.
 * 
 * @see Distributor
 * @author Simon Suiter
 */
public class AnnotatorThread extends Thread
{
	private Distributor distr;
	private Annotator annotator;
	private boolean tokenize;
	private Tokenizer tokenizer;
	
	/**
	 * @param distr
	 *            the Distributor to receive date from
	 * @param sf
	 *            a SearcherFactory to construct searchers
	 * @param tokenize
	 *            whether the thread shall retrieve untokenized data from distr
	 */
	public AnnotatorThread(Distributor distr, SearcherFactory sf, boolean tokenize)
	{
		this.distr = distr;
		this.tokenize = tokenize;
		if (tokenize)
			tokenizer = new Tokenizer();
		annotator = new Annotator(sf);
	}
	
	public void run()
	{
		String text = null;
		List<BoaSentence> sentences = null;
		while ((tokenize ? (text = distr.getText()) : (sentences = distr.getSentences())) != null)
		{
			if (tokenize)
				sentences = tokenizer.tokenize(text);
			annotator.annotate(sentences);
			distr.returnSentences(sentences);
		}
	}
}
