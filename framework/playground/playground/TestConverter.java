/*
 * TestConverter.java
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import de.uni_leipzig.informatik.pcai042.boa.converter.ConverterWeight;
import de.uni_leipzig.informatik.pcai042.boa.manager.SentenceLoader;

public class TestConverter
{
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		File file = new File("testConverterWeight.txt");
		SentenceLoader sentence = null;
		ConverterWeight cw = new ConverterWeight("WEIGHT", "goldstandard.xml");
		
		try
		{
			System.setOut(new PrintStream(new FileOutputStream(file, true)));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
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
		
		
		
	}
	
}
