/*
 * EvaluationApp.java
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

package de.uni_leipzig.informatik.pcai042.boa.gui.evaluation;

import com.vaadin.Application;
import com.vaadin.ui.*;

/**
 * 
 * @author Simon Suiter
 */
@SuppressWarnings("serial")
public class EvaluationApp extends Application
{
	private EvaluationView view = new EvaluationView();
	
	/**
	 * 
	 */
	@Override
	public void init()
	{
		Window mainWindow = new Window("Boa");
		setMainWindow(mainWindow);
		mainWindow.getContent().setSizeFull();
		mainWindow.addComponent(view);
	}
}
