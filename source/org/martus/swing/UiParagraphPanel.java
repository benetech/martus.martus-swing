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
import javax.swing.JLabel;
import javax.swing.JPanel;


public class UiParagraphPanel extends JPanel
{
	public UiParagraphPanel()
	{
		super();
		setLayout(new ParagraphLayout());
		setComponentOrientation(UiLanguageDirection.getComponentOrientation());
	}
	
	public ParagraphLayout getParagraphLayout()
	{
		return (ParagraphLayout)getLayout();
	}
	
	public void addBlankLine()
	{
		addOnNewLine(new JLabel(""));
	}

	public void addOnNewLine(Component itemToAdd)
	{
		addComponents(new JLabel(""), itemToAdd);
	}

	public void addComponents(Component item1, Component item2)
	{
		if(UiLanguageDirection.isRightToLeftLanguage())
		{
			if(!item2.isVisible())
				add(new JLabel(""),ParagraphLayout.NEW_PARAGRAPH);
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
}
