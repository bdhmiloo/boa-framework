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

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.VerticalLayout;
// DON'T DELETE THE FOLLOWING IMPORTS --> WILL BE DELETED AFTER FINISHING APPLICATION COMPLETELY
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
	
	private TextArea textAreaSentence;
	
	private ListSelect listSelectAnnotation;
	
	private Panel panelAnnotation;
	
	private Button buttonFileUpload;
	private Button buttonNew;
	private Button buttonAnnotate;
	private Button buttonNext; // button 'next' in tab 'Annotation'
	private Button buttonNext2; // button 'next' in tab 'Evaluation'
	private Button buttonEnd;
	
	/**
	 * Constructor initializes View.
	 */
	public EvaluationView()
	{
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
		
		// reset to default status
		this.resetComponents();
		
		this.addComponent(tabsheet);
	}
	
	/**
	 * Build content 'welcome page' of tab 1.
	 * 
	 * @return content of first tab
	 */
	private Layout buildTab1Content()
	{
		VerticalLayout tab1Content = new VerticalLayout();
		tab1Content.setSpacing(true);
		tab1Content.setMargin(true);
		tab1Content.setSizeFull();
		
		Label welcomeIntro = new Label("<h1>Welcome to our test-GUI</h1>"
				+ "<p><i>there should be a description here...as soon as possible! :)</i></p>"
				+ "<p>Ð <i>swp12-1</i> Ð</p>", Label.CONTENT_XHTML);
		
		tab1Content.addComponent(welcomeIntro);
		
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
		
		HorizontalLayout hlay1 = new HorizontalLayout();
		this.buttonFileUpload = new Button("Select File");
		buttonFileUpload.setImmediate(true);
		buttonFileUpload.setDescription("CLick <b>Select File</b> in order to upload a plain-text file to server");
		this.buttonNew = new Button("New");
		buttonNew.setImmediate(true);
		buttonNew.setDescription("Click <b>New</b> in order to type in a new sentence");
		hlay1.setSpacing(true);
		hlay1.setMargin(false);
		hlay1.addComponent(buttonFileUpload);
		hlay1.addComponent(buttonNew);
		tab2Content.addComponent(hlay1);
		
		this.textAreaSentence = new TextArea("Sentence:");
		textAreaSentence.setImmediate(true);
		textAreaSentence.setRows(2);
		textAreaSentence.setWidth("100%");
		tab2Content.addComponent(textAreaSentence);
		
		this.buttonAnnotate = new Button("Annotate");
		buttonAnnotate.setImmediate(true);
		tab2Content.addComponent(buttonAnnotate);
		
		this.listSelectAnnotation = new ListSelect("Annotations:");
		listSelectAnnotation.setImmediate(false);
		listSelectAnnotation.setHeight("70px");
		listSelectAnnotation.setWidth("100%");
		tab2Content.addComponent(listSelectAnnotation);
		
		this.panelAnnotation = new Panel("Further annotations with other surfaceforms:");
		panelAnnotation.setImmediate(false);
		panelAnnotation.setHeight("100px");
		panelAnnotation.setWidth("100%");
		tab2Content.addComponent(panelAnnotation);
		
		HorizontalLayout hlay2 = new HorizontalLayout();
		this.buttonNext = new Button("Next");
		buttonNext.setImmediate(true);
		this.buttonEnd = new Button("End");
		buttonEnd.setImmediate(true);
		hlay2.setSpacing(true);
		hlay2.setMargin(false);
		hlay2.addComponent(buttonNext);
		hlay2.addComponent(buttonEnd);
		tab2Content.addComponent(hlay2);
		
		return tab2Content;
	}
	
	public Button getButtonFileUpload()
	{
		return this.buttonFileUpload;
	}
	
	public Button getButtonNew()
	{
		return this.buttonNew;
	}
	
	public Button getButtonNext()
	{
		return this.buttonNext;
	}
	
	public Button getButtonAnnotate()
	{
		return this.buttonAnnotate;
	}
	
	public Panel getPanelAnnotation()
	{
		return this.panelAnnotation;
	}
	
	public ListSelect getListSelectAnnotation()
	{
		return this.listSelectAnnotation;
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
		buttonNext2.setImmediate(true);
		tab3Content.addComponent(buttonNext2);
		
		return tab3Content;
	}
	
	public Button getButtonNext2()
	{
		return this.buttonNext2;
	}
	
	/**
	 * TabChangeListener for testing GUI.
	 * 
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
		textAreaSentence.setInputPrompt("Please insert or load a sentence from a plain-text file.");
		buttonAnnotate.setEnabled(false);
		
	}
	
	/**
	 * 
	 */
	public void close()
	{
		
	}
	
}
