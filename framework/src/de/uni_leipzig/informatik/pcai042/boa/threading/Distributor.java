/*
 * Distributor.java
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

package de.uni_leipzig.informatik.pcai042.boa.threading;

import java.util.List;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaSentence;
import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;

/**
 * An abstract class that provides methods for AnnotatorThread to retrieve data and return the result. 
 * 
 * @see AnnotatorThread
 * @author Simon Suiter
 */
public abstract class Distributor
{
	protected List<AnnotatorThread> threads;
	protected ConfigLoader configLoder;
	
	/**
	 * Called by an AnnotatorThread to retrieve an untokenized piece of text to
	 * annotate.
	 */
	public abstract String getText();
	
	/**
	 * Called by an AnnotatorThread to retrieve a list of (tokenized) sentences
	 * to annotate.
	 */
	public abstract List<BoaSentence> getSentences();
	
	/**
	 * Called by an AnnotatorThread to return a list of annotated sentences.
	 * 
	 * @param sentences
	 *            the result of the annotations made by the thread
	 */
	public abstract void returnSentences(List<BoaSentence> sentences);
	
	/**
	 * Returns true when there is no data left to annotate.
	 */
	public abstract boolean hasFinished();
	
	/**
	 * Starts the annotation process.
	 */
	public abstract void run();
	
	protected Distributor(ConfigLoader configLoader)
	{
		this.configLoder = configLoader;
	}
}
