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
 * This Algorithm search for date structures in sentences and annotate them.
 * It still need some Improvements.
 * Important: Year search is still beta.
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
		String nextSet="", subSet="";
		
		
		daySet=new HashSet<String>(Arrays.asList(dateIT.next().split("&&")));
		monthSet=new HashSet<String>(Arrays.asList(dateIT.next().split("&&")));
		
		/*
		while(dateIT.hasNext())
		{
			
			
			nextSet=dateIT.next();
			
			if(nextSet.startsWith("D"))
			{
				subSet=nextSet.substring(2);
				daySet=new HashSet<String>(Arrays.asList(subSet.split("&&")));
			}
			if(nextSet.startsWith("M"))
			{
				subSet=nextSet.substring(2);
				monthSet=new HashSet<String>(Arrays.asList(subSet.split("&&")));
			}
			
		}*/
	}
	
	public void search(BoaSentence sentence)
	{
		String currentToken = "", dayToken = "", metaToken = "", yearToken="";
		int positionDay, positionMeta, positionYear, positionMonth=-1;										//-1 means initial position		
		boolean annotated=false;
		
		Iterator<String> tokensIT=sentence.getTokens().iterator();
		while(tokensIT.hasNext())																			//while has Tokens left
		{
			currentToken=tokensIT.next();
			positionMonth++;
			annotated=false;
			if(monthSet.contains(currentToken.toLowerCase()))												//if month=true, 
			{
				if(tokensIT.hasNext())																		//if month=true next=true
				{
					positionDay=-1;
					Iterator<String> findDay=sentence.getTokens().iterator();
					while(positionDay!=positionMonth+1)														//until day=month+1
					{
						dayToken=findDay.next();
						positionDay++;
					}
					if(daySet.contains(dayToken.toLowerCase()))												//if month=true day=true
					{
						if(findDay.hasNext())																//if month=true day=true next=true
						{
							positionYear=-1;
							Iterator<String> findYear=sentence.getTokens().iterator();							
							while(positionYear != positionDay+1)											//until year=day+1
							{
								yearToken=findYear.next();
								positionYear++;
							}	
							if(checkIfYear(yearToken))														//if month=true day=true year=true
							{
								ArrayList<String> annoTokens = new ArrayList<String>();								
								annoTokens.add(currentToken);
								annoTokens.add(dayToken);
								annoTokens.add(yearToken);								
								sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
								annotated=true;
							}
							else if(yearToken.equals(",")&&findYear.hasNext())								//if month=true day=true ','=true next=true
							{
								positionMeta=-1;
								Iterator<String> findYearMeta=sentence.getTokens().iterator();						
								while(positionMeta!=positionYear+1)											//until meta=day-1
								{
									metaToken=findYearMeta.next();
									positionMeta++;
								}
								if(checkIfYear(metaToken))													//if month=true day=true ','=true year=true
								{
									ArrayList<String> annoTokens = new ArrayList<String>();								
									annoTokens.add(currentToken);
									annoTokens.add(dayToken);
									annoTokens.add(yearToken);
									annoTokens.add(metaToken);
									sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
									annotated=true;
								}
								else																		//if month=true day=true ','=true year=false
								{
									ArrayList<String> annoTokens = new ArrayList<String>();								
									annoTokens.add(currentToken);
									annoTokens.add(dayToken);								
									sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
									annotated=true;
								}								
							}
							else																			//if month=true day=true ','=false year=false
							{
								ArrayList<String> annoTokens = new ArrayList<String>();								
								annoTokens.add(currentToken);
								annoTokens.add(dayToken);								
								sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
								annotated=true;
							}
						}
						else																				//if month=true day=true next=false
						{
							ArrayList<String> annoTokens = new ArrayList<String>();							
							annoTokens.add(currentToken);
							annoTokens.add(dayToken);							
							sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
							annotated=true;
						}
					}
					else																					//if month=true year=true month:isFirstToken=true
					{
						if(positionMonth==0&&checkIfYear(dayToken))
						{
							ArrayList<String> annoTokens = new ArrayList<String>();								
							annoTokens.add(currentToken);
							annoTokens.add(dayToken);								
							sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
							annotated=true;
						}
					}
				}																							//end month=true next=true
	
				if(!annotated)																				//if not annotated
				{
					if(positionMonth==0)																	//if month=true month:isFirstToken=true
					{
						ArrayList<String> annoTokens = new ArrayList<String>();								
						annoTokens.add(currentToken);								
						sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
						annotated=true;
					}
					else																					//if month=true month:isFirstToken=false
					{
						positionDay=-1;
						Iterator<String> findDay=sentence.getTokens().iterator();					
						while(positionDay!=positionMonth-1&&findDay.hasNext())								//until day=month-1
						{
							dayToken=findDay.next();
							positionDay++;
						}
						if(dayToken.equals("of")&&positionDay!=0)											//if of=true and of:isFirstToken=false
						{
							positionMeta=-1;
							Iterator<String> findDayMeta=sentence.getTokens().iterator();						
							while(positionMeta!=positionDay-1&&findDayMeta.hasNext())						//until meta=day-1
							{
								metaToken=findDayMeta.next();
								positionMeta++;
							}						
							if(daySet.contains(metaToken.toLowerCase()))									//if day=true of=true month=true
							{
								if(tokensIT.hasNext())														//if day=true of=true month=true next=true
								{
									positionYear=-1;
									Iterator<String> findYear=sentence.getTokens().iterator();								
									while(positionYear!=positionMonth+1)									//until year=month+1
									{
										yearToken=findYear.next();
										positionYear++;
									}								
									if(checkIfYear(yearToken))												//if day=true of=true month=true year=true
									{
										ArrayList<String> annoTokens = new ArrayList<String>();								
										annoTokens.add(metaToken);
										annoTokens.add(dayToken);
										annoTokens.add(currentToken);
										annoTokens.add(yearToken);
										sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
										annotated=true;
									}
									else																	//if day=true of=true month=true year=false
									{
										ArrayList<String> annoTokens = new ArrayList<String>();									
										annoTokens.add(metaToken);
										annoTokens.add(dayToken);
										annoTokens.add(currentToken);									
										sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
										annotated=true;
									}
								}
								else																		//if day=true of=true month=true day:ifFirstToken=true
								{
										ArrayList<String> annoTokens = new ArrayList<String>();
										annoTokens.add(metaToken);
										annoTokens.add(dayToken);
										annoTokens.add(currentToken);																							
										sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
										annotated=true;
								}
							}																				//if day=false of=true month=true
							else
							{
								ArrayList<String> annoTokens = new ArrayList<String>();
								annoTokens.add(currentToken);							
								sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
								annotated=true;
							}
						}
						else if(daySet.contains(dayToken.toLowerCase()))									//if day=true of=false month=true
						{
							if(tokensIT.hasNext())															//if day=true month=true next=true
							{
								positionYear=-1;
								Iterator<String> findYear=sentence.getTokens().iterator();								
								while(positionYear!=positionMonth+1)										//until year=month+1
								{
									yearToken=findYear.next();
									positionYear++;
								}								
								if(checkIfYear(yearToken))													//if day=true month=true year=true
								{
									ArrayList<String> annoTokens = new ArrayList<String>();								
									annoTokens.add(dayToken);
									annoTokens.add(currentToken);
									annoTokens.add(yearToken);
									sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
									annotated=true;
								}
								else																		//if day=true month=true year=false
								{
									ArrayList<String> annoTokens = new ArrayList<String>();									
									annoTokens.add(dayToken);
									annoTokens.add(currentToken);									
									sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
									annotated=true;								
								}
							}
							else																			//if day=true month=true next=false
							{
								ArrayList<String> annoTokens = new ArrayList<String>();									
								annoTokens.add(dayToken);
								annoTokens.add(currentToken);									
								sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
								annotated=true;	
							}
						}
						else																				//if day=false month=true
						{
							ArrayList<String> annoTokens = new ArrayList<String>();									
							annoTokens.add(currentToken);									
							sentence.getAnnotations().add(new BoaAnnotation(annoType, annoTokens));
							annotated=true;	
						}
					}
				}
			} //End If contains month
		} //End while token
	}
	
	public void setSurForms(Set<String> surfaceForms)
	{
		Iterator<String> dateIT= surfaceForms.iterator();
		String nextSet="", subSet="";		
		
		daySet=new HashSet<String>(Arrays.asList(dateIT.next().split("&&")));
		monthSet=new HashSet<String>(Arrays.asList(dateIT.next().split("&&")));
		/*		
		while(dateIT.hasNext())
		{
			nextSet=dateIT.next();
			
			if(nextSet.startsWith("D"))
			{
				subSet = nextSet.substring(2);
				daySet = new HashSet<String>(Arrays.asList(subSet.split("&&")));
			}
			if(nextSet.startsWith("M"))
			{
				subSet = nextSet.substring(2);
				monthSet = new HashSet<String>(Arrays.asList(subSet.split("&&")));
			}
			
		}*/
	}
	/**
	 * Checks if a Token contains a year date. Only works for years after 1 CE.
	 * @param token
	 * @return
	 */
	
	public boolean checkIfYear(String token)
	{	
		try
		{
			
			if(Integer.parseInt(token)>0)
			{
				return true;
			}
		}
		catch(NumberFormatException e){}
				
		return false;
	}

}
