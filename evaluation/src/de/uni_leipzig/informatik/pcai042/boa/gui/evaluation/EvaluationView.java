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
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;

/**
 * 
 * @author Duc Huy Bui, Simon Suiter
 */
@SuppressWarnings("serial")
public class EvaluationView extends VerticalLayout
{
	private TabSheet tabsheet;
	
	private Table tableEvaluation;
	
//	private VerticalLayout tab1;
	private VerticalLayout tab2;
	private VerticalLayout tab3;
	
	private TextArea textAreaSentence;
	private TextArea textAreaEvalSentence;
	private TextArea textAreaAnnotation;
	
	private ListSelect listSelectAnnotation;
	private ListSelect listSelectGoldstandard;
	private ListSelect listSelectFramework;
	
	private Button buttonNew;
	private Button buttonAnnotate;
	//private Button buttonNext; // button 'next' in tab 'Annotation'
	private Button buttonNext2; // button 'next' in tab 'Evaluation'
	
	/**
	 * Constructor initializes View.
	 */
	public EvaluationView()
	{
		this.setSizeFull();
		
		// buildMainLayout
		
		// Tab 1 content
//		tab1 = new VerticalLayout();
//		tab1.setMargin(true);
//		tab1.addComponent(buildTab1Content());
		
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
//		tabsheet.addTab(tab1, "Home");
		tabsheet.addTab(tab2, "Annotation");
		tabsheet.addTab(tab3, "Evaluation");
		
		// reset to default status
		this.resetComponents();
		
		this.addComponent(tabsheet);
	}
	
//	/**
//	 * Build content 'welcome page' of tab 1.
//	 * 
//	 * @return content of first tab
//	 */
//	private Layout buildTab1Content()
//	{
//		VerticalLayout tab1Content = new VerticalLayout();
//		tab1Content.setSpacing(true);
//		tab1Content.setMargin(true);
//		tab1Content.setSizeFull();
//		
//		Label welcomeIntro = new Label("<h1>Welcome to our test-GUI</h1>"
//				+ "<p><i>There was not enough time to do this! :)</i></p>"
//				+ "<p>- <i>swp12-1</i> -</p>", Label.CONTENT_XHTML);
//		
//		tab1Content.addComponent(welcomeIntro);
//		
//		return tab1Content;
//	}
	
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
		
		this.textAreaSentence = new TextArea("Sentence:");
		textAreaSentence.setImmediate(true);
		textAreaSentence.setRows(7);
		textAreaSentence.setWidth("100%");
		tab2Content.addComponent(textAreaSentence);
		
		HorizontalLayout hlay1 = new HorizontalLayout();
		this.buttonNew = new Button("New");
		buttonNew.setImmediate(true);
		buttonNew.setDescription("Type in new sentences");
		this.buttonAnnotate = new Button("Annotate");
		buttonAnnotate.setImmediate(true);
		buttonAnnotate.setDescription("Annotate the sentences above");
		hlay1.setSpacing(true);
		hlay1.setMargin(false);
		hlay1.addComponent(buttonNew);
		hlay1.addComponent(buttonAnnotate);
		tab2Content.addComponent(hlay1);
		
		this.listSelectAnnotation = new ListSelect("Annotations:");
		listSelectAnnotation.setImmediate(true);
		listSelectAnnotation.setHeight("150px");
		listSelectAnnotation.setWidth("100%");
		listSelectAnnotation.setNullSelectionAllowed(false);
		tab2Content.addComponent(listSelectAnnotation);
		
		this.textAreaAnnotation = new TextArea("Further annotations with other surface forms:");
		textAreaAnnotation.setImmediate(false);
		textAreaAnnotation.setRows(4);
		textAreaAnnotation.setReadOnly(true);
		textAreaAnnotation.setWidth("100%");
		tab2Content.addComponent(textAreaAnnotation);
		
		//this.buttonNext = new Button("Next");
		//buttonNext.setImmediate(true);
		//buttonNext.setDescription("Get next annotation");
		//tab2Content.addComponent(buttonNext);
		
