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

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;

import javax.swing.CellEditor;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
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
		enableEvents(AWTEvent.FOCUS_EVENT_MASK);
	}
	
	// This is needed to work around a horrible quirk in swing:
	// If an editor is active when the table loses focus,
	// the editing is not stopped, so the edits are lost.
	// We avoid this by stopping editing whenever we lose focus.
	protected void processFocusEvent(FocusEvent e) 
	{
		CellEditor editor = getCellEditor();
		if(editor != null)
			editor.stopCellEditing();
		super.processFocusEvent(e);
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
	
	static public void setColumnWidthToHeaderWidth(JTable table, int column)
	{
		TableColumnModel columnModel = table.getColumnModel();
		TableColumn statusColumn = columnModel.getColumn(column);
		String padding = "    ";
		String value = (String)statusColumn.getHeaderValue() + padding;

		TableCellRenderer renderer = statusColumn.getHeaderRenderer();
		if(renderer == null)
		{
			JTableHeader header = table.getTableHeader();
			renderer = header.getDefaultRenderer();
		}
		Component c = renderer.getTableCellRendererComponent(table, value, true, true, -1, column);
		Dimension size = c.getPreferredSize();

		statusColumn.setPreferredWidth(size.width);
		statusColumn.setMaxWidth(size.width);
	}

	public TableCellRenderer getCellRenderer(int row, int column)
	{
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)super.getCellRenderer(row, column);
		renderer.setHorizontalAlignment(UiLanguageDirection.getHorizontalAlignment());
		return renderer;
	}
}
