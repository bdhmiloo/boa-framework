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

public class scoring
{
	public static void main(String[] args)
	{
		SentenceLoader s1 = null;
		int kp,fp;
		kp=0;
		fp=0;
		try
		{
			s1 = new SentenceLoader(new File("C:/Users/Christian/workspace/scoring/Goldstandard.xml"));
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
		
		if (s1 == null)
			return;
		
		for(int i=0;i<s1.getSentenceCount();i++) 
		{
			for(int j=0;j<s1.getSentence(i).getAnnotations().size();j++)
			{	
				for(int k=0;k<s1.getSentence(i).getAnnotations().size();k++)
				{	
					if(s2.getSentence(i).getAnnotations().get(j).toString().equals(s1.getSentence(i).getAnnotations().get(k).toString()))
					{
						kp++;
					}
					else
					{
						fp++;
					}	
				}	
			}		
					
		}
		System.out.println(kp);
		System.out.println(fp);
		
	}	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	

