/*
 * LabelSearcher.java
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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaSentence;
import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;
import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation.Type;

/**
 * Searches BoaSentence with a given SearchAlgorithm and a given 
 * Set of certain patterns.
 * @author Jakob M.
 *
 */

public class LabelSearcher
{
	/**
	 * The Algorithm used by the searchUnit function.
	 */
	
	private SearchAlgorithm algo;
	
	/**
	 * Constructor. Creates a new SearchAlgorithm of the given type 
	 * and provides it with patterns from an external file.
	 * @param annoType type of the created annotations
	 * @param algorithm the algorithm used to search for the patterns
	 */
	
	public LabelSearcher(Type annoType, SearchAlgorithm algorithm)
	{
		try
		{
		    ConfigLoader load = new ConfigLoader();
			Set<String> unitNames = load.openConfigSurfaceForms(annoType.toString());
			Set<String> surfaceForms = new HashSet<String>();
			
			Iterator<String> it = unitNames.iterator();
			while(it.hasNext())
			{
				surfaceForms.addAll(load.openConfigSurfaceForms(it.next().toUpperCase()));
			}
			
			algo = algorithm.getClass().newInstance();
			algo.setSurForms(surfaceForms);
			algo.setAnnoType(annoType);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Appends the search method of it's SearchAlgorithm on a given BoaSentence
	 * @param sentence the sentence to be searched in
	 */
	
	public void searchUnit(BoaSentence sentence)
	{
		try
		{
			algo.search(sentence);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return;
	}
}
