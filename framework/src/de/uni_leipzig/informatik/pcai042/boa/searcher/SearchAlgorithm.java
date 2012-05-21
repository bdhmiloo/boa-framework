/*
 * SearchAlgorithm.java
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

import java.util.HashMap;
import java.util.Set;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaSentence;

public abstract class SearchAlgorithm
{
	protected String annoType;
	
	public SearchAlgorithm(HashMap<String, Set<String>> config, String annoType)
	{
		this.annoType = annoType;
	}
	
	/**
	 * Searches sentence for occurrences of certain patterns.
	 * The nature of this patterns is defined in each subclass.
	 * On success objects of type BoaAnnotation will be added to sentence.
	 * @param sentence
	 */
	public abstract void search(BoaSentence sentence);
	
	public String getAnnoType()
	{
		return annoType;
	}
}
