/*
 * GoldstandardGUI.java
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

import java.util.ArrayList;
import java.util.Iterator;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

import de.uni_leipzig.informatik.pcai042.boa.gui.goldstandard.BoaAnnotation.Type;

@SuppressWarnings("serial")
public class GoldstandardGUI extends CustomComponent
{
	
	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Label labelAnnotations;
	@AutoGenerated
	private Label labelSentence;
	@AutoGenerated
	private ListSelect listSelectAnnotations;
	@AutoGenerated
	private Panel panelTokens;
	@AutoGenerated
	private VerticalLayout verticalLayoutTokens;
	@AutoGenerated
	private Label labelType;
	@AutoGenerated
	private Label labelLabel;
	@AutoGenerated
	private TextField textFieldLabel;
	@AutoGenerated
	private ComboBox comboBoxTypes;
	@AutoGenerated
	private Button buttonDelAnno;
	@AutoGenerated
	private Button buttonAddAnno;
	@AutoGenerated
	private Button buttonDiscard;
	@AutoGenerated
	private Button buttonNext;
	
	private CssLayout cssLayoutTokens;
	private BoaSentence sentence;
	
	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public GoldstandardGUI()
	{
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		// user code
		
		for (Type t : BoaAnnotation.Type.values())
		{
			comboBoxTypes.addItem(t);
		}
		comboBoxTypes.setTextInputAllowed(false);
		listSelectAnnotations.setMultiSelect(false);
		comboBoxTypes.setNullSelectionAllowed(false);
		listSelectAnnotations.setNullSelectionAllowed(false);
		
		// with this layout components will get wrapped
		cssLayoutTokens = new CssLayout()
		{
			@Override
			protected String getCss(Component c)
			{
				return "float:left;margin-right:5px;";
			}
		};
		cssLayoutTokens.setWidth("100%");
		// auto; becomes vertical scroll-able since verticalLayout_tokens height
		// is also auto
		cssLayoutTokens.setHeight(SIZE_UNDEFINED, 0);
		verticalLayoutTokens.addComponent(cssLayoutTokens);
		
		resetComponents();
		
		buttonNext.addListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				if (sentence != null)
				{
					SentenceServer.returnSentence(sentence);
				}
				
				resetComponents();
				sentence = SentenceServer.getSentence();
				
				if (sentence == null)
				{
					getWindow().showNotification("No more senentences.", Notification.TYPE_ERROR_MESSAGE);
					return;
				}
				
				// create new check boxes for tokens
				CheckBox checkbox;
				Label label;
				VerticalLayout vertLayout;
				for (int i = 0; i < sentence.getTokens().size(); i++)
				{
					checkbox = new CheckBox();
					checkbox.setImmediate(true);
					checkbox.addListener(new Property.ValueChangeListener()
					{
						public void valueChange(ValueChangeEvent event)
						{
							Iterator<Component> iterator = cssLayoutTokens.getComponentIterator();
							Label l;
							CheckBox cb;
							VerticalLayout vl;
							String s = "";
							while (iterator.hasNext())
							{
								vl = (VerticalLayout) iterator.next();
								l = (Label) vl.getComponent(0);
								cb = (CheckBox) vl.getComponent(1);
								if (cb.booleanValue())
								{
									s += l.getValue();
								}
							}
							if (s.isEmpty())
								s = "";
							textFieldLabel.setReadOnly(false);
							textFieldLabel.setValue(s);
							textFieldLabel.setReadOnly(true);
						}
					});
					label = new Label(sentence.getTokens().get(i));
					label.setSizeUndefined();
					vertLayout = new VerticalLayout();
					vertLayout.addComponent(label);
					vertLayout.addComponent(checkbox);
					vertLayout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
					vertLayout.setComponentAlignment(checkbox, Alignment.MIDDLE_CENTER);
					vertLayout.setSizeUndefined();
					cssLayoutTokens.addComponent(vertLayout);
				}
			}
		});
		
		buttonDiscard.addListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				if (sentence != null)
				{
					SentenceServer.discardSentence(sentence);
					resetComponents();
				}
			}
		});
		
		buttonAddAnno.addListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				if (!textFieldLabel.getValue().equals("") && comboBoxTypes.getValue() != null)
				{
					Iterator<Component> iterator = cssLayoutTokens.getComponentIterator();
					Label l;
					CheckBox cb;
					VerticalLayout vl;
					ArrayList<String> selected = new ArrayList<String>();
					while (iterator.hasNext())
					{
						vl = (VerticalLayout) iterator.next();
						l = (Label) vl.getComponent(0);
						cb = (CheckBox) vl.getComponent(1);
						if (cb.booleanValue())
						{
							selected.add(l.getValue().toString());
							cb.setValue(false);
						}
					}
					BoaAnnotation anno = new BoaAnnotation((Type) comboBoxTypes.getValue(), selected);
					boolean isDuplicate = false;
					boolean isColliding = false;
					for (BoaAnnotation a : sentence.getAnnotations())
					{
						if (a.getTokens().size() == anno.getTokens().size())
						{
							boolean sameTokens = true;
							for (int i = 0; i < a.getTokens().size(); i++)
							{
								sameTokens = a.getTokens().get(i) == anno.getTokens().get(i);
								if (!sameTokens) break;
							}
							if (sameTokens)
							{
								isColliding = !(isDuplicate = a.getType().equals(anno.getType()));
								break;
							}
						}
					}
					if (isDuplicate)
					{
						getWindow().showNotification("Duplicate was dismissed.", Notification.TYPE_WARNING_MESSAGE);
					} else if (isColliding)
					{
						getWindow().showNotification("Label is already assigned to<br/> a different type.",
								Notification.TYPE_ERROR_MESSAGE);
					} else
					{
						sentence.getAnnotations().add(anno);
						listSelectAnnotations.addItem(anno);
					}
				} else
					getWindow().showNotification("Label or type is inavlid.", Notification.TYPE_ERROR_MESSAGE);
			}
		});
		
		buttonDelAnno.addListener(new Button.ClickListener()
		{
			public void buttonClick(ClickEvent event)
			{
				if (listSelectAnnotations.getValue() != null)
				{
					sentence.getAnnotations().remove(listSelectAnnotations.getValue());
					listSelectAnnotations.removeItem(listSelectAnnotations.getValue());
				}
			}
		});
	}
	
	/**
	 * Restores the initial status of the Components and sets sentence to null.
	 */
	private void resetComponents()
	{
		sentence = null;
		textFieldLabel.setReadOnly(false);
		textFieldLabel.setValue("");
		textFieldLabel.setReadOnly(true);
		cssLayoutTokens.removeAllComponents();
		listSelectAnnotations.removeAllItems();
	}
	
	/**
	 * Called to discardSentence when timeout occurred
	 */
	public void close()
	{
		if (sentence != null)
			SentenceServer.discardSentence(sentence);
	}
	
	@SuppressWarnings("deprecation")
	@AutoGenerated
	private AbsoluteLayout buildMainLayout()
	{
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setCaption("close XML");
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// buttonNext
		buttonNext = new Button();
		buttonNext.setCaption("next");
		buttonNext.setImmediate(true);
		buttonNext.setWidth("-1px");
		buttonNext.setHeight("-1px");
		mainLayout.addComponent(buttonNext, "top:0.0px;left:81.0px;");
		
		// buttonDiscard
		buttonDiscard = new Button();
		buttonDiscard.setCaption("discard");
		buttonDiscard.setImmediate(true);
		buttonDiscard.setWidth("-1px");
		buttonDiscard.setHeight("-1px");
		mainLayout.addComponent(buttonDiscard, "top:0.0px;left:431.0px;");
		
		// buttonAddAnno
		buttonAddAnno = new Button();
		buttonAddAnno.setCaption("add");
		buttonAddAnno.setImmediate(true);
		buttonAddAnno.setWidth("-1px");
		buttonAddAnno.setHeight("-1px");
		mainLayout.addComponent(buttonAddAnno, "top:180.0px;left:160.0px;");
		
		// buttonDelAnno
		buttonDelAnno = new Button();
		buttonDelAnno.setCaption("delete");
		buttonDelAnno.setImmediate(true);
		buttonDelAnno.setWidth("-1px");
		buttonDelAnno.setHeight("-1px");
		mainLayout.addComponent(buttonDelAnno, "top:180.0px;left:439.0px;");
		
		// comboBoxTypes
		comboBoxTypes = new ComboBox();
		comboBoxTypes.setImmediate(false);
		comboBoxTypes.setWidth("210px");
		comboBoxTypes.setHeight("-1px");
		mainLayout.addComponent(comboBoxTypes, "top:269.0px;left:0.0px;");
		
		// textFieldLabel
		textFieldLabel = new TextField();
		textFieldLabel.setImmediate(false);
		textFieldLabel.setWidth("210px");
		textFieldLabel.setHeight("-1px");
		textFieldLabel.setSecret(false);
		mainLayout.addComponent(textFieldLabel, "top:206.0px;left:0.0px;");
		
		// labelLabel
		labelLabel = new Label();
		labelLabel.setImmediate(false);
		labelLabel.setWidth("-1px");
		labelLabel.setHeight("-1px");
		labelLabel.setValue("Label:");
		mainLayout.addComponent(labelLabel, "top:182.0px;left:1.0px;");
		
		// labelType
		labelType = new Label();
		labelType.setImmediate(false);
		labelType.setWidth("-1px");
		labelType.setHeight("-1px");
		labelType.setValue("Type:");
		mainLayout.addComponent(labelType, "top:242.0px;left:0.0px;");
		
		// panelTokens
		panelTokens = buildPanelTokens();
		mainLayout.addComponent(panelTokens, "top:30.0px;left:0.0px;");
		
		// listSelectAnnotations
		listSelectAnnotations = new ListSelect();
		listSelectAnnotations.setImmediate(false);
		listSelectAnnotations.setWidth("271px");
		listSelectAnnotations.setHeight("87px");
		mainLayout.addComponent(listSelectAnnotations, "top:206.0px;left:230.0px;");
		
		// labelSentence
		labelSentence = new Label();
		labelSentence.setImmediate(false);
		labelSentence.setWidth("-1px");
		labelSentence.setHeight("-1px");
		labelSentence.setValue("Sentence:");
		mainLayout.addComponent(labelSentence, "top:2.0px;left:0.0px;");
		
		// labelAnnotations
		labelAnnotations = new Label();
		labelAnnotations.setImmediate(false);
		labelAnnotations.setWidth("-1px");
		labelAnnotations.setHeight("-1px");
		labelAnnotations.setValue("Annotations:");
		mainLayout.addComponent(labelAnnotations, "top:182.0px;left:230.0px;");
		
		return mainLayout;
	}
	
	@AutoGenerated
	private Panel buildPanelTokens()
	{
		// common part: create layout
		panelTokens = new Panel();
		panelTokens.setImmediate(false);
		panelTokens.setWidth("500px");
		panelTokens.setHeight("140px");
		
		// verticalLayoutTokens
		verticalLayoutTokens = new VerticalLayout();
		verticalLayoutTokens.setImmediate(false);
		verticalLayoutTokens.setWidth("100.0%");
		verticalLayoutTokens.setHeight("-1px");
		verticalLayoutTokens.setMargin(false);
		panelTokens.setContent(verticalLayoutTokens);
		
		return panelTokens;
	}
	
}