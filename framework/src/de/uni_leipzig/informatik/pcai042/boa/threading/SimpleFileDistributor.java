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

public class SimpleFileDistributor extends StaticThreadDistributor
{
	private ArrayList<BoaSentence> result;
	private BufferedReader reader;
	
	public SimpleFileDistributor(InputStreamReader inputStream, int threadCount, ConfigLoader configLoader)
	{
		super(threadCount, configLoader, true);
		reader = new BufferedReader(inputStream);
		result = new ArrayList<BoaSentence>();
	}
	
	@Override
	public String getText()
	{
		try
		{
			return reader.readLine();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
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
	public void returnSentences(List<BoaSentence> sentences)
	{
		result.addAll(sentences);
	}
	
	public ArrayList<BoaSentence> getResult()
	{
		return hasFinished() ? result : null; 
	}
	
}
