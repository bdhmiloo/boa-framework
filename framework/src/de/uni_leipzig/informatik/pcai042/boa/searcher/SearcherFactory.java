/*
 * SearcherFactory.java
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

package de.uni_leipzig.informatik.pcai042.boa.searcher;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;

/**
 * This class simplifies the construction of SearchAlgorithm instances from the
 * information provides by an ConfigLoader. It uses reflection to choose the
 * right class for any type, which is defined by a TYPE_ALGO key.
 * 
 * @author Simon Suiter
 */
public class SearcherFactory
{
	private static final Logger logger = LoggerFactory.getLogger(SearcherFactory.class);
	
	private HashMap<String, Class<? extends SearchAlgorithm>> algos;
	private HashMap<String, HashMap<String, Set<String>>> configs;
	
	public SearcherFactory(ConfigLoader cf)
	{
		algos = new HashMap<String, Class<? extends SearchAlgorithm>>();
		configs = new HashMap<String, HashMap<String, Set<String>>>();
		Set<String> types = cf.openConfigSurfaceForms("ALL_UNITS");
		
		for (String type : types)
		{
			try
			{
				Class<?> clazz = Class.forName(cf.returnValue(type + "_ALGO"));
				if (SearchAlgorithm.class.isAssignableFrom(clazz))
				{
					algos.put(type, castClass(clazz));
					HashMap<String, Set<String>> config = new HashMap<String, Set<String>>();
					Set<String> keys = cf.openConfigSurfaceForms(type);
					Iterator<String> it = keys.iterator();
					while (it.hasNext())
					{
						String key = it.next();
						config.put(key, cf.openConfigSurfaceForms(key));
					}
					configs.put(type, config);
				} else
				{
					logger.warn("Class ist not an SearchAlgorithm: {}", clazz.getName());
				}
				
			} catch (ClassNotFoundException e)
			{
				logger.warn("Couldn't load SearchAlgorithm: {}", cf.returnValue(type + "_ALGO"));
			} catch (SecurityException e)
			{
				logger.warn("Class ist not an valid SearchAlgorithm: {}", cf.returnValue(type + "_ALGO"));
			} catch (IllegalArgumentException e)
			{
				logger.warn("Class ist not an valid SearchAlgorithm: {}", cf.returnValue(type + "_ALGO"));
			}
		}
	}
	
	/**
	 * Creates a new instance of a SearchAlgorithm searching for a certain type.
	 * 
	 * @param type
	 *            the type of annotations the searcher should find
	 * @return the new searcher
	 */
	public SearchAlgorithm newSearcher(String type)
	{
		try
		{
			return algos.get(type).getConstructor(new Class[] { HashMap.class, String.class })
					.newInstance(configs.get(type), type);
		} catch (IllegalArgumentException e)
		{
			
		} catch (SecurityException e)
		{
			
		} catch (InstantiationException e)
		{
			
		} catch (IllegalAccessException e)
		{
			
		} catch (InvocationTargetException e)
		{
			
		} catch (NoSuchMethodException e)
		{
			
		}
		logger.error("Couldn't create searcher for \"{}\"", type);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private Class<? extends SearchAlgorithm> castClass(Class<?> clazz)
	{
		return (Class<? extends SearchAlgorithm>) clazz;
	}
	
	/**
	 * Returns an iterator to iterate over all aviable annotation types.
	 * 
	 * @return
	 */
	public Iterator<String> getTypeIterator()
	{
		return algos.keySet().iterator();
	}
}
