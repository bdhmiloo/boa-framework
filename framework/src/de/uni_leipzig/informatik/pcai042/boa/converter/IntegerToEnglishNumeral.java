/*
 * IntegerToEnglishNumeral.java
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

/**
 * 
 * @author Duc Huy Bui
 */
public class IntegerToEnglishNumeral
{
	private String[] lessThan20 = { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
			"eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen" };
	private String[] tens = { "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety" };
	private String[] denom = { "", "thousand", "million" };
	
	/**
	 * 
	 * @param number
	 */
	public IntegerToEnglishNumeral()
	{
		
	}
	
	/**
	 * Convert a value less than 100 to English numeral.
	 * 
	 * @param value
	 * @return
	 */
	private String convertXX(int value)
	{
		if (value < 20)
		{
			return lessThan20[value];
		}
		
		for (int i = 0; i < tens.length; i++)
		{
			String dcap = tens[i];
			
			int dval = 20 + 10 * i;
			
			if (dval + 10 > value)
			{
				if ((value % 10) != 0)
				{
					return dcap + "-" + lessThan20[value % 10];
				}
				
				return dcap;
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	private String convertXXX(int value)
	{
		String word = "";
		
		int rem = value / 100;
		int mod = value % 100;
		
		if (rem > 0)
		{
			word = lessThan20[rem] + "hundred";
			
			if (mod > 0)
			{
				word = word + "-";
			}
		}
		
		if (mod > 0)
		{
			word = word + convertXX(mod);
		}
		
		return word;
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public String makeEnglishNumeral(int value)
	{
		if (value < 100)
		{
			return convertXX(value);
		}
		
		if (value < 1000)
		{
			return convertXXX(value);
		}
		
		for (int i = 0; i < denom.length; i++)
		{
			int didx = i - 1;
			int dval = new Double(Math.pow(1000, i)).intValue();
			
			if (dval > value)
			{
				int mod = new Double(Math.pow(1000, didx)).intValue();
				int l = value / mod;
				int r = value - (l * mod);
				
				String ret = convertXXX(l) + "-" + denom[didx];
				
				if (r > 0)
				{
					ret = ret + "-" + makeEnglishNumeral(r);
				}
				
				return ret;
			}
		}
		return null;
		
	}
	
	/**
	 * 
	 * @param argv
	 * @throws Exception
	 */
	public static void main(String[] argv) throws Exception
	{
		int test = 1968;
		
		IntegerToEnglishNumeral a = new IntegerToEnglishNumeral();
		
		System.out.println(a.makeEnglishNumeral(test));
		
	}
	
}
