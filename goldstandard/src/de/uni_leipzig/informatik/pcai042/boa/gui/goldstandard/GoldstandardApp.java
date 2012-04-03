package de.uni_leipzig.informatik.pcai042.boa.gui.goldstandard;

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
	
	@Override
	public void close()
	{
		gui.close();
	}
}
