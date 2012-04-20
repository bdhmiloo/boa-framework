/*
 * EvaluationView.java
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

import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;

/**
 * 
 * @author Duc Huy Bui
 */
@SuppressWarnings("serial")
public class EvaluationView extends VerticalLayout implements TabSheet.SelectedTabChangeListener
{
	// private static final String CAPTION = "Open Google in new window";
	// private static final String TOOLTIP =
	// "http://pcai042.informatik.uni-leipzig.de/~swp12-1/";
	// private static final ThemeResource ICON = new
	// ThemeResource("../sampler/icons/icon_world.gif");
	
	private TabSheet t;
	private VerticalLayout mainlayout;
	
	/**
	 * Constructor
	 */
	public EvaluationView()
	{
		buildMainLayout();
		
	}
	
	/**
	 * 
	 */
	private VerticalLayout buildMainLayout()
	{
		
		// Tab 1 content
		VerticalLayout lay1 = new VerticalLayout();
		lay1.setMargin(true);
		lay1.addComponent(new Label("There are no previously saved actions."));
		// Tab 2 content
		VerticalLayout lay2 = new VerticalLayout();
		lay2.setMargin(true);
		lay2.addComponent(new Label("There are no saved notes."));
		// Tab 3 content
		VerticalLayout lay3 = new VerticalLayout();
		lay3.setMargin(true);
		lay3.addComponent(new Label("There are currently no issues."));
		
		//
		t = new TabSheet();
		t.setHeight("800px");
		t.setWidth("1024px");
		
		t.addTab(lay1, "Home");
		t.addTab(lay2, "Annotation");
		t.addTab(lay3, "Evaluation");
		t.addListener(this);
		
		addComponent(t);
		
		return mainlayout;
	}
	
	/**
	 * @TODO should be moved to class EvaluationApp
	 * 
	 * @param event
	 */
	public void selectedTabChange(SelectedTabChangeEvent event)
	{
		TabSheet tabsheet = event.getTabSheet();
		Tab tab = tabsheet.getTab(tabsheet.getSelectedTab());
		
		if (tab != null)
		{
			getWindow().showNotification("Selected tab: " + tab.getCaption());
		}
	}
	
	/**
	 * Set all components to default status.
	 */
	public void resetComponents()
	{
		
	}
	
}
