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

package de.uni_leipzig.informatik.pcai042.boa.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation.Type;
import de.uni_leipzig.informatik.pcai042.boa.searcher.LabelSearcher;
import de.uni_leipzig.informatik.pcai042.boa.searcher.SearchAlgorithm;

public class Annotater
{
	private boolean isInitialized = false;
	private ArrayList<LabelSearcher> searchers;
	
	public Annotater()
	{
		try
		{
			//DynamicEnumTest eTest;
			String algoName, currentUnit;
			searchers = new ArrayList<LabelSearcher>();
			ConfigLoader loader = new ConfigLoader();
			Set<String> units = loader.openConfigSurfaceForms("ALL_UNITS");
			Iterator<String> itUnit = units.iterator();
			
			while(itUnit.hasNext())
			{
				currentUnit = itUnit.next();
				algoName = loader.returnValue(currentUnit.toUpperCase()+"_ALGO").toString();
				Class algo = Class.forName(algoName);
				
				SearchAlgorithm sAlgo = (SearchAlgorithm) algo.newInstance();
				System.out.println(sAlgo);
				
				//if(Type.valueOf(currentUnit.toUpperCase())==null) eTest.add
				searchers.add(new LabelSearcher(Type.valueOf(currentUnit.toUpperCase()), sAlgo));
			}
			
			isInitialized = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void annotate(ArrayList<BoaSentence> sentences)
	{
		if(!isInitialized) return;
		
		BoaSentence currentSentence;
		
		Iterator<BoaSentence> sentenceIt = sentences.iterator();
		
		while(sentenceIt.hasNext())
		{
			currentSentence = sentenceIt.next();
			Iterator<LabelSearcher> searchIt = searchers.iterator();
			
			while(searchIt.hasNext())
			{
				searchIt.next().searchUnit(currentSentence);
			}
		}

	}
}
