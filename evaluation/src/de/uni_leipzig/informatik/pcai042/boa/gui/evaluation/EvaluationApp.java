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
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;

/**
 * 
 * @author Simon Suiter
 */
@SuppressWarnings("serial")
public class EvaluationApp extends Application implements ItemClickListener, ClickListener, SelectedTabChangeListener
{
	private EvaluationView view = new EvaluationView();
	
	@Override
	public void init()
	{
		Window mainWindow = new Window("Boa");
		setMainWindow(mainWindow);
		mainWindow.getContent().setSizeFull();
		mainWindow.addComponent(view);
		
		view.getButtonAnnotate().addListener(this);
		view.getButtonFileUpload().addListener(this);
		view.getButtonNew().addListener(this);
		view.getButtonNext().addListener(this);
		view.getButtonNext2().addListener(this);
		view.getTabSheet().addListener(this);
		view.getTableEvaluation().addListener(this);
	}
	
	/**
	 * Listener for clicking buttons.
	 */
	public void buttonClick(ClickEvent event)
	{
		Button button = event.getButton();
		
		if (button == view.getButtonAnnotate())
		{
			
		} else if (button == view.getButtonFileUpload())
		{
			
		} else if (button == view.getButtonNew())
		{
			
		} else if (button == view.getButtonNext())
		{
			
		} else if (button == view.getButtonNext2())
		{
			
		}
	}
	
	/**
	 * Listener for changing tabs on tabsheet.
	 */
	public void selectedTabChange(SelectedTabChangeEvent event)
	{
		TabSheet tabsheet = event.getTabSheet();
		tabsheet.getTab(tabsheet.getSelectedTab());
	}
	
	/**
	 * 
	 */
	public void itemClick(ItemClickEvent event)
	{
		// https://vaadin.com/book/-/page/components.table.html
		
	}
}
