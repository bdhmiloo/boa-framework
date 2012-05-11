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

/**
 * This is a Testclass for LabelSearcherDate. When its finished, it will be merged
 * with LabelSearcherDate.
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
 * 
 * @author Benedict Preﬂler
 *
 */

public abstract class DateAlgorithm extends SearchAlgorithm
{
	private Set<String> monthSet;
	private Set<String> daySet;
	
	public DateAlgorithm(Set<String> surfaceForms, Type annoType)
	{
		super(surfaceForms, annoType);
		
		Iterator<String> dateIT= surfaceForms.iterator();
		monthSet=new HashSet<String>(Arrays.asList(dateIT.next().split("&&")));
		daySet=new HashSet<String>(Arrays.asList(dateIT.next().split("&&")));
	}
	
	public void searchViaMonth(BoaSentence sentence)
	{
		String currentToken = "", dayToken = "", metaToken = "";
		int positionDay, positionMeta, positionMonth=-1;				
		
		Iterator<String> tokensIT=sentence.getTokens().iterator();
		while(tokensIT.hasNext())
		{
			currentToken=tokensIT.next();
			positionMonth++;
			
			if(monthSet.contains(currentToken))
			{
				if(positionMonth==0)
				{
					ArrayList<String> annoTokens = new ArrayList<String>();
					annoTokens.add(currentToken);
					
					sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
				}
				else
				{
					positionDay=-1;
					Iterator<String> findDay=sentence.getTokens().iterator();
					
					while(positionDay!=positionMonth-1&&findDay.hasNext())
					{
						dayToken=findDay.next();
						positionDay++;
					}
					if(dayToken.equals("of"))
					{
						positionMeta=-1;
						Iterator<String> findDayMeta=sentence.getTokens().iterator();
						
						while(positionMeta!=positionDay-1&&findDayMeta.hasNext())
						{
							metaToken=findDayMeta.next();
							positionMeta++;
						}
						if(daySet.contains(metaToken))
						{
							ArrayList<String> annoTokens = new ArrayList<String>();
							annoTokens.add(metaToken);
							annoTokens.add(dayToken);
							annoTokens.add(currentToken);
							
							sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
						}
					}
					else if(daySet.contains(dayToken))
					{
						ArrayList<String> annoTokens = new ArrayList<String>();
						annoTokens.add(dayToken);
						annoTokens.add(currentToken);
						
						sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
					}
				}
			}
		}
	}
}
