/*
 * SimpleStreamDistributor.java
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaSentence;
import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;

/**
 * A Simple Implementation of Distributor to annotate an untokenized text line
 * by line. The data is retrieved from an InputStream.
 * 
 * @author Simon Suiter
 */
public class SimpleStreamDistributor extends StaticThreadDistributor
{
	private ArrayList<BoaSentence> result;
	private BufferedReader reader;
	
	/**
	 * @param inputStream
	 *            the stream to retrieve data from
	 * @param threadCount
	 *            the number of threads to create
	 */
	public SimpleStreamDistributor(InputStreamReader inputStream, int threadCount, ConfigLoader configLoader)
	{
		super(threadCount, configLoader, true);
		reader = new BufferedReader(inputStream);
		result = new ArrayList<BoaSentence>();
	}
	
	@Override
	public synchronized String getText()
	{
		try
		{
			return reader.readLine();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<BoaSentence> getSentences()
	{
		return null;
	}
	
	@Override
	public synchronized void returnSentences(List<BoaSentence> sentences)
	{
		result.addAll(sentences);
	}
	
	/**
	 * Returns the result of the annotation process or null if it has not
	 * finished yet.
	 */
	public ArrayList<BoaSentence> getResult()
	{
		return hasFinished() ? result : null;
	}
	
}
