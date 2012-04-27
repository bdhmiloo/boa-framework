/*
 * SearchThread.java
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

package de.uni_leipzig.informatik.pcai042.boa;

import java.util.ArrayList;
import java.util.Iterator;

public class SearchThread extends Thread
{
	private ArrayList<BoaSentence> sentences;
	private LabelSearcher searcher;
	
	public SearchThread(LabelSearcher ls, ArrayList<BoaSentence> sentences)
	{
		try
		{
			this.sentences = sentences;
			this.searcher = ls;
		}
		catch(NullPointerException e)
		{
			//do sth.
		}
	}
	
	public void run()
	{
		try
		{
			Iterator<BoaSentence> it = sentences.iterator();
			
			while(it.hasNext())
			{
				searcher.searchUnit(it.next());
			}
		}
		catch(NullPointerException e)
		{
			//do sth
		}
	}
	
}
