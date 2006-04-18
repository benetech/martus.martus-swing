/*

The Martus(tm) free, social justice documentation and
monitoring software. Copyright (C) 2001-2006, Beneficent
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

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UiWrappedTextArea extends UiTextArea
{
	public UiWrappedTextArea(String message)
	{
		this(message, 60);
	}
	

	public UiWrappedTextArea(String message, int maxChars)
	{
		this(message, maxChars, 0);
	}

	public UiWrappedTextArea(String message, int maxChars, int minChars)
	{
		super(message);
		int cols = message.length();
		if(cols < minChars)
			cols = minChars;
		if(cols > maxChars)
			cols = maxChars;

		setColumns(cols);
		setEditable(false);
		setFocusable(true);
		setWrapStyleWord(true);
		setLineWrap(true);
		JFrame sampleWindow = new JFrame();
		setBackground(sampleWindow.getBackground());
		setForeground(sampleWindow.getForeground());
	}

	public JPanel getWrappedTextPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(this);
		return panel;
	}
}
