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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
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
//	private static HashMap<String, BigDecimal> conversionStandard;
//	private static HashMap<String, BigDecimal> conversionUnit;
	
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
	
	public Converter(String unit)
	{
		
		
	}
	
	/**
	 * 
	 */
	public Converter()
	{
		
	}
	
	/**
	 * Checks whether token is a number or not.
	 * 
	 * @param token
	 * @return true - if token is a number, false - if token is not a number
	 */
	protected static boolean checkIfNumber(String token)
	{
		int help = 0;
		
		// testing integer
		try
		{
			Integer.parseInt(token);
			help++;
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		
		// testing double
		try
		{
			if (token.contains(","))
				token = token.replace(",", ".");
			Double.parseDouble(token);
			help++;
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		
		if (help == 0)
			return false;
		else
			return true;
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
		File file = new File("test.txt");
		try
		{
			System.setOut(new PrintStream(new FileOutputStream(file, true)));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		int countCreatedSurfaceForms = 0;
		int countProcessibleAnnotations = 0;
		int countTotalAnnotations = 0;
		
		String[] mm = { "nm", "nanometer", "nanometers" };
		String[] cm = { "cm", "centimeter", "centimeters" };
		String[] dm = { "dm", "decimeters", "decimeter" };
		String[] m = { "m", "meter", "meters", "metres" };
		String[] km = { "km", "kilometer", "kilometers" };
		String[] ft = { "ft", "feet", "feet", "'" };
		String[] inch = { "inch", "in", "inches", };
		String[] yard = { "yard", "yards", "yd", "yds" };
		String[] seamile = { "sm", "seamile", "seamiles", "nautic mile", "nautic miles" };
		String[] mile = { "mile", "miles" };
		
		String[] mg = { "mg", "milligram", "milligrams" };
		String[] Kt = { "Kt", "carat" };
		String[] lbs = { "lbs", "lb", "labs", "pound", "pounds" };
		String[] oz = { "oz", "ounze" };
		String[] g = { "g", "gram" };
		String[] t = { "t", "ton", "metric ton", "metric tons", "tons" };
		String[] kg = { "kg", "kilogram", "kilo", "kg." };
		
		String[] help3 = { "g", "t", "kg", "oz", "lbs", "Kt", "mg" };
		String[] help4 = { "mm", "cm", "dm", "m", "km", "mile", "seamile", "yard", "ft", "inch" };
		
		SentenceLoader sentence = null;
		
		try
		{
			sentence = new SentenceLoader(new File("goldstandard.xml"));
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
		
		if (sentence == null)
			return;
		
		String[][] printOut = new String[sentence.getSentenceCount()][20];
		
		for (int i = 0; i < sentence.getSentenceCount(); i++)
		{
			for (int k = 0; k < sentence.getSentence(i).getAnnotations().size(); k++)
			{
				countTotalAnnotations++;
				String help2 = null;
				String help5 = null;
				
				if (sentence.getSentence(i).getAnnotations().get(k).getType().toString() == "WEIGHT")
				{
					conversionStandardw = new ConfigLoader().openConfigConversion("Standard", "WEIGHT");
					conversionUnitw = new ConfigLoader().openConfigConversion("Unit", "WEIGHT");
					
					for (int j = 0; j < sentence.getSentence(i).getAnnotations().get(k).getTokens().size(); j++)
					{
						int help = 0;
						
						for (int l = 0; l < g.length; l++)
						{
							if (g[l].equals(sentence.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
							{
								help2 = "g";
								help++;
							}
						}
						if (help == 0)
							for (int l = 0; l < t.length; l++)
							{
								if (t[l].equals(sentence.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
								{
									
									help++;
									help2 = "t";
								}
								
							}
						if (help == 0)
							for (int l = 0; l < kg.length; l++)
							{
								if (kg[l].equals(sentence.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
								{
									
									help++;
									help2 = "kg";
								}
								
							}
						if (help == 0)
							for (int l = 0; l < Kt.length; l++)
							{
								if (Kt[l].equals(sentence.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
								{
									
									help++;
									help2 = "Kt";
								}
								
							}
						if (help == 0)
							for (int l = 0; l < oz.length; l++)
							{
								if (oz[l].equals(sentence.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
								{
									
									help++;
									help2 = "oz";
								}
								
							}
						
						if (help == 0)
							for (int l = 0; l < mg.length; l++)
							{
								if (mg[l].equals(sentence.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
								{
									
									help++;
									help2 = "mg";
								}
								
							}
						if (help == 0)
							for (int l = 0; l < lbs.length; l++)
							{
								if (lbs[l].equals(sentence.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
								{
									
									help++;
									help2 = "lbs";
								}
							}
					}
					
					if (help2 != null)
					{
						countProcessibleAnnotations++;
						double z = 0;
						
						for (int w = 0; w < sentence.getSentence(i).getAnnotations().get(k).getTokens().size(); w++)
						{
							if (checkIfNumber(sentence.getSentence(i).getAnnotations().get(k).getTokens().get(w)))
							{
								String token = sentence.getSentence(i).getAnnotations().get(k).getTokens().get(w);
								if (token.contains("+"))
									token = token.replace("+", "");
								if (token.contains(","))
									token = token.replace(",", ".");
								if (token.contains("."))
									z = Double.parseDouble(token);
								
								else
								{
									z = (double) Integer.parseInt(token);
									
								}
							}
						}
						
						BigDecimal a = new BigDecimal(z);
						BigDecimal standard = a.multiply(conversionStandardw.get(help2));
						
						printOut[i][k] = "satz" + i + " annotation" + k + ":";
						
						for (int d = 0; d < help3.length; d++)
						{
							BigDecimal neu = standard.multiply(conversionUnitw.get(help3[d]));
							
							if (help3[d].equals("kg"))
							{
								for (int l = 0; l < kg.length; l++)
									printOut[i][k] = printOut[i][k].concat(neu + " " + kg[l] + "  //  ");
								countCreatedSurfaceForms++;
							}
							if (help3[d].equals("g"))
							{
								for (int l = 0; l < g.length; l++)
									printOut[i][k] = printOut[i][k].concat(neu + " " + g[l] + "  //  ");
								countCreatedSurfaceForms++;
							}
							if (help3[d].equals("t"))
							{
								for (int l = 0; l < t.length; l++)
									printOut[i][k] = printOut[i][k].concat(neu + " " + t[l] + "  //  ");
								countCreatedSurfaceForms++;
							}
							if (help3[d].equals("Kt"))
							{
								for (int l = 0; l < Kt.length; l++)
									printOut[i][k] = printOut[i][k].concat(neu + " " + Kt[l] + "  //  ");
								countCreatedSurfaceForms++;
							}
							if (help3[d].equals("oz"))
							{
								for (int l = 0; l < oz.length; l++)
									printOut[i][k] = printOut[i][k].concat(neu + " " + oz[l] + "  //  ");
								countCreatedSurfaceForms++;
							}
							if (help3[d].equals("lbs"))
							{
								for (int l = 0; l < lbs.length; l++)
									printOut[i][k] = printOut[i][k].concat(neu + " " + lbs[l] + "  //  ");
								countCreatedSurfaceForms++;
							}
							if (help3[d].equals("mg"))
							{
								for (int l = 0; l < mg.length; l++)
									printOut[i][k] = printOut[i][k].concat(neu + " " + mg[l] + "  //  ");
								countCreatedSurfaceForms++;
							}
							
						}
						
					}
					
				}
				if (sentence.getSentence(i).getAnnotations().get(k).getType().toString() == "LINEAR_MEASURE")
				{
					conversionStandardl = new ConfigLoader().openConfigConversion("Standard", "LINEAR_MEASURE");
					conversionUnitl = new ConfigLoader().openConfigConversion("Unit", "LINEAR_MEASURE");
					
					for (int j = 0; j < sentence.getSentence(i).getAnnotations().get(k).getTokens().size(); j++)
					{
						int help = 0;
						
						for (int l = 0; l < m.length; l++)
						{
							if (m[l].equals(sentence.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
							{
								// System.out.println("satz"+i+g[l]+"und"+s1.getSentence(i).getAnnotations().get(k).getTokens().get(j));
								help5 = "m";
								help++;
							}
						}
						
						if (help == 0)
							for (int l = 0; l < mm.length; l++)
							{
								if (mm[l].equals(sentence.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
								{
									// System.out.println("satz"+i+"kg");
									help++;
									help5 = "mm";
								}
								
							}
						if (help == 0)
							for (int l = 0; l < cm.length; l++)
							{
								if (cm[l].equals(sentence.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
								{
									// System.out.println("satz"+i+"ton");
									help++;
									help5 = "cm";
								}
								
							}
						if (help == 0)
							for (int l = 0; l < dm.length; l++)
							{
								if (dm[l].equals(sentence.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
								{
									// System.out.println("satz"+i+"ton");
									help++;
									help5 = "dm";
								}
								
							}
						if (help == 0)
							for (int l = 0; l < km.length; l++)
							{
								if (km[l].equals(sentence.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
								{
									// System.out.println("satz"+i+"ton");
									help++;
									help5 = "km";
								}
								
							}
						if (help == 0)
							for (int l = 0; l < mile.length; l++)
							{
								if (mile[l].equals(sentence.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
								{
									// System.out.println("satz"+i+"ton");
									help++;
									help5 = "mile";
								}
								
							}
						if (help == 0)
							for (int l = 0; l < yard.length; l++)
							{
								if (yard[l].equals(sentence.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
								{
									// System.out.println("satz"+i+"ton");
									help++;
									help5 = "yard";
								}
								
							}
						if (help == 0)
							for (int l = 0; l < seamile.length; l++)
							{
								if (seamile[l].equals(sentence.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
								{
									// System.out.println("satz"+i+"ton");
									help++;
									help5 = "seamile";
								}
								
							}
						if (help == 0)
							for (int l = 0; l < ft.length; l++)
							{
								if (ft[l].equals(sentence.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
								{
									// System.out.println("satz"+i+"ton");
									help++;
									help5 = "ft";
								}
								
							}
						if (help == 0)
							for (int l = 0; l < inch.length; l++)
							{
								if (inch[l].equals(sentence.getSentence(i).getAnnotations().get(k).getTokens().get(j)))
								{
									// System.out.println("satz"+i+"ton");
									help++;
									help5 = "inch";
								}
								
							}
						
						if (help5 != null)
						{
							
							countProcessibleAnnotations++;
							double z = 0;
							
							for (int w = 0; w < sentence.getSentence(i).getAnnotations().get(k).getTokens().size(); w++)
							{
								if (checkIfNumber(sentence.getSentence(i).getAnnotations().get(k).getTokens().get(w)))
								{
									String token = sentence.getSentence(i).getAnnotations().get(k).getTokens().get(w);
									if (token.contains("+"))
										token = token.replace("+", "");
									if (token.contains(","))
										token = token.replace(",", ".");
									if (token.contains("."))
										z = Double.parseDouble(token);
									
									else
									{
										z = (double) Integer.parseInt(token);
										
									}
								}
							}
							
							if (!help5.equals(null))
							{
								BigDecimal a = new BigDecimal(z);
								
								BigDecimal standard = a.multiply(conversionStandardl.get(help5));
								
								printOut[i][k] = "satz" + i + " annotation" + k + ":";
								
								for (int d = 0; d < help4.length; d++)
								{
									BigDecimal neu = standard.multiply(conversionUnitl.get(help4[d]));
									
									if (help4[d].equals("mm"))
									{
										for (int l = 0; l < mm.length; l++)
											printOut[i][k] = printOut[i][k].concat(neu + " " + mm[l] + "  //  ");
										countCreatedSurfaceForms++;
									}
									if (help4[d].equals("cm"))
									{
										for (int l = 0; l < cm.length; l++)
											printOut[i][k] = printOut[i][k].concat(neu + " " + cm[l] + "  //  ");
										countCreatedSurfaceForms++;
									}
									if (help4[d].equals("dm"))
									{
										for (int l = 0; l < dm.length; l++)
											printOut[i][k] = printOut[i][k].concat(neu + " " + dm[l] + "  //  ");
										countCreatedSurfaceForms++;
									}
									if (help4[d].equals("m"))
									{
										for (int l = 0; l < m.length; l++)
											printOut[i][k] = printOut[i][k].concat(neu + " " + m[l] + "  //  ");
										countCreatedSurfaceForms++;
									}
									if (help4[d].equals("km"))
									{
										for (int l = 0; l < km.length; l++)
											printOut[i][k] = printOut[i][k].concat(neu + " " + km[l] + "  //  ");
										countCreatedSurfaceForms++;
									}
									if (help4[d].equals("mile"))
									{
										for (int l = 0; l < mile.length; l++)
											printOut[i][k] = printOut[i][k].concat(neu + " " + mile[l] + "  //  ");
										countCreatedSurfaceForms++;
									}
									if (help4[d].equals("seamile"))
									{
										for (int l = 0; l < seamile.length; l++)
											printOut[i][k] = printOut[i][k].concat(neu + " " + seamile[l] + "  //  ");
										countCreatedSurfaceForms++;
									}
									if (help4[d].equals("yard"))
									{
										for (int l = 0; l < yard.length; l++)
											printOut[i][k] = printOut[i][k].concat(neu + " " + yard[l] + "  //  ");
										countCreatedSurfaceForms++;
									}
									if (help4[d].equals("ft"))
									{
										for (int l = 0; l < ft.length; l++)
											printOut[i][k] = printOut[i][k].concat(neu + " " + ft[l] + "  //  ");
										countCreatedSurfaceForms++;
									}
									if (help4[d].equals("inch"))
									{
										for (int l = 0; l < inch.length; l++)
											printOut[i][k] = printOut[i][k].concat(neu + " " + inch[l] + "  //  ");
										countCreatedSurfaceForms++;
									}
									
								}
								
							}
							
						}
					}
					
				}
				
				System.out.println(printOut[i][k]);
			}
			
		}
		
		System.out.println(countCreatedSurfaceForms + "oberflaechenformen erzeugt");
		System.out.println("konnte " + countProcessibleAnnotations + " von " + countTotalAnnotations + "annotationen bearbeiten");
	}
}
