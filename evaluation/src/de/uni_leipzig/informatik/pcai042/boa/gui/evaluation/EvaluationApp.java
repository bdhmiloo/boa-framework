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
import java.util.ArrayList;

import nu.xom.ParsingException;
import nu.xom.ValidityException;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.uni_leipzig.informatik.pcai042.boa.manager.BoaSentence;
import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;
import de.uni_leipzig.informatik.pcai042.boa.manager.Scoring;
import de.uni_leipzig.informatik.pcai042.boa.manager.SentenceLoader;
import de.uni_leipzig.informatik.pcai042.boa.searcher.SearcherFactory;

/**
 * 
 * @author Simon Suiter, Duc Huy Bui
 */
@SuppressWarnings("serial")
public class EvaluationApp extends Application implements ItemClickListener, ClickListener, ValueChangeListener
{
	private EvaluationView view = new EvaluationView();
	private File rootFolder;
	
	@Override
	public void init()
	{
		rootFolder = new File(getContext().getBaseDirectory(), "WEB-INF/resources/");
		
		Window mainWindow = new Window("Boa");
		setMainWindow(mainWindow);
		mainWindow.getContent().setSizeFull();
		mainWindow.addComponent(view);
		
		view.getButtonAnnotate().addListener((ClickListener) this);
		view.getButtonNew().addListener((ClickListener) this);
		view.getButtonNext().addListener((ClickListener) this);
		view.getButtonNext2().addListener((ClickListener) this);
		view.getTableEvaluation().addListener((Property.ValueChangeListener) this);
		
		Scoring scoring = null;
		try
		{ 
			scoring = new Scoring(new File(rootFolder, "goldstandard.xml"), new SearcherFactory(new ConfigLoader(rootFolder)));
		} catch (ValidityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParsingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<BoaSentence> sentences = scoring.getWorkSentences();
		ArrayList<BoaSentence> gold = scoring.getGoldstandard();
		ArrayList<double[]> result = scoring.score();
		for (int i = 0; i < result.size(); i++)
		{
			// last "new Integer()" represents position of object in table!
			view.getTableEvaluation().addItem(
					new Object[] { new Integer(i), sentences.get(i).getSentence(), new Double(result.get(i)[0]),
							new Double(result.get(i)[1]), new Double(result.get(i)[2]) }, new Integer(i));
		}
	}
	
	/**
	 * Listener for clicking buttons.
	 */
	public void buttonClick(ClickEvent event)
	{
		Button button = event.getButton();
		
		if (button == view.getButtonAnnotate())
		{
			// TODO add code here
			
		} else if (button == view.getButtonNew())
		{
			view.resetComponents();
		} else if (button == view.getButtonNext())
		{
			// TODO add code here
			
		} else if (button == view.getButtonNext2())
		{
			// TODO add code here
			
		}
	}
	
	/**
	 * Listener for choosing an item in listselect.
	 */
	public void itemClick(ItemClickEvent event)
	{
		
	}
	
	/**
	 * Listener for selecting a line of a table.
	 */
	public void valueChange(ValueChangeEvent event)
	{
		
	}
}
