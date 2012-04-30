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

package de.uni_leipzig.informatik.pcai042.boa;

import java.util.Set;

import de.uni_leipzig.informatik.pcai042.boa.BoaAnnotation.Type;

public class LabelSearcher
{
	private static Set<String> surfaceForms;
	private static Type annoType;
	private static SearchAlgorithm algo;
	
	public LabelSearcher(Type annoType, String sForms, SearchAlgorithm algorithm)
	{
		try
		{
			LabelSearcher.annoType = annoType;
		    surfaceForms  = new ConfigLoader().openConfigSurfaceForms(sForms);
			
			algo = algorithm.getClass().newInstance();
			algo.setSurForms(surfaceForms);
			algo.setAnnoType(annoType);
			System.out.println(algo + " " + annoType.toString());
		}
		catch(Exception e)
		{
			//do sth
		}
	}

	public void searchUnit(BoaSentence sentence)
	{
		try
		{
			algo.search(sentence);
		}
		catch(Exception e)
		{
			//do sth
		}
		
		return;
	}
}
