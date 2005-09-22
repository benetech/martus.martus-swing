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
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.martus.util.language.LanguageOptions;


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
		setTableHeader(new UiTableHeader(getColumnModel()));
	}
	
	public int getRowHeight() 
	{
		int defaultRowHeight = super.getRowHeight();
		defaultRowHeight += getExtraHeightOfRowIfNecessary();
		return defaultRowHeight;
	}

	int getExtraHeightOfRowIfNecessary()
	{
		if(LanguageOptions.needsLanguagePadding())
			return EXTRA_PIXELS;
		return 0;
	}
	
	public void resizeTable()
	{
		resizeTable(getModel().getRowCount());
	}

	public void resizeTable(int rowCount)
	{
		Dimension d = getPreferredScrollableViewportSize();
		int constantRowHeight = getRowHeight() + getRowMargin() ;
		d.height = rowCount * constantRowHeight;
		setPreferredScrollableViewportSize(d);
	}
	
	public void setMaxColumnWidthToHeaderWidth(int column)
	{
		setColumnMaxWidth(column, getColumnHeaderWidth(column));
	}

	public void setColumnWidthToHeaderWidth(int column)
	{
		setColumnWidth(column, getColumnHeaderWidth(column));
	}

	public void setColumnMaxWidth(int column, int width) 
	{
		TableColumn columnToAdjust = getColumnModel().getColumn(column);
		columnToAdjust.setMaxWidth(width);
		setColumnWidth(column, width);
	}
	
	public void setColumnWidth(int column, int width) 
	{
		TableColumn columnToAdjust = getColumnModel().getColumn(column);
		columnToAdjust.setPreferredWidth(width);
		columnToAdjust.setWidth(width);
	}

	public int getColumnHeaderWidth(int column) 
	{
		TableColumn columnToAdjust = getColumnModel().getColumn(column);
		String padding = "    ";
		String value = (String)columnToAdjust.getHeaderValue() + padding;
		return getRenderedWidth(column, value);
	}
	
	public int getRenderedWidth(int column, String value) 
	{
		TableColumn columnToAdjust = getColumnModel().getColumn(column);
		TableCellRenderer renderer = columnToAdjust.getHeaderRenderer();
		if(renderer == null)
		{
			JTableHeader header = getTableHeader();
			renderer = header.getDefaultRenderer();
		}
		Component c = renderer.getTableCellRendererComponent(this, value, true, true, -1, column);
		int width = c.getPreferredSize().width;
		return width;
	}
	
	public class UiTableHeader extends JTableHeader
	{
		public UiTableHeader (TableColumnModel model)
		{
			super(model);
			setComponentOrientation(UiLanguageDirection.getComponentOrientation());
			setReorderingAllowed(false);
		}
		
		public Dimension getPreferredSize()
		{
			Dimension d = super.getPreferredSize();
			d.height += getExtraHeightOfRowIfNecessary();
			return d;
		}
	}

	public TableCellRenderer getCellRenderer(int row, int column)
	{
		TableCellRenderer renderer = super.getCellRenderer(row, column);
		if(renderer instanceof DefaultTableCellRenderer)
			((DefaultTableCellRenderer)renderer).setHorizontalAlignment(UiLanguageDirection.getHorizontalAlignment());
		return renderer;
	}

	private static final int EXTRA_PIXELS = 14;
	
}
