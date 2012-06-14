/*
 * StaticThreadDistributor.java
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

import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;
import de.uni_leipzig.informatik.pcai042.boa.searcher.SearcherFactory;

/**
 * An abstract class for Distributors which deal with a fixed number threads
 * which are alive throughout the whole annotation process.
 * 
 * @author Simon Suiter
 */
public abstract class StaticThreadDistributor extends Distributor
{
	/**
	 * Creates a certain number of threads.
	 * 
	 * @param threadCount
	 *            number of threads to create
	 * @param tokenize
	 *            whether the threads shall retrieve untokenized data
	 */
	protected StaticThreadDistributor(int threadCount, ConfigLoader configLoader, boolean tokenize)
	{
		super(configLoader);
		
		if (threadCount <= 0)
			threadCount = 1;
		threads = new ArrayList<AnnotatorThread>(threadCount);
		
		SearcherFactory sf = new SearcherFactory(configLoader);
		for (int i = 0; i < threadCount; i++)
		{
			threads.add(new AnnotatorThread(this, sf, tokenize));
		}
	}
	
	@Override
	public boolean hasFinished()
	{
		for (AnnotatorThread at : threads)
		{
			if (at.isAlive())
				return false;
		}
		return true;
	}
	
	@Override
	public void run()
	{
		for (AnnotatorThread at : threads)
		{
			at.start();
		}
	}
}
