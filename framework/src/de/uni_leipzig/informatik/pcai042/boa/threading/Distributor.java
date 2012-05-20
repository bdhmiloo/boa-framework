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

public abstract class Distributor
{
	protected List<AnnotatorThread> threads;
	protected ConfigLoader configLoder;
	
	public abstract String getText();
	public abstract List<BoaSentence> getSentences();
	public abstract void returnSentences(List<BoaSentence> sentences);
	public abstract boolean hasFinished();
	public abstract void run();
	
	protected Distributor(ConfigLoader configLoader)
	{
		this.configLoder = configLoader;
	}
}
