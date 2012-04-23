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

/**
 * Class to compare the result of annotation process with goldstandard
 */

import java.io.File;
import java.io.IOException;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class Scoring
{
	//testmain
	public static void main(String[] args)
	{
		//can extract the sentences out of the xml file
		SentenceLoader s1 = null;
		double kp,fp,countGoldAnno;
		kp=0;
		fp=0;
		countGoldAnno=0;
		int hilfe;
		double recall,precision,fscore;
		
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
		
		
		
		SentenceLoader s2 = null;
		try
		{
			s2 = new SentenceLoader(new File("C:/Users/Christian/workspace/scoring/BoaAnno.xml"));
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
		
		//if (s1 == null)
			//return;
		
		//calculate kp
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
		
		//calculate countGoldAnno 
		for(int l=0;l<s1.getSentenceCount();l++)
		{
			countGoldAnno+=s1.getSentence(l).getAnnotations().size();
		}
		//calculate fp 
		for(int b=0;b<s2.getSentenceCount();b++)
		{	
			for(int a=0;a<s2.getSentence(b).getAnnotations().size();a++)
			{
					
				hilfe=0;
				for(int c=0;c<s1.getSentence(b).getAnnotations().size();c++)
				{
					if(!(s2.getSentence(b).getAnnotations().get(a).toString().equals(s1.getSentence(b).getAnnotations().get(c).toString())))
					{
						hilfe++;
					}
										
				}
				if(hilfe==s1.getSentence(b).getAnnotations().size())
				{
					fp++;
				}
				
			}
			
		}	
		
		//calculate scoring functions
		recall=(kp)/(countGoldAnno);
		precision=kp/(kp+fp);
		fscore=(2*precision*recall)/(recall+precision);
		
			
			
		//testoutput	
			
		System.out.println(kp);
		System.out.println(fp);
		System.out.println(countGoldAnno);
		System.out.println(" ");
		System.out.println("recall:"+recall);
		System.out.println("precision:"+precision);
		System.out.println("fscore:"+fscore);
		
	}	
		
			
		
		
		
}
	
	
	
	
	
	
	

