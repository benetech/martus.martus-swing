/*

The Martus(tm) free, social justice documentation and
monitoring software. Copyright (C) 2005-2007, Beneficent
Technology, Inc. (The Benetech Initiative).

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

To the extent this copyrighted software code is used in the 
Miradi project, it is subject to a royalty-free license to 
members of the Conservation Measures Partnership when 
used with the Miradi software as specified in the agreement 
between Benetech and WCS dated 5/1/05.
*/
package org.martus.swing;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class UiRenderer extends DefaultTableCellRenderer
{
	public UiRenderer(UiTableModel modelToUse, Color disabledBackgroundColorToUse,TableCellRenderer oldRendererToUse)
	{
		tableModel = modelToUse;
		oldRenderer = oldRendererToUse;
		disabledBackgroundColor = disabledBackgroundColorToUse;
	}

	public Component getTableCellRendererComponent(
			JTable tableToUse, Object value,
			boolean isSelected, boolean hasFocus,
			int row, int column)
	{
		Component cell = oldRenderer.getTableCellRendererComponent(tableToUse, value, isSelected, hasFocus, row, column);
		cell.setComponentOrientation(UiLanguageDirection.getComponentOrientation());
		if(normalBackgroundColor == null)
			normalBackgroundColor = cell.getBackground();
		if(!isSelected)
		{
			if(tableModel.isEnabled(row))
				cell.setBackground(normalBackgroundColor);
			else
				cell.setBackground(disabledBackgroundColor);
		}			
		return cell;
	}

	Color normalBackgroundColor;
	Color disabledBackgroundColor;
	protected UiTableModel tableModel;
	TableCellRenderer oldRenderer;
}
