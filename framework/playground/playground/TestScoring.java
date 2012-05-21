/*
 * TestScoring.java
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
import java.io.IOException;
import java.util.ArrayList;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;
import de.uni_leipzig.informatik.pcai042.boa.manager.BoaSentence;
import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;
import de.uni_leipzig.informatik.pcai042.boa.manager.Scoring;
import de.uni_leipzig.informatik.pcai042.boa.searcher.SearcherFactory;

public class TestScoring
{
	
	public static void main(String[] args)
	{
		Scoring s1 = null;
		try
		{
			s1 = new Scoring(new File("goldstandard.xml"), new SearcherFactory(new ConfigLoader()));
		} catch (ValidityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParsingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<BoaSentence> sentences = s1.getWorkSentences();
		ArrayList<BoaSentence> gold = s1.getGoldstandard();
		ArrayList<double[]> jau = s1.score();
		for (int j = 0; j < s1.score().size(); j++)
		{
			System.out.println("satz:" + j + "   " + sentences.get(j).getSentence());
			for (BoaAnnotation anno : sentences.get(j).getAnnotations())
			{
				System.out.print(anno + ",  ");
			}
			System.out.println();
			for (BoaAnnotation anno : gold.get(j).getAnnotations())
			{
				System.out.print(anno + ",  ");
			}
			System.out.println();
			System.out.println("precision:" + jau.get(j)[2] + "   recall:" + jau.get(j)[2] + "   fscore:" + jau.get(j)[2]);
			
			System.out.println();
		}
		
	}
}