/*
 * LabelSearcher.java
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

package playground;

import java.util.ArrayList;

public interface LabelSearcher
{
	static ArrayList<String> surfaceForms = null;
	static ArrayList<String> falseSurfaceForms = null;
	
	//this is the Label for the Type any BoaAnnotations made by this Class will get
	//for example a subclass LabelSearcherWeight would initialize annoType with "WEIGHT"
	static String annoType = "ANNOTATION_TYPE";
	
	public BoaSentence searchUnit(BoaSentence sentence);
	public BoaSentence searchFalseUnit(BoaSentence sentence);
}
