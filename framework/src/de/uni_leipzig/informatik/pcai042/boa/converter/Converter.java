/*
 * Converter.java
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

package de.uni_leipzig.informatik.pcai042.boa.converter;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;
import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;
import de.uni_leipzig.informatik.pcai042.boa.manager.SentenceLoader;

/**
 * 
 * @author Duc Huy Bui; Christian Kahmann
 */
public abstract class Converter
{
	
	private static HashMap<String, BigDecimal> conversionStandard;
	private static HashMap<String, BigDecimal> conversionUnit;
	/**
	 * 
	 * 
	 * @param token
	 * @param number
	 */

	public Converter(String token, double number)
	{
	
	}	
	public Converter()
	{
		
	}
	
	/**
	 * 
	 * @param annotation
	 * @return
	 */
	public ArrayList<String> convertUnit(BoaAnnotation annotation)
	{
		
		// null --> placeholder for return
		return null;
	}

	/**
	 * Test
	 */
	public static void main(String arg[])
	{
		
		
		
		int test=0;
		String []g={"g","gram"};
		String []t={"t","ton","metric ton","metric tons","metric tons"};
		String []kg={"kg","kilogram"};
		SentenceLoader s1;
		double f;
		s1=null;
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
		
		
		for (int i = 0; i < s1.getSentenceCount(); i++)
		{
			
			
				for (int k = 0; k < s1.getSentence(i).getAnnotations().size(); k++)
				{
					String help2=null;
					if(s1.getSentence(i).getAnnotations().get(k).getType().toString()!="DATE"&&s1.getSentence(i).getAnnotations().get(k).getType().toString()!="TEMPERATURE")
					{	
						conversionStandard  = new ConfigLoader().openConfigConversion("Standard", s1.getSentence(i).getAnnotations().get(k).getType().toString());
						conversionUnit  = new ConfigLoader().openConfigConversion("Unit", s1.getSentence(i).getAnnotations().get(k).getType().toString());
					}	
				
					//System.out.println(conversionStandard);
					for (int j = 0; j < s1.getSentence(i).getAnnotations().get(k).getTokens().size(); j++)
					{int help=0;
						if(s1.getSentence(i).getAnnotations().get(k).getType().toString()=="WEIGHT")
						{
							
							if(help==0)
							for(int l=0;l<g.length;l++)
							{
								if(g[l].equals(s1.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
								{
									//System.out.println("satz"+i+g[l]+"und"+s1.getSentence(i).getAnnotations().get(k).getTokens().get(j));
									help2="g";
									help++;
								}
							}	
							if(help==0)
							for(int l=0;l<t.length;l++)
							{	
								if(t[l].equals(s1.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
								{
									//System.out.println("satz"+i+"ton");
									help++;
									help2="t";
								}
							
							}	
							if(help==0)
							for(int l=0;l<kg.length;l++)
							{	
								if(kg[l].equals(s1.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
								{
									//System.out.println("satz"+i+"kg");
									help++;
									help2="kg";
								}
						
							}
						
						}
						else
						{
							
							//abgleich mit längen string
						}
						
						
						if(help!=0)
						{	BigDecimal b= new BigDecimal("10");
						System.out.println(conversionStandard.get(help2));
						b=b.add(conversionStandard.get(help2));
							
							
						}	
					}
						
						
						
						
					}
					
					
					
		
				
				
		}
		
	}
}
