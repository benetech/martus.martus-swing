/*

The Martus(tm) free, social justice documentation and
monitoring software. Copyright (C) 2005, Beneficent
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
import javax.swing.Box;
import javax.swing.BoxLayout;

public class UiVBox extends Box
{
	public UiVBox()
	{
		super(BoxLayout.Y_AXIS);
		setComponentOrientation(UiLanguageDirection.getComponentOrientation());		
	}

	public Component add(Component componentToAddRespectingOrientation)
	{
		return addRespectingOrientation(new Component[] {componentToAddRespectingOrientation});
	}

	public Component add(Component[] componentsToAddRespectingOrientation)
	{
		return addRespectingOrientation(componentsToAddRespectingOrientation);
	}

	public Component addCentered(Component component)
	{
		return super.add(component);
	}

	public Component addSpace()
	{
		return addCentered(new UiLabel(" "));
	}
	private Component addRespectingOrientation(Component[] component)
	{
		Box h1 = Box.createHorizontalBox();
		Utilities.addComponentsRespectingOrientation(h1, component);
		Box h2 = Box.createHorizontalBox();
		Utilities.addComponentsRespectingOrientation(h2, new Component[] {h1,Box.createHorizontalGlue()});
		return super.add(h2);
	}
}
