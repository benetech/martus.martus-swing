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
import java.io.IOException;
import java.io.NotSerializableException;

import javax.swing.JButton;


public class UiButton extends JButton
{
	public UiButton(String text)
	{
		super(text);
	}

	/* 
	 * This is copied from the horrible hack in JTextArea.
	 * I'm not sure if there is a better way, but somehow we 
	 * need to prevent Arabic letters from being chopped off 
	 * at the top and bottom.
	 * 
	 * Unfortunately, since my system didn't show the problem,
	 * I'm not sure this actually helped. Still, it seems like 
	 * a good idea to have a UiButton class, so it's not all 
	 * wasted, even if this method disappears. kbs.
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

	// This class is NOT intended to be serialized!!!
	private static final long serialVersionUID = 1;
	private void writeObject(java.io.ObjectOutputStream stream) throws IOException
	{
		throw new NotSerializableException();
	}

	final int EXTRA_PIXELS = 14;
}
