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
		int help;
		double recall,precision,fscore;
		
		/**
		 * Constructor
		 * @param FileString, location and name of the file to be scored 
		 */
		public Scoring(String FileString)
		{
		s1 = null;
		s2 = null; 
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
public double calculateKp()
{ 
	
		for(int i=0;i<s2.getSentenceCount();i++) 
		{
			for(int j=0;j<s2.getSentence(i).getAnnotations().size();j++)
			{	
				for(int k=0;k<s1.getSentence(i).getAnnotations().size();k++)
				{	
					if(s2.getSentence(i).getAnnotations().get(j).toString().equals(s1.getSentence(i).getAnnotations().get(k).toString()))
					{
						kp++;
					}
					
				}	
			}		 	
		}
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
public double calculateFp()
{
	kp =0; 
		//calculate fp 
		for(int b=0;b<s2.getSentenceCount();b++)
		{	
			for(int a=0;a<s2.getSentence(b).getAnnotations().size();a++)
			{
					
				help=0;
				for(int c=0;c<s1.getSentence(b).getAnnotations().size();c++)
				{
					if(!(s2.getSentence(b).getAnnotations().get(a).toString().equals(s1.getSentence(b).getAnnotations().get(c).toString())))
					{
						help++;
					}
										
				}
				if(help==s1.getSentence(b).getAnnotations().size())
				{
					fp++;
				}
				
			}
			
		}
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
		
		score.fp = score.calculateFp();
		score.precision = score.setPrecision(score.calculateKp(), score.calculateFp());
		score.recall = score.setRecall(score.calculateKp(), score.calculateGoldAnno());
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
	