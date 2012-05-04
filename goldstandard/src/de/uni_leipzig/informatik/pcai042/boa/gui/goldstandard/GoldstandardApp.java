/*
 * GoldstandardApp.java
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

package de.uni_leipzig.informatik.pcai042.boa.gui.goldstandard;

/**
 * Instantiated by the Servlet Container when a new client invokes the corresponding page.
 * Creates a GoldstandardGUI.
 */
import com.vaadin.Application;
import com.vaadin.ui.*;

@SuppressWarnings("serial")
public class GoldstandardApp extends Application
{
	private GoldstandardGUI gui = new GoldstandardGUI();
	
	@Override
	public void init()
	{
		if (!SentenceServer.isInitialized())
			SentenceServer.init(getContext().getBaseDirectory());
		Window mainWindow = new Window("Goldstandard");
		setMainWindow(mainWindow);
		mainWindow.getContent().setSizeFull();
		mainWindow.addComponent(gui);
	}
	
	/**
	 * Ensures that the current sentence is discarded on a session timeout.
	 */
	@Override
	public void close()
	{
		gui.close();
	}
}
