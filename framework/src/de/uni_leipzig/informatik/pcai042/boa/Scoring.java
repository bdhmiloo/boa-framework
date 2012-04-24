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
import java.text.*;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class Scoring
{
	public static void main(String[] args)
	{
		SentenceLoader s1 = null;
		double kp,fp,countGoldAnno;
		kp=0;//kp=korrekte Annotationen
		fp=0;//fp=falsche Annotationen
		countGoldAnno=0;//=ANzahl der Annotationen im Goldstandard
		int hilfe,hilfe2,t1,t2;
		double recall,precision,fscore;
		t1=0;
		t2=0;
		
		try
		{
			s1 = new SentenceLoader(new File("Goldstandard.xml"));
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
			s2 = new SentenceLoader(new File("Goldstandard.xml"));
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
		
		
		
		
		System.out.println("recall       precision    fscore");
				
		//kp ermitteln
		
		for(int i=0;i<s1.getSentenceCount();i++) 
		{
			countGoldAnno+=s1.getSentence(i).getAnnotations().size();
			
			for(int j=0;j<s2.getSentence(i).getAnnotations().size();j++)
			{	
				for(int k=0;k<s1.getSentence(i).getAnnotations().size();k++)
				{	
					if(s2.getSentence(i).getAnnotations().get(j).toString().equals(s1.getSentence(i).getAnnotations().get(k).toString()))
					t1=1;
					
				}	
				hilfe2=0;
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
												hilfe2++;
										}
									}
								}
							}	
						}
					}
							
				}	//	System.out.println("t1:"+t1);
						//System.out.println("satz:"+i);
						//System.out.println("annotation:"+j);
						//System.out.println("h2:"+hilfe2);
						//System.out.println("anzahl tokens in sfalsch:"+s2.getSentence(i).getAnnotations().get(j).getTokens().size());
						//System.out.println("  ");
						if(hilfe2==s2.getSentence(i).getAnnotations().get(j).getTokens().size())
							t2=1;
						if(t1==1&&t2==1)
							kp++;
			}
					
			for(int a=0;a<s2.getSentence(i).getAnnotations().size();a++)
			{
					
				hilfe=0;
				for(int c=0;c<s1.getSentence(i).getAnnotations().size();c++)
				{
					if(!(s2.getSentence(i).getAnnotations().get(a).toString().equals(s1.getSentence(i).getAnnotations().get(c).toString())))
					{
						hilfe++;
					}
										
				}
				if(hilfe==s1.getSentence(i).getAnnotations().size())
				{
					fp++;
				}
				
			}	
			recall=(kp)/(countGoldAnno);
			precision=kp/(kp+fp);
			fscore=(2*precision*recall)/(recall+precision);
			
				
			DecimalFormat df =   new DecimalFormat  ( ",0.00000000" );
		    System.out.print(  df.format(recall)   +"   ");   
			System.out.print(df.format(precision)  +"   ");	  
			System.out.println(df.format(fscore));
			
			
		}	
			
				
			
		
		
		System.out.println("korrekte Annotationen:"+kp);
		System.out.println("falsche Annotationen:"+fp);
		System.out.println("Anzahl Annotationen im Goldstandard:"+countGoldAnno);
		
		
			
		
		
	}	
		
			
		
		
		
}
	
	
	
	
	
	
	

