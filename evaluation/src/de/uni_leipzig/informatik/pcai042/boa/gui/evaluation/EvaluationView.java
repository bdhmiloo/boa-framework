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
import com.vaadin.ui.RichTextArea;
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
	
	private Label labelIntroduction;
	
	/**
	 * Constructor
	 */
	public EvaluationView()
	{
		setSpacing(true);
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
		labelIntroduction = buildContentTab1();
		lay1.addComponent(labelIntroduction);
		// Tab 2 content
		VerticalLayout lay2 = new VerticalLayout();
		lay2.setMargin(true);
		lay2.addComponent(new Label("There should be some textfields and buttons."));
		// Tab 3 content
		VerticalLayout lay3 = new VerticalLayout();
		lay3.setMargin(true);
		lay3.addComponent(new Label("evaluation, precision, recall."));
		
		// add all tabs to tabsheet
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
	 * 
	 * @return
	 */
	private Label buildContentTab1()
	{
		labelIntroduction = new Label("<h1>Welcome to our test-GUI</h1>"
				+ "<p><i>there should be a description here...as soon as possible! :)</i></p>"
				+ "<p>Ð <i>swp12-1</i> Ð</p>");
		
		// adjust content to webpage
		labelIntroduction.setContentMode(Label.CONTENT_XHTML);
		
		return labelIntroduction;
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
