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

import com.vaadin.ui.Label;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;

import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.Tab;
// DON'T DELETE THE FOLLOWING IMPORTS --> WILL BE DELETED AFTER FINISHING APPLICATION COMPLETELY
//import com.vaadin.ui.Window;
//import com.vaadin.ui.Link;
//import com.vaadin.ui.Upload;
//import com.vaadin.ui.Upload.FinishedEvent;
//import com.vaadin.ui.Upload.Receiver;
//import com.vaadin.terminal.ExternalResource;
//import com.vaadin.terminal.Sizeable;
//import com.vaadin.terminal.ThemeResource;

/**
 * 
 * 
 * @author Duc Huy Bui
 */
@SuppressWarnings("serial")
public class EvaluationView extends VerticalLayout implements TabSheet.SelectedTabChangeListener
{
	// private static final String CAPTION = "Open Homepage";
	// private static final String TOOLTIP =
	// "http://pcai042.informatik.uni-leipzig.de/~swp12-1/";
	// private static final ThemeResource ICON = new
	// ThemeResource("../sampler/icons/icon_world.gif");
	
	private TabSheet tabsheet;
	
	private VerticalLayout tab1;
	private VerticalLayout tab2;
	private VerticalLayout tab3;
	
	private TextField textFieldSentence;
	
	private Button buttonFileUpload;
	private Button buttonNew;
	private Button buttonAnnotate;
	private Button buttonNext;
	private Button buttonNext2;
	private Button buttonEnd;
	
	// private Button buttonHelp;
	
	/**
	 * Constructor initializes View.
	 */
	public EvaluationView()
	{
		// this.setModal(true);
		this.setWidth("800px");
		this.setHeight("1024px");
		// this.setResizable(false);
		this.setSizeFull();
		
		// buildMainLayout

		// Tab 1 content
		tab1 = new VerticalLayout();
		tab1.setMargin(true);
		tab1.addComponent(buildTab1Content());
		
		// Tab 2 content
		tab2 = new VerticalLayout();
		tab2.setMargin(true);
		tab2.addComponent(buildTab2Content());
		
		// Tab 3 content
		tab3 = new VerticalLayout();
		tab3.setMargin(true);
		tab3.addComponent(buildTab3Content());
		
		tabsheet = new TabSheet();
		// tabsheet.setSizeFull();
		tabsheet.addTab(tab1, "Home");
		tabsheet.addTab(tab2, "Annotation");
		tabsheet.addTab(tab3, "Evaluation");
		tabsheet.addListener(this);

		this.addComponent(tabsheet);
	}
	
	/**
	 * Build content 'welcome page' of tab 1.
	 * 
	 * @return content of first tab
	 */
	private Label buildTab1Content()
	{
		Label tab1Content = new Label("<h1>Welcome to our test-GUI</h1>"
				+ "<p><i>there should be a description here...as soon as possible! :)</i></p>"
				+ "<p>Ð <i>swp12-1</i> Ð</p>", Label.CONTENT_XHTML);
		
		return tab1Content;
	}
	
	/**
	 * Build content 'annotation process' of tab 2.
	 * 
	 * @return content of second tab
	 */
	private Layout buildTab2Content()
	{
		VerticalLayout tab2Content = new VerticalLayout();
		tab2Content.setSpacing(true);
		tab2Content.setMargin(true);
		tab2Content.setSizeFull();
		
		Label labelSentence = new Label("Sentence:");
		tab2Content.addComponent(labelSentence);
		
		HorizontalLayout hlay1 = new HorizontalLayout();
		this.buttonFileUpload = new Button("Select File");
		this.buttonNew = new Button("New");
		hlay1.addComponent(buttonFileUpload);
		hlay1.addComponent(buttonNew);
		tab2Content.addComponent(hlay1);
		
		this.textFieldSentence = new TextField();
		textFieldSentence.setValue("Please insert a sentence.");
		tab2Content.addComponent(textFieldSentence);
		
		this.buttonAnnotate = new Button("Annotate");
		tab2Content.addComponent(buttonAnnotate);
		
		Label labelPrintOut = new Label("Print out:");
		tab2Content.addComponent(labelPrintOut);
		
		// listSelect needed here
		
		// table neede here
		
		// button next, end needed here
		
		return tab2Content;
	}
	
	/**
	 * Build content 'evaluation process' of tab 3.
	 * 
	 * @return content of third tab
	 */
	private Layout buildTab3Content()
	{
		VerticalLayout tab3Content = new VerticalLayout();
		tab3Content.setSpacing(true);
		tab3Content.setMargin(true);
		tab3Content.setSizeFull();
		
		Label labelConversion = new Label("Conversion:");
		tab3Content.addComponent(labelConversion);
		
		// table needed here
		
		this.buttonNext2 = new Button("Next");
		tab3Content.addComponent(buttonNext2);
		
		return tab3Content;
	}
	
	/**
	 * @TODO should be moved to class EvaluationApp
	 * 
	 *       TabChangeListener for testing GUI.
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
		textFieldSentence.setValue("Please insert a sentence.");
		// t.getTab(tab3).setEnabled(false);
	}
	
	/**
	 * 
	 */
	public void close()
	{
		
	}
	
}
