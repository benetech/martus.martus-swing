/*

The Martus(tm) free, social justice documentation and
monitoring software. Copyright (C) 2001-2005, Beneficent
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

import javax.swing.JPanel;

import org.martus.util.language.LanguageOptions;


public class UiParagraphPanel extends JPanel
{
	public UiParagraphPanel()
	{
		super();
		setLayout(new ParagraphLayout());
		setComponentOrientation(UiLanguageDirection.getComponentOrientation());
	}
	
	
	public void outdentFirstField()
	{
		getParagraphLayout().outdentFirstField();
	}
	
	public int getFirstColumnMaxWidth(Container target)
	{
		return getParagraphLayout().getFirstColumnMaxWidth(target);
	}
	
	public void setFirstColumnWidth(int width)
	{
		getParagraphLayout().setFirstColumnWidth(width);
	}
	
	public void addBlankLine()
	{
		addOnNewLine(new UiLabel(""));
	}

	public void addOnNewLine(Component itemToAdd)
	{
		addComponents(new UiLabel(""), itemToAdd);
	}

	public void addLabelOnly(Component itemToAdd)
	{
		addComponents(itemToAdd, new UiLabel(""));
	}

	public void addComponents(Component item1, Component item2)
	{
		if(LanguageOptions.isRightToLeftLanguage())
		{
			if(!item2.isVisible())
				add(new UiLabel(""),ParagraphLayout.NEW_PARAGRAPH);
			else
				add(item2, ParagraphLayout.NEW_PARAGRAPH);
			add(item1);
		}
		else
		{
			add(item1, ParagraphLayout.NEW_PARAGRAPH);
			add(item2);
		}
	}

	private ParagraphLayout getParagraphLayout()
	{
		return ((ParagraphLayout)getLayout());
	}
}
