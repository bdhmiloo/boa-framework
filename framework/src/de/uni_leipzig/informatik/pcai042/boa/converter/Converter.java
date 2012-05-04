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
import edu.stanford.nlp.io.EncodingPrintWriter.out;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
/**
 * 
 * @author Duc Huy Bui; Christian Kahmann
 */
public abstract class Converter
{
	
	private static HashMap<String, BigDecimal> conversionStandardw;
	private static HashMap<String, BigDecimal> conversionUnitw;
	private static HashMap<String, BigDecimal> conversionStandardl;
	private static HashMap<String, BigDecimal> conversionUnitl;
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
	protected static  boolean checkIfNumber(String token)
	{	int help=0;
		try
		{
			Integer.parseInt(token);
			help++;
		}
		catch(NumberFormatException e){}
		try
		{
			if(token.contains(",")) token = token.replace(",", ".");
			Double.parseDouble(token);
			help++;
			
		}
		catch(NumberFormatException e){}
		
		if(help==0)		
		return false;
		else
		return true;	
	}
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
		
		File file = new File("test.txt");
        try {
            System.setOut(new PrintStream(new FileOutputStream(file,true)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		int count=0;
		String[] µm={"µm"};
		String[] mm={"nm","nanometer","nanometers"};
		String[] cm={"cm","centimeter","centimeters"};
		String[] dm={"dm","decimeters","decimeter"};
		String[] m={"m","meter","meters","metres"};
		String[] km={"km","kilometer","kilometers"};
		String[] ft={"ft","feet","feets","'"};
		String[] in={"in","insh","inshes",};
		String[] yard={"yard","yards","yd","yds"};
		String[] seamile={"sm","seamile","seamiles","nautic mile","nautic miles"};
		String[] mile={"mile","miles"};
		String []µg={"µg","microgram","micrograms"};
		String []mg={"mg","milligram","milligrams"};
		String []Kt={"Kt","carat"};
		String []lbs={"lbs","lb","labs","pound","pounds"};
		String []oz={"oz","ounze"};
		String []g={"g","gram"};
		String []t={"t","ton","metric ton","metric tons","tons"};
		String []kg={"kg","kilogram","kilo","kg."};
		String[] help3={"g","t","kg","oz","lbs","Kt","µg","mg"};
		String[] help4={"µm","mm","cm","dm","m","km","mile","seamile","yard","ft","in"};
		SentenceLoader s1;
		
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
		String [][] ausgabe=new String[s1.getSentenceCount()][20];
		
		for (int i = 0; i <s1.getSentenceCount() ; i++)
		{
			
			
				for (int k = 0; k < s1.getSentence(i).getAnnotations().size(); k++)
				{
					String help2=null;
					String help5=null;
					if(s1.getSentence(i).getAnnotations().get(k).getType().toString()=="WEIGHT")
					{	
						conversionStandardw  = new ConfigLoader().openConfigConversion("Standard", "WEIGHT");
						conversionUnitw = new ConfigLoader().openConfigConversion("Unit", "WEIGHT");
						
						
				
					//System.out.println(conversionStandard);
					for (int j = 0; j < s1.getSentence(i).getAnnotations().get(k).getTokens().size(); j++)
					{	int help=0;
						
							
							
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
							if(help==0)
								for(int l=0;l<Kt.length;l++)
								{	
									if(Kt[l].equals(s1.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
									{
										//System.out.println("satz"+i+"ton");
										help++;
										help2="Kt";
									}
								
								}								
							if(help==0)
								for(int l=0;l<oz.length;l++)
								{	
									if(oz[l].equals(s1.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
									{
										//System.out.println("satz"+i+"ton");
										help++;
										help2="oz";
									}
								
								}	
							if(help==0)
								for(int l=0;l<µg.length;l++)
								{	
									if(µg[l].equals(s1.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
									{
										//System.out.println("satz"+i+"ton");
										help++;
										help2="µg";
									}
								
								}	
							if(help==0)
								for(int l=0;l<mg.length;l++)
								{	
									if(mg[l].equals(s1.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
									{
										//System.out.println("satz"+i+"ton");
										help++;
										help2="mg";
									}
								
								}	
							if(help==0)
								for(int l=0;l<lbs.length;l++)
								{	
									if(lbs[l].equals(s1.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
									{
										//System.out.println("satz"+i+"ton");
										help++;
										help2="lbs";
									}
								
								}	
							
							
							
								
						
					}
						
						
					
						
						
							
							
						
						if(help2!=null)
						{
						//System.out.println(i+" "+k+" "+j+" "+help);
						
							double z=0;
							
							//System.out.println(conversionStandard.get(help2));
						
							//System.out.println(i+" "+k+" "+j);
							for(int w=0;w<s1.getSentence(i).getAnnotations().get(k).getTokens().size();w++)
							{
								if(checkIfNumber(s1.getSentence(i).getAnnotations().get(k).getTokens().get(w)))
								{
									String token=s1.getSentence(i).getAnnotations().get(k).getTokens().get(w);
									if(token.contains("+"))
										token=token.replace("+", "");
									if(token.contains(","))
										token = token.replace(",", ".");
									if(token.contains("."))	
										z=Double.parseDouble(token);
									
									else
									{	
										z=(double)Integer.parseInt(token);
										
									}
								}
							}	
							
							
							
								BigDecimal a= new BigDecimal(z);
							BigDecimal standard=a.multiply(conversionStandardw.get(help2));
							
							ausgabe[i][k]="satz"+i+" annotation"+k+":";
								
							for(int d=0;d<help3.length;d++)
							{
								BigDecimal neu=standard.multiply(conversionUnitw.get(help3[d]));
								
								if(help3[d].equals("kg"))
								{	
									for(int l=0;l<kg.length;l++)
									ausgabe[i][k]=ausgabe[i][k].concat(neu+" "+kg[l]+"  //  ");
									count++;
								}
								if(help3[d].equals("g"))
								{	
									for(int l=0;l<g.length;l++)
									ausgabe[i][k]=ausgabe[i][k].concat(neu+" "+g[l]+"  //  ");
									count++;
								}
								if(help3[d].equals("t"))
								{	
									for(int l=0;l<t.length;l++)
									ausgabe[i][k]=ausgabe[i][k].concat(neu+" "+t[l]+"  //  ");
									count++;
								}
								if(help3[d].equals("Kt"))
								{	
									for(int l=0;l<Kt.length;l++)
									ausgabe[i][k]=ausgabe[i][k].concat(neu+" "+Kt[l]+"  //  ");
									count++;
								}
								if(help3[d].equals("oz"))
								{	
									for(int l=0;l<oz.length;l++)
									ausgabe[i][k]=ausgabe[i][k].concat(neu+" "+oz[l]+"  //  ");
									count++;
								}
								if(help3[d].equals("lbs"))
								{	
									for(int l=0;l<lbs.length;l++)
									ausgabe[i][k]=ausgabe[i][k].concat(neu+" "+lbs[l]+"  //  ");
									count++;
								}
								if(help3[d].equals("mg"))
								{	
									for(int l=0;l<mg.length;l++)
									ausgabe[i][k]=ausgabe[i][k].concat(neu+" "+mg[l]+"  //  ");
									count++;
								}
								if(help3[d].equals("µg"))
								{	
									for(int l=0;l<µg.length;l++)
									ausgabe[i][k]=ausgabe[i][k].concat(neu+" "+µg[l]+"  //  ");
									count++;
								}
							}
														
							
								
							}
								
								
													
								
							}
					if(s1.getSentence(i).getAnnotations().get(k).getType().toString()=="LINEAR_MEASURE")
					{	
						conversionStandardl  = new ConfigLoader().openConfigConversion("Standard", "LINEAR_MEASURE");
						conversionUnitl = new ConfigLoader().openConfigConversion("Unit", "LINEAR_MEASURE");
						System.out.print(conversionStandardl.get("m"));
						
				
					//System.out.println(conversionStandard);
					for (int j = 0; j < s1.getSentence(i).getAnnotations().get(k).getTokens().size(); j++)
					{	int help=0;
						
							
							
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
							if(help==0)
								for(int l=0;l<Kt.length;l++)
								{	
									if(Kt[l].equals(s1.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
									{
										//System.out.println("satz"+i+"ton");
										help++;
										help2="Kt";
									}
								
								}								
							if(help==0)
								for(int l=0;l<oz.length;l++)
								{	
									if(oz[l].equals(s1.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
									{
										//System.out.println("satz"+i+"ton");
										help++;
										help2="oz";
									}
								
								}	
							if(help==0)
								for(int l=0;l<µg.length;l++)
								{	
									if(µg[l].equals(s1.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
									{
										//System.out.println("satz"+i+"ton");
										help++;
										help2="µg";
									}
								
								}	
							if(help==0)
								for(int l=0;l<mg.length;l++)
								{	
									if(mg[l].equals(s1.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
									{
										//System.out.println("satz"+i+"ton");
										help++;
										help2="mg";
									}
								
								}	
							if(help==0)
								for(int l=0;l<lbs.length;l++)
								{	
									if(lbs[l].equals(s1.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
									{
										//System.out.println("satz"+i+"ton");
										help++;
										help2="lbs";
									}
								
								}	
							
							
							
								
						
					}
						
						
					
						
						
							
							
						
						if(help2!=null)
						{
						//System.out.println(i+" "+k+" "+j+" "+help);
						
							double z=0;
							
							//System.out.println(conversionStandard.get(help2));
						
							//System.out.println(i+" "+k+" "+j);
							for(int w=0;w<s1.getSentence(i).getAnnotations().get(k).getTokens().size();w++)
							{
								if(checkIfNumber(s1.getSentence(i).getAnnotations().get(k).getTokens().get(w)))
								{
									String token=s1.getSentence(i).getAnnotations().get(k).getTokens().get(w);
									if(token.contains("+"))
										token=token.replace("+", "");
									if(token.contains(","))
										token = token.replace(",", ".");
									if(token.contains("."))	
										z=Double.parseDouble(token);
									
									else
									{	
										z=(double)Integer.parseInt(token);
										
									}
								}
							}	
							
							
							
								BigDecimal a= new BigDecimal(z);
							BigDecimal standard=a.multiply(conversionStandardw.get(help2));
							
							ausgabe[i][k]="satz"+i+" annotation"+k+":";
								
							for(int d=0;d<help3.length;d++)
							{
								BigDecimal neu=standard.multiply(conversionUnitw.get(help3[d]));
								
								if(help3[d].equals("kg"))
								{	
									for(int l=0;l<kg.length;l++)
									ausgabe[i][k]=ausgabe[i][k].concat(neu+" "+kg[l]+"  //  ");
									count++;
								}
								if(help3[d].equals("g"))
								{	
									for(int l=0;l<g.length;l++)
									ausgabe[i][k]=ausgabe[i][k].concat(neu+" "+g[l]+"  //  ");
									count++;
								}
								if(help3[d].equals("t"))
								{	
									for(int l=0;l<t.length;l++)
									ausgabe[i][k]=ausgabe[i][k].concat(neu+" "+t[l]+"  //  ");
									count++;
								}
								if(help3[d].equals("Kt"))
								{	
									for(int l=0;l<Kt.length;l++)
									ausgabe[i][k]=ausgabe[i][k].concat(neu+" "+Kt[l]+"  //  ");
									count++;
								}
								if(help3[d].equals("oz"))
								{	
									for(int l=0;l<oz.length;l++)
									ausgabe[i][k]=ausgabe[i][k].concat(neu+" "+oz[l]+"  //  ");
									count++;
								}
								if(help3[d].equals("lbs"))
								{	
									for(int l=0;l<lbs.length;l++)
									ausgabe[i][k]=ausgabe[i][k].concat(neu+" "+lbs[l]+"  //  ");
									count++;
								}
								if(help3[d].equals("mg"))
								{	
									for(int l=0;l<mg.length;l++)
									ausgabe[i][k]=ausgabe[i][k].concat(neu+" "+mg[l]+"  //  ");
									count++;
								}
								if(help3[d].equals("µg"))
								{	
									for(int l=0;l<µg.length;l++)
									ausgabe[i][k]=ausgabe[i][k].concat(neu+" "+µg[l]+"  //  ");
									count++;
								}
							}
														
							
								
							}
								
								
													
								
							}
							
					
					
			
						
						
					
			 
			        
					System.out.println(ausgabe[i][k]);	
				
					
				
	}
}System.out.println(count);
		}}
