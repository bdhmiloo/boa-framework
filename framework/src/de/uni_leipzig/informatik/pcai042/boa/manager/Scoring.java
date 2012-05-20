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
import java.text.ParseException;
import java.util.ArrayList;

import nu.xom.ParsingException;
import nu.xom.ValidityException;
import de.uni_leipzig.informatik.pcai042.boa.manager.BoaSentence;
import de.uni_leipzig.informatik.pcai042.boa.manager.SentenceLoader;
import de.uni_leipzig.informatik.pcai042.boa.searcher.Annotator;

public class Scoring
{
	Annotator a1;
	SentenceLoader s1;
	double kp, fp, countGoldAnno;
	int help, help2, t1, t2;
	double recall, precision, fscore;
	BoaSentence workSentence;
	public Scoring()
	{
	}
	
	
	/**
	 * method to score the annotater method
	 * @return list with 3 doubles: percision, recall, fscore
	 *        
	 */
	public ArrayList<double[]> score()
	{
		ArrayList<BoaSentence> workSentences = new ArrayList<BoaSentence>();
		
		ArrayList<double[]> result = new ArrayList<double[]>();
		a1 = new Annotator();
		t1 = 0;
		t2 = 0;
		kp = 0;
		fp = 0;
		countGoldAnno = 0;
		//load Goldstandard.xml
		try
		{
			s1 = new SentenceLoader(new File("goldstandard.xml"));
		} catch (ValidityException e)
		{
			e.printStackTrace();
		} catch (ParsingException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
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
			
			double[] help={precision,recall,fscore};
			result.add(help);
			
			
		}
		
		return result;
		
	}
	
}
