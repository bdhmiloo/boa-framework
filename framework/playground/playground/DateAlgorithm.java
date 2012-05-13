/*
 * DateAlgorithm.java

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaSentence;
import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;
import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation.Type;
import de.uni_leipzig.informatik.pcai042.boa.searcher.SearchAlgorithm;

/**
 * Frist Try of DateAlgorithm. 
 * It has been not tested yet. It still need some Improvements.
 * Important: This Algorithm currently search for month-day structures without year.
 * 
 * @author Benedict Preﬂler
 *
 */

public class DateAlgorithm extends SearchAlgorithm
{
	private Set<String> monthSet;
	private Set<String> daySet;
	
	public DateAlgorithm()
	{

	}
	
	public DateAlgorithm(Set<String> surfaceForms, Type annoType)
	{
		super(surfaceForms, annoType);
		
		Iterator<String> dateIT= surfaceForms.iterator();
		monthSet=new HashSet<String>(Arrays.asList(dateIT.next().split("&&")));
		daySet=new HashSet<String>(Arrays.asList(dateIT.next().split("&&")));
	}
	
	public void search(BoaSentence sentence)
	{
		String currentToken = "", dayToken = "", metaToken = "";
		int positionDay, positionMeta, positionMonth=-1;	//-1 means initial position			
		
		Iterator<String> tokensIT=sentence.getTokens().iterator();
		while(tokensIT.hasNext())
		{
			currentToken=tokensIT.next();
			positionMonth++;
			
			//Step 1: Check, if Token contains a month-surface form.
			if(monthSet.contains(currentToken.toLowerCase()))
			{
				//System.out.println(currentToken);
				//Step 2: Check, if next Token is day
				if(tokensIT.hasNext())
				{
					positionDay=-1;
					Iterator<String> findDay=sentence.getTokens().iterator();
					
					while(positionDay!=positionMonth+1)
					{
						dayToken=findDay.next();
						positionDay++;
					}
					if(daySet.contains(dayToken.toLowerCase()))
					{
						ArrayList<String> annoTokens = new ArrayList<String>();
						annoTokens.add(dayToken);
						annoTokens.add(currentToken);
						
						sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
						//System.out.println("Annotating " + " " + dayToken + " " + currentToken);
					}
				}
				//Step 3: Check, if found month is the first Token in List
				else if(positionMonth==0)
				{
					ArrayList<String> annoTokens = new ArrayList<String>();
					annoTokens.add(currentToken);
					
					sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
				}
				//Step 4: Check previous Token
				else
				{
					
					positionDay=-1;
					Iterator<String> findDay=sentence.getTokens().iterator();
					
					while(positionDay!=positionMonth-1&&findDay.hasNext())
					{
						dayToken=findDay.next();
						positionDay++;
					}
					
					//Step 3: Check, if Token before found month is a day or the word "of"
					if(dayToken.equals("of"))
					{
						positionMeta=-1;
						Iterator<String> findDayMeta=sentence.getTokens().iterator();
						
						while(positionMeta!=positionDay-1&&findDayMeta.hasNext())
						{
							metaToken=findDayMeta.next();
							positionMeta++;
						}
						
						//Step 4: Check, if Token before found "of" contains day
						if(daySet.contains(metaToken.toLowerCase()))
						{
							ArrayList<String> annoTokens = new ArrayList<String>();
							annoTokens.add(metaToken);
							annoTokens.add(dayToken);
							annoTokens.add(currentToken);
							
							sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
							//System.out.println("Annotating " + metaToken + " " + dayToken +  " " + currentToken);
						}
					}
					else if(daySet.contains(dayToken.toLowerCase()))
					{
						ArrayList<String> annoTokens = new ArrayList<String>();
						annoTokens.add(dayToken);
						annoTokens.add(currentToken);
						
						sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
						//System.out.println("Annotating " + " " + dayToken + " " + currentToken);
					}
				}
			}
		}
	}
	
	public void setSurForms(Set<String> surfaceForms)
	{
		Iterator<String> dateIT= surfaceForms.iterator();
		monthSet=new HashSet<String>(Arrays.asList(dateIT.next().split("&&")));
		daySet=new HashSet<String>(Arrays.asList(dateIT.next().split("&&")));
	}
}
