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
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;


public class UiTable extends JTable
{
	public UiTable()
	{
		this(null);
	}
	
	public UiTable(TableModel model)
	{
		super(model);
		setComponentOrientation(UiLanguageDirection.getComponentOrientation());
		getTableHeader().setReorderingAllowed(false);
	}

	public void resizeTable()
	{
		resizeTable(getModel().getRowCount());
	}

	public void resizeTable(int rowCount)
	{
		Dimension d = getPreferredScrollableViewportSize();
		int rowHeight = getRowHeight() + getRowMargin() ;
		d.height = rowCount * rowHeight;
		setPreferredScrollableViewportSize(d);
	}
	
	static public void setMaxColumnWidthToHeaderWidth(JTable table, int column)
	{
		int width = setColumnWidthToHeaderWidth(table, column);
		table.getColumnModel().getColumn(column).setMaxWidth(width);
	}

	static public int setColumnWidthToHeaderWidth(JTable table, int column)
	{
		TableColumn columnToAdjust = table.getColumnModel().getColumn(column);
		String padding = "    ";
		String value = (String)columnToAdjust.getHeaderValue() + padding;

		TableCellRenderer renderer = columnToAdjust.getHeaderRenderer();
		if(renderer == null)
		{
			JTableHeader header = table.getTableHeader();
			renderer = header.getDefaultRenderer();
		}
		Component c = renderer.getTableCellRendererComponent(table, value, true, true, -1, column);
		int width = c.getPreferredSize().width;

		columnToAdjust.setPreferredWidth(width);
		columnToAdjust.setWidth(width);
		return width;
	}

	public TableCellRenderer getCellRenderer(int row, int column)
	{
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)super.getCellRenderer(row, column);
		renderer.setHorizontalAlignment(UiLanguageDirection.getHorizontalAlignment());
		return renderer;
	}
}
