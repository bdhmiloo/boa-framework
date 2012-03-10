package de.uni_leipzig.informatik.pcai042.boa.gui.goldstandard;

import com.vaadin.Application;
import com.vaadin.ui.*;

public class GoldstandardApp extends Application
{
	private static final long serialVersionUID = 4933567410456231975L;

	@Override
	public void init()
	{
		Window mainWindow = new Window("Goldstandard");
		Label label = new Label("Hello Vaadin user");
		mainWindow.addComponent(label);
		setMainWindow(mainWindow);
	}

}
