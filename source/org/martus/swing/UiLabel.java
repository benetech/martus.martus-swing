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

import java.awt.Dimension;

import javax.swing.Icon;
import javax.swing.JLabel;

import org.martus.util.language.LanguageOptions;


public class UiLabel extends JLabel
{
	public UiLabel()
	{
		super();
		setOrientation();
	}
	
	public UiLabel(String text)
	{
		super(text);
		setOrientation();
	}

	public UiLabel(String text, int alignment)
	{
		super(text, alignment);
		setOrientation();
	}
	
	public UiLabel(String text, Icon icon, int alignment)
	{
		super(text, icon, alignment);
		setOrientation();
	}

	private void setOrientation()
	{
		setComponentOrientation(UiLanguageDirection.getComponentOrientation());
		setHorizontalAlignment(UiLanguageDirection.getHorizontalAlignment());
	}
	

	/* 
	 * This is copied from the horrible hack in JTextArea.
	 * I'm not sure if there is a better way, but somehow we 
	 * need to prevent Arabic letters from being chopped off 
	 * at the top and bottom.
	 * 
	 * Unfortunately, since my system didn't show the problem,
	 * I'm not sure this actually helped. Still, it seems like 
	 * a good idea to have a UiLabel class, so it's not all 
	 * wasted, even if this method disappears. kbs.
	 */
	public Dimension getPreferredSize()
	{
		if(!LanguageOptions.needsLanguagePadding())
			return super.getPreferredSize();

		final int EXTRA_PIXELS = 14;
		Dimension d = super.getPreferredSize();
		d.setSize(d.getWidth(), d.getHeight() + EXTRA_PIXELS);
		return d;
	}
}
