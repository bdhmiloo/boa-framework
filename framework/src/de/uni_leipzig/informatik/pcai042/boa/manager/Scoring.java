/*
 * scoring.java
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import nu.xom.ParsingException;
import nu.xom.ValidityException;
import de.uni_leipzig.informatik.pcai042.boa.manager.BoaSentence;
import de.uni_leipzig.informatik.pcai042.boa.manager.SentenceLoader;
import de.uni_leipzig.informatik.pcai042.boa.searcher.Annotator;
import de.uni_leipzig.informatik.pcai042.boa.searcher.SearcherFactory;

public class Scoring
{
	private ArrayList<double[]> result;
	private ArrayList<BoaSentence> gold;
	private ArrayList<BoaSentence> workSentences;
	
	public Scoring(File goldstandard, SearcherFactory sf) throws ValidityException, ParsingException, IOException
	{
		Annotator a1;
		SentenceLoader s1;
		double kp, fp, countGoldAnno;
		int help, help2, t1, t2;
		double recall, precision, fscore;
		BoaSentence workSentence;
		workSentences = new ArrayList<BoaSentence>();
		
		s1 = new SentenceLoader(goldstandard);
		gold = s1.getSentences();
		HashSet<String> types = new HashSet<String>();
		for (BoaSentence sentence : gold)
		{
			for(BoaAnnotation anno : sentence.getAnnotations())
				types.add(anno.getType());
		}
		a1 = new Annotator(sf, types);
		
		result = new ArrayList<double[]>();
		
		t1 = 0;
		t2 = 0;
		kp = 0;
		fp = 0;
		countGoldAnno = 0;
		
		//copy each sentence and send it to annotater
		for (int i = 0; i < s1.getSentenceCount(); i++)
		{
			workSentence = s1.getSentence(i).copy();
			workSentences.add(workSentence);
			
		}
		a1.annotate(workSentences);
		//compare goldstandard and annotated sentences to get scoring information
		for (int i = 0; i < s1.getSentenceCount(); i++)
		{
			
			for (int j = 0; j < workSentences.get(i).getAnnotations().size(); j++)
			{
				for (int k = 0; k < s1.getSentence(i).getAnnotations().size(); k++)
				{
					if (workSentences.get(i).getAnnotations().get(j).toString()
							.equals(s1.getSentence(i).getAnnotations().get(k).toString()))
						t1 = 1;
					
				}
				help2 = 0;
				for (int e = 0; e < workSentences.get(i).getAnnotations().get(j).size(); e++)
				{
					for (int d = 0; d < workSentences.get(i).size(); d++)
					{
						if (workSentences.get(i).getAnnotations().get(j).getToken(e) == workSentences.get(i)
								.getToken(d))
						{ //System.out.println("i:"+i+"j:"+j+"e:"+e+"d:"+d);
							for (int w = 0; w < s1.getSentence(i).getAnnotations().size(); w++)
							{
								for (int z = 0; z < s1.getSentence(i).getAnnotations().get(w).size(); z++)
								{
									for (int y = 0; y < s1.getSentence(i).size(); y++)
									{
										if (s1.getSentence(i).getAnnotations().get(w).getToken(z) == s1
												.getSentence(i).getToken(y))
										{
											if (e == z && d == y)
												help2++;
										}
									}
								}
							}
						}
					}
					
				}
				if (help2 == workSentences.get(i).getAnnotations().get(j).size())
					t2 = 1;
				if (t1 == 1 && t2 == 1)
					kp++;
				
			}
			
			for (int a = 0; a < workSentences.get(i).getAnnotations().size(); a++)
			{
				
				help = 0;
				for (int c = 0; c < s1.getSentence(i).getAnnotations().size(); c++)
				{
					if (!(workSentences.get(i).getAnnotations().get(a).toString().equals(s1.getSentence(i)
							.getAnnotations().get(c).toString())))
					{
						help++;
					}
					
				}
				if (help == s1.getSentence(i).getAnnotations().size())
				{
					fp++;
				}
				
			}
		
			countGoldAnno += s1.getSentence(i).getAnnotations().size();
			precision= kp / (kp + fp);
			recall=	(kp) / (countGoldAnno);
			fscore=(2 * precision * recall) / (recall + precision);
			
			result.add(new double[] {precision,recall,fscore});
			
			
		}
	}
	
	/**
	 * method to score the annotater method
	 * @return list with 3 doubles: percision, recall, fscore
	 *        
	 */
	public ArrayList<double[]> score()
	{
		return result;
		
	}
	
	public ArrayList<BoaSentence> getWorkSentences()
	{
		return workSentences;
		
	}
	public ArrayList<BoaSentence> getGoldstandard()
	{
		return gold;
		
	}
}
