package de.uni_leipzig.informatik.pcai042.boa.gui.goldstandard;

import com.vaadin.Application;
import com.vaadin.ui.*;

@SuppressWarnings("serial")
public class GoldstandardApp extends Application
{
	@Override
	public void init()
	{
		Window mainWindow = new Window("Goldstandard");
		setMainWindow(mainWindow);
		mainWindow.getContent().setSizeFull();
		
        GoldstandardGUI gui = new GoldstandardGUI();
        mainWindow.addComponent(gui);
	}

}
