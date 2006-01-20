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

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class UiTextArea extends JTextArea
{
	public UiTextArea(int rows, int cols)
	{
		super(rows, cols);
		initalize();
	}

	public UiTextArea(String text)
	{
		super(text);
		initalize();
	}

	private void initalize()
	{
		ComponentOrientation languageOrientation = UiLanguageDirection.getComponentOrientation();
		setComponentOrientation(languageOrientation);
		setFont(new Font(FontHandler.defaultFontName, Font.PLAIN, FontHandler.defaultFontSize));
		if(languageOrientation == ComponentOrientation.RIGHT_TO_LEFT && getText().length()==0)
		{
			//Bug in Java's JTextArea
			//When orientation is Right to Left, the cursor is not Right justified in an empty JTextArea.
			setText(" ");
			setText("");
		}
		SetTabKeyForFocusEvents();
	}

	private void SetTabKeyForFocusEvents()
	{
		Set set = new HashSet(getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
	    set.clear();
	    set.add(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_TAB, 0));
	    setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, set);
	    set.clear();
	    set.add(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_TAB,
	    		 java.awt.event.InputEvent.SHIFT_MASK));
	    setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, set);
	}


	/* 
	 * NOTE: This is a horrible hack to work around the fact that a JTextArea
	 * seems to completely ignore any calls to setBorder or setMargin.
	 * We need to add some space to the bottom of the field to avoid Arabic
	 * (and even English) characters from being chopped off. 
	 * The number of pixels is arbitrary and may need to be adjusted.
	 */
	
	public Dimension getPreferredSize()
	{
		return Utilities.addCushionToHeightIfRequired(super.getPreferredSize(), EXTRA_PIXELS);
	}

	/* Another horible hack to deal with "tall" letters
	 * getMaximumSize() instead of getPreferredSize()
	 * is being called from Box's horizontal and vertical.
	 */
	
	public Dimension getMaximumSize()
	{
		return Utilities.addCushionToHeightIfRequired(super.getMaximumSize(), EXTRA_PIXELS);
	}
	
	final int EXTRA_PIXELS = 7;
}
