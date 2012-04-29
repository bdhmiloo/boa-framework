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

package de.uni_leipzig.informatik.pcai042.boa;



import java.io.File;
import java.io.IOException;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

/**
 * Class to compare the result of annotation process with goldstandard
 */
public class Scoring
{
		SentenceLoader s1, s2;
		double kp,fp,countGoldAnno;
		int help, help2, t1, t2;
		double recall,precision,fscore;
		
		/**
		 * Constructor
		 * @param FileString, location and name of the file to be scored 
		 */
		public Scoring(String FileString)
		{
		s1 = null;
		s2 = null; 
		t1 =0;
		t2 =0;
		kp=0;
		fp=0;
		countGoldAnno=0;
		
		//Call for SentenceLoader with Exception Handling
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
		
		if (s1 == null)
			return;
		
		//Call for SentenceLoader with Exception Handling
		try
		{
			s2 = new SentenceLoader(new File(FileString));
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
		
		if (s2 == null)
			return;
		
		}

/**
 * calculation kp, number of right annotations
 * @return kp
 */
public void calculate()
{ 
	
	for(int i=0;i<s1.getSentenceCount();i++) 
	{
		
		for(int j=0;j<s2.getSentence(i).getAnnotations().size();j++)
		{	
			for(int k=0;k<s1.getSentence(i).getAnnotations().size();k++)
			{	
				if(s2.getSentence(i).getAnnotations().get(j).toString().equals(s1.getSentence(i).getAnnotations().get(k).toString()))
				t1=1;
				
			}	
			help2=0;
			for(int e=0;e<s2.getSentence(i).getAnnotations().get(j).getTokens().size();e++)
			{	
				for(int d=0;d<s2.getSentence(i).getTokens().size();d++)
				{		
					if(s2.getSentence(i).getAnnotations().get(j).getTokens().get(e)==s2.getSentence(i).getTokens().get(d))
					{	//System.out.println("i:"+i+"j:"+j+"e:"+e+"d:"+d);	
						for(int w=0;w<s1.getSentence(i).getAnnotations().size();w++)
						{																			
							for(int z=0;z<s1.getSentence(i).getAnnotations().get(w).getTokens().size();z++)
							{	
								for(int y=0;y<s1.getSentence(i).getTokens().size();y++)
								{		
									if(s1.getSentence(i).getAnnotations().get(w).getTokens().get(z)==s1.getSentence(i).getTokens().get(y))
									{
										if(e==z&&d==y)
											help2++;
									}
								}
							}
						}	
					}
				}
						
			}	
					if(help2==s2.getSentence(i).getAnnotations().get(j).getTokens().size())
						t2=1;
					if(t1==1&&t2==1)
						kp++;
					setKp(kp);
		}
				
		for(int a=0;a<s2.getSentence(i).getAnnotations().size();a++)
		{
				
			help=0;
			for(int c=0;c<s1.getSentence(i).getAnnotations().size();c++)
			{
				if(!(s2.getSentence(i).getAnnotations().get(a).toString().equals(s1.getSentence(i).getAnnotations().get(c).toString())))
				{
					help++;
				}
									
			}
			if(help==s1.getSentence(i).getAnnotations().size())
			{
				fp++;
			}
			setFp(fp);
			
		}
	}
}

/**
 * 
 * @param kp2
 */
private void setKp(double kp2)
{
	this.kp = kp2; 
}

/**
 * 
 * @return
 */
private double getKp()
{
	return kp; 
}

/**
 * Count all Annotations in goldstandard.xml
 * @return number of Annotations in goldstandard.xml
 */
public double calculateGoldAnno()
{
		for(int l=0;l<s1.getSentenceCount();l++)
		{
			countGoldAnno+=s1.getSentence(l).getAnnotations().size();
		}
		return countGoldAnno; 
}

/**
 * Count all wrong Annotations made in process
 * @return number of all wrong made annotations 
 */
public void setFp(double fp2)
{
	this.fp = fp2; 
}

public double getFp()
{
	return fp; 
}
	


/**
 *  setRecall
 */
public double setRecall(double kp, double countGoldAnno)
{
		return recall=(kp)/(countGoldAnno);
}
/**
 * setPrescision
 * @param kp
 * @param fp
 * @return
 */
public double setPrecision(double kp, double fp)
{
		return precision=kp/(kp+fp);
}
/**
 * setFscore
 * @param recall
 * @param precision
 * @return
 */
public double setFscore(double recall, double precision)
{
		return fscore=(2*precision*recall)/(recall+precision);
}
/**
 * testmain 
 * @param args
 */
public static  void main(String args[])
{
				
		Scoring score = new Scoring("testResultOfAnnotation.xml");
		
		score.calculate();
		score.fp = score.getFp();
		score.precision = score.setPrecision(score.getKp(), score.getFp());
		score.recall = score.setRecall(score.getKp(), score.calculateGoldAnno());
		score.setFscore(score.recall, score.precision);
		
		System.out.println(score.kp);
		System.out.println(score.fp);
		System.out.println(score.countGoldAnno);
		System.out.println(" ");
		System.out.println("recall:"+score.recall);
		System.out.println("precision:"+score.precision);
		System.out.println("fscore:"+score.fscore);
}		
}
	