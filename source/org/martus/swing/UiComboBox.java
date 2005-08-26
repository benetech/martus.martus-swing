/*

The Martus(tm) free, social justice documentation and
monitoring software. Copyright (C) 2001-2004, Beneficent
Technology, Inc. (Benetech).

Martus is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either
version 2 of the License, or (at your option) any later
version with the additions and exceptions described in the
accompanying Martus license file entitled "license.txt".

It is distributed WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, including warranties of fitness of purpose or
merchantability.  See the accompanying Martus License and
GPL license for more details on the required license terms
for this software.

You should have received a copy of the GNU General Public
License along with this program; if not, write to the Free
Software Foundation, Inc., 59 Temple Place - Suite 330,
Boston, MA 02111-1307, USA.

*/
package org.martus.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import org.martus.util.language.LanguageOptions;


public class UiComboBox extends JComboBox
{
	
	public UiComboBox()
	{
		setComponentOrienation();
	}
	
	public UiComboBox(Object[] items)
	{
		super(items);
		setComponentOrienation();
	}

	public void setUI(ComboBoxUI ui)
	{
		super.setUI(new slimArrowComboBoxUi());
	}

	private class UiComboListCellRenderer extends DefaultListCellRenderer
	{
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
		{
			setComponentOrientation(UiLanguageDirection.getComponentOrientation());
			setHorizontalAlignment(UiLanguageDirection.getHorizontalAlignment());
			return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		}
	}
	
	private void setComponentOrienation()
	{
		setComponentOrientation(UiLanguageDirection.getComponentOrientation());
		setRenderer(new UiComboListCellRenderer());
	}
	
	public class slimArrowComboBoxUi extends BasicComboBoxUI
	{
		protected LayoutManager createLayoutManager()
		{
			return new slimArrowLayoutManager();
		}
		
		public class slimArrowLayoutManager extends ComboBoxLayoutManager
		{
	        public void layoutContainer(Container parent) 
	        {
	        	super.layoutContainer(parent);
	        	
		        if(LanguageOptions.needsLanguagePadding()) 
		        {
		        	Rectangle rect = arrowButton.getBounds();
		        	int slimArrowFactor = 2;
		        	rect.width /= slimArrowFactor;
		        	arrowButton.setBounds(rect);
		        }
	        }
		}
	}
	
}
