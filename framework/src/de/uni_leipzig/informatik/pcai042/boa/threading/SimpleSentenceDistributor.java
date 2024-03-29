/*
 * SimpleSentenceDistributor.java
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

import java.util.ArrayList;
import java.util.List;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaSentence;
import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;

/**
 * A simple Implementation of Distributor to annotate a list of sentences.
 * 
 * @author Simon Suiter
 */
public class SimpleSentenceDistributor extends StaticThreadDistributor
{
	private ArrayList<BoaSentence> sentences;
	private int counter = 0;
	private int chunkSize;
	
	/**
	 * @param sentences
	 *            the sentences to annotate
	 * @param threadCount
	 *            the number of threads to create
	 * @param chunkSize
	 *            the number of sentences a thread retrieves when calling
	 *            getSentences (at maximum)
	 */
	public SimpleSentenceDistributor(ArrayList<BoaSentence> sentences, int threadCount, int chunkSize,
			ConfigLoader configLoader)
	{
		super(threadCount, configLoader, false);
		this.sentences = sentences;
		
		if (chunkSize <= 0)
			chunkSize = 1;
		this.chunkSize = chunkSize;
	}
	
	@Override
	public String getText()
	{
		return null;
	}
	
	@Override
	public List<BoaSentence> getSentences()
	{
		int x;
		synchronized (this)
		{
			if (counter > sentences.size() / chunkSize)
				return null;
			else
			{
				x = counter++;
			}
		}
		int upper = chunkSize * x + chunkSize;
		if (upper > sentences.size())
			upper = sentences.size();
		return sentences.subList(chunkSize * x, upper);
	}
	
	@Override
	public void returnSentences(List<BoaSentence> sentences)
	{
	}
}
