/*
 * TestAnnotater.java
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import nu.xom.ParsingException;
import nu.xom.ValidityException;
import de.uni_leipzig.informatik.pcai042.boa.manager.Annotater;
import de.uni_leipzig.informatik.pcai042.boa.manager.BoaSentence;
import de.uni_leipzig.informatik.pcai042.boa.manager.SentenceLoader;

public class TestAnnotater
{
	public static void main(String[] args)
	{
		ArrayList<BoaSentence> sentences = new ArrayList<BoaSentence>();
		SentenceLoader sentenceLoader = null;
		
		try
		{
			sentenceLoader = new SentenceLoader(new File("goldstandard.xml"));
		} catch (ValidityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParsingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sentences = sentenceLoader.getSentences();
		
		Annotater testAnnotater = new Annotater();
		testAnnotater.annotate(sentences);
		
		Iterator<BoaSentence> it = sentences.iterator();
		while(it.hasNext())
		{
			System.out.println(it.next().getAnnotations().toString());
		}
	}
}
