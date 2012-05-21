/*
 * Annotater.java
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

package de.uni_leipzig.informatik.pcai042.boa.searcher;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaSentence;
import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;

public class Annotator
{
	private ArrayList<SearchAlgorithm> searchers;
	
	public Annotator()
	{
		this(new SearcherFactory(new ConfigLoader()));
	}
	
	public Annotator(SearcherFactory sf)
	{
		searchers = new ArrayList<SearchAlgorithm>();
		Iterator<String> it = sf.getTypeIterator();
		while (it.hasNext())
		{
			searchers.add(sf.newSearcher(it.next()));
		}
	}
	
	public Annotator(SearcherFactory sf, Set<String> types)
	{
		searchers = new ArrayList<SearchAlgorithm>();
		Iterator<String> it = types.iterator();
		while (it.hasNext())
		{
			SearchAlgorithm searcher = sf.newSearcher(it.next());
			if (searcher != null)
				searchers.add(searcher);
		}
	}
	
	/**
	 * Uses SearcheAlgorithm to look for certain patterns in the given sentences
	 * and adds a BoaAnnotation for each match.
	 * 
	 * @param sentences
	 *            the sentences that are searched
	 */
	public void annotate(List<BoaSentence> sentences)
	{
		for (BoaSentence sentence : sentences)
		{
			for (SearchAlgorithm searcher : searchers)
			{
				searcher.search(sentence);
			}
		}
	}
	
	/**
	 * Uses SearcheAlgorithm to look for certain patterns in the given sentences
	 * and adds a BoaAnnotation for each match.
	 * 
	 * @param sentences
	 *            the sentences that are searched
	 */
	public void annotate(BoaSentence[] sentences)
	{
		for (BoaSentence sentence : sentences)
		{
			for (SearchAlgorithm searcher : searchers)
			{
				searcher.search(sentence);
			}
		}
	}
	
}
