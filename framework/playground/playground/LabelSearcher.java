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

package playground;

import java.io.File;
import java.util.ArrayList;

import playground.BoaAnnotation.Type;

public abstract class LabelSearcher
{
	private static ArrayList<String> surfaceForms;
	private static ArrayList<String> falseSurfaceForms;
	private static Type annoType;
	protected static SearchAlgorithm algoSimple;
	protected static SearchAlgorithm algoHard;
	
	public LabelSearcher(Type annoType, String configPath)
	{
		try
		{
			LabelSearcher.annoType = annoType;
			
			surfaceForms = new ConfigLoader().openConfigSurfaceForms(new File(configPath), false);
			falseSurfaceForms = new ConfigLoader().openConfigSurfaceForms(new File(configPath), true);
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
			algoSimple.search(sentence, surfaceForms, annoType);
		}
		catch(Exception e)
		{
			//do sth
		}
		
		return;
	}
	public void searchFalseUnit(BoaSentence sentence)
	{
		try
		{
			algoHard.search(sentence, falseSurfaceForms, annoType);
		}
		catch(Exception e)
		{
			//do sth
		}
		
		return;
	}
}
