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

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;

import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.uni_leipzig.informatik.pcai042.boa.converter.Converter;
import de.uni_leipzig.informatik.pcai042.boa.converter.ConverterDate;
import de.uni_leipzig.informatik.pcai042.boa.converter.ConverterLinearMeasure;
import de.uni_leipzig.informatik.pcai042.boa.converter.ConverterTemperature;
import de.uni_leipzig.informatik.pcai042.boa.converter.ConverterWeight;
import de.uni_leipzig.informatik.pcai042.boa.manager.BoaAnnotation;
import de.uni_leipzig.informatik.pcai042.boa.manager.BoaSentence;
import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;
import de.uni_leipzig.informatik.pcai042.boa.manager.Scoring;
import de.uni_leipzig.informatik.pcai042.boa.manager.Tokenizer;
import de.uni_leipzig.informatik.pcai042.boa.searcher.Annotator;
import de.uni_leipzig.informatik.pcai042.boa.searcher.SearcherFactory;

/**
 * 
 * @author Simon Suiter, Duc Huy Bui
 */
@SuppressWarnings("serial")
public class EvaluationApp extends Application implements ClickListener, ValueChangeListener
{
	private static final Logger logger = LoggerFactory.getLogger(EvaluationApp.class);
	
	private EvaluationView view = new EvaluationView();
	private File rootFolder;
	
	private ArrayList<BoaSentence> sentences;
	private ArrayList<BoaSentence> goldstandard;
	ArrayList<BoaSentence> annotated;
	
	private Annotator annotator;
	private ConfigLoader cl;
	private SearcherFactory sf;
	private Tokenizer tokenizer;
	
	private ConverterWeight conWEIGHT;
	private ConverterLinearMeasure conLINEAR_MEASURE;
	private ConverterTemperature conTEMPERATURE;
	private ConverterDate conDATE;
	
	@Override
	public void init()
	{
		rootFolder = new File(getContext().getBaseDirectory(), "WEB-INF/resources/");
		cl = new ConfigLoader(rootFolder);
		sf = new SearcherFactory(cl);
		annotator = new Annotator(sf);
		tokenizer = new Tokenizer();
		
		conWEIGHT = new ConverterWeight(cl);
		conLINEAR_MEASURE = new ConverterLinearMeasure(cl);
		conTEMPERATURE = new ConverterTemperature(cl);
		conDATE = new ConverterDate(cl);
		
		Window mainWindow = new Window("Boa");
		setMainWindow(mainWindow);
		mainWindow.getContent().setSizeFull();
		mainWindow.addComponent(view);
		
		view.getButtonAnnotate().addListener((ClickListener) this);
		view.getButtonNew().addListener((ClickListener) this);
		view.getButtonNext2().addListener((ClickListener) this);
		view.getTableEvaluation().addListener((Property.ValueChangeListener) this);
		view.getListSelectAnnotation().addListener((Property.ValueChangeListener) this);
		
		Scoring scoring = null;
		try
		{
			scoring = new Scoring(new File(rootFolder, "goldstandard.xml"), sf);
			sentences = scoring.getWorkSentences();
			goldstandard = scoring.getGoldstandard();
			ArrayList<double[]> result = scoring.score();
			for (int i = 0; i < result.size(); i++)
			{
				// last "new Integer()" represents position of object in table!
				view.getTableEvaluation().addItem(
						new Object[] { new Integer(i), sentences.get(i).getSentence(), new Double(result.get(i)[0]),
								new Double(result.get(i)[1]), new Double(result.get(i)[2]) }, new Integer(i));
			}
		} catch (ValidityException e)
		{	
		} catch (ParsingException e)
		{
		} catch (IOException e)
		{
		}
		if (scoring == null) logger.error("Failed to load goldstandard");
	}
	
	/**
	 * Listener for clicking buttons.
	 */
	public void buttonClick(ClickEvent event)
	{
		Button button = event.getButton();
		
		if (button == view.getButtonAnnotate())
		{
			String text = (String) view.getTextArea().getValue();
			text = text.trim();
			if (!text.equals(""))
			{
				annotated = tokenizer.tokenize(text);
				annotator.annotate(annotated);
				
				view.getListSelectAnnotation().removeAllItems();
				for (BoaSentence sentence : annotated)
				{
					for (BoaAnnotation anno : sentence.getAnnotations())
					{
						view.getListSelectAnnotation().addItem(anno);
					}
				}
				
			}
			
		} else if (button == view.getButtonNew())
		{
			view.resetComponents();
		} else if (button == view.getButtonNext2())
		{
			view.getTableEvaluation().setValue(new Integer((Integer) (view.getTableEvaluation().getValue()) + 1));
			
		}
	}
	
	/**
	 * Listener for selecting a line of a table.
	 */
	public void valueChange(ValueChangeEvent event)
	{
		if (view.getTableEvaluation().getValue() != null)
		{
			int index = (Integer) view.getTableEvaluation().getValue();
			view.getListSelectFramework().removeAllItems();
			view.getListSelectGoldstandard().removeAllItems();
			for (BoaAnnotation anno : goldstandard.get(index).getAnnotations())
			{
				view.getListSelectGoldstandard().addItem(anno);
			}
			for (BoaAnnotation anno : sentences.get(index).getAnnotations())
			{
				view.getListSelectFramework().addItem(anno);
			}
			view.getTextAreaEvalSentence().setReadOnly(false);
			view.getTextAreaEvalSentence().setValue(sentences.get(index).getSentence());
			view.getTextAreaEvalSentence().setReadOnly(true);
			
		}
		
		BoaAnnotation anno = (BoaAnnotation) view.getListSelectAnnotation().getValue();
		
		if (anno != null)
		{
			Converter converter = null;
			
			if (anno.getType().equals("DATE"))
			{
				converter = conDATE;
			}
			if (anno.getType().equals("WEIGHT"))
			{
				converter = conWEIGHT;
			}
			if (anno.getType().equals("TEMPERATURE"))
			{
				converter = conTEMPERATURE;
			}
			if (anno.getType().equals("LINEAR_MEASURE"))
			{
				converter = conLINEAR_MEASURE;
			}
			if (converter != null)
			{
				ArrayList<String> units;
				try
				{
					units = converter.convertUnits(anno);
					
					StringBuilder sb = new StringBuilder();
					for (String string : units)
					{
						sb.append(string);
						sb.append("\n");
					}
					view.getTextAreaAnnotation().setReadOnly(false);
					view.getTextAreaAnnotation().setValue(sb.toString());
					view.getTextAreaAnnotation().setReadOnly(true);
				} catch (ParseException e)
				{
					e.printStackTrace();
				}
			} else
			{
			}
		}
	}
}
