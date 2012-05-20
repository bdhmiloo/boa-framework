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

import de.uni_leipzig.informatik.pcai042.boa.manager.ConfigLoader;

public class SearcherFactory
{
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
				// Constructor<?> c = clazz.getConstructor(new Class[] {
				// HashMap.class, String.class });
				// if (SearchAlgorithm.class.isInstance(c.newInstance(new
				// HashMap<String, Set<String>>(), "")))
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
					// TODO logger
				}
				
			} catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public SearchAlgorithm newSearcher(String type)
	{
		try
		{
			return algos.get(type).getConstructor(new Class[] { HashMap.class, String.class })
					.newInstance(configs.get(type), type);
		} catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private Class<? extends SearchAlgorithm> castClass(Class<?> clazz)
	{
		return (Class<? extends SearchAlgorithm>) clazz;
	}
	
	public Iterator<String> getTypeIterator()
	{
		return algos.keySet().iterator();
	}
}