		return tab2Content;
	}
	
	/**
	 * Add an item to listselect Annotation.
	 * 
	 * @param annotation
	 */
	public void addItemToListSelectAnnotation(BoaAnnotation annotation)
	{
		listSelectAnnotation.addItem(annotation);
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
		
		Label instructions = new Label(
				"<b>Instructions:</b> <i>Please select and click a sentence below in order to process the evaluation.</i>", Label.CONTENT_XHTML);
		tab3Content.addComponent(instructions);
		
		this.tableEvaluation = new Table("Evaluation process:");
		tableEvaluation.setHeight("150px");
		tableEvaluation.setWidth("100%");
		tableEvaluation.setImmediate(true);
		tableEvaluation.setSelectable(true);
		tableEvaluation.setMultiSelect(false);
		tableEvaluation.setSortDisabled(false);
		tableEvaluation.addContainerProperty("ID", Integer.class, null);
		tableEvaluation.addContainerProperty("Sentence", String.class, null);
		tableEvaluation.addContainerProperty("Precision", Double.class, null);
		tableEvaluation.addContainerProperty("Recall", Double.class, null);
		tableEvaluation.addContainerProperty("F-Score", Double.class, null);
		tab3Content.addComponent(tableEvaluation);
		
		this.buttonNext2 = new Button("Next");
		buttonNext2.setImmediate(true);
		buttonNext2.setDescription("Get the next sentence in table");
		tab3Content.addComponent(buttonNext2);
		
		this.textAreaEvalSentence = new TextArea("Sentence:");
		textAreaEvalSentence.setImmediate(false);
		textAreaEvalSentence.setReadOnly(true);
		textAreaEvalSentence.setRows(3);
		textAreaEvalSentence.setWidth("100%");
		tab3Content.addComponent(textAreaEvalSentence);
		
		HorizontalLayout hlay1 = new HorizontalLayout();
		this.listSelectGoldstandard = new ListSelect("Goldstandard:");
		listSelectGoldstandard.setImmediate(true);
		listSelectGoldstandard.setHeight("120px");
		listSelectGoldstandard.setWidth("100%");
		listSelectGoldstandard.setNullSelectionAllowed(false);
		this.listSelectFramework = new ListSelect("Framework:");
		listSelectFramework.setImmediate(true);
		listSelectFramework.setHeight("120px");
		listSelectFramework.setWidth("100%");
		listSelectFramework.setNullSelectionAllowed(false);
		hlay1.setSpacing(true);
		hlay1.setMargin(false);
		hlay1.setWidth("100%");
		hlay1.addComponent(listSelectGoldstandard);
		hlay1.addComponent(listSelectFramework);
		tab3Content.addComponent(hlay1);
		
		return tab3Content;
	}
	
	/**
	 * Add an item to table Evaluation.
	 * 
	 * @param id
	 * @param sentence
	 * @param precision
	 * @param recall
	 * @param fScore
	 * @param line
	 */
	public void addItemToTableEvaluation(Integer id, String sentence, Double precision, Double recall, Double fScore,
			Integer line)
	{
		tableEvaluation.addItem(new Object[] { new Integer(id), sentence, new Double(precision), new Double(recall),
				new Double(fScore) }, new Integer(line));
	}
	
	/**
	 * Add an item to listselect Goldstandard.
	 * 
	 * @param annotation
	 */
	public void addItemToListSelectGoldstandard(BoaAnnotation annotation)
	{
		listSelectGoldstandard.addItem(annotation);
	}
	
	/**
	 * Add an item to listselect Framework.
	 * 
	 * @param annotation
	 */
	public void addItemToListSelectFramework(BoaAnnotation annotation)
	{
		listSelectGoldstandard.addItem(annotation);
	}
	
	public Button getButtonNew()
	{
		return this.buttonNew;
	}
	
	/*public Button getButtonNext()
	{
		return this.buttonNext;
	}*/
	
	public Button getButtonAnnotate()
	{
		return this.buttonAnnotate;
	}
	
	public ListSelect getListSelectAnnotation()
	{
		return this.listSelectAnnotation;
	}
	
	public Button getButtonNext2()
	{
		return this.buttonNext2;
	}
	
	public Table getTableEvaluation()
	{
		return this.tableEvaluation;
	}
	
	public ListSelect getListSelectGoldstandard()
	{
		return listSelectGoldstandard;
	}
	
	public ListSelect getListSelectFramework()
	{
		return listSelectFramework;
	}
	
	public TextArea getTextArea()
	{
		return textAreaSentence;
	}
	
	public TextArea getTextAreaAnnotation()
	{
		return textAreaAnnotation;
	}
	
	public TextArea getTextAreaEvalSentence()
	{
		return textAreaEvalSentence;
	}
	
	/**
	 * Set all components to default status.
	 */
	public void resetComponents()
	{
		textAreaSentence.setValue("");
		textAreaSentence.setInputPrompt("Please insert a sentence here.");
		// buttonAnnotate.setEnabled(false);
		listSelectAnnotation.removeAllItems();
	}
}
