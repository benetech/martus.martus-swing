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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.event.TableModelEvent;

public class UiSortableTable extends UiTable 
{
	public UiSortableTable(TableSortableModel model)
	{
		super(model);
		getTableHeader().addMouseListener(new SortColumnListener(this));
	}
	
	public TableSortableModel getSortableTableModel()
	{
		return (TableSortableModel)getModel();
	}
	
	class SortColumnListener extends MouseAdapter
	{
		SortColumnListener (UiSortableTable tableToUse)
		{
			table = tableToUse;
		}
		
		public void mouseClicked(MouseEvent e) 
		{
		     int columnToSort = table.getColumnModel().getColumnIndexAtX(e.getX());
		     sortTable(columnToSort);
		}

		private void sortTable(int columnToSort) 
		{
			Vector newIndexes = getNewSortedOrderOfRows(columnToSort);
			TableSortableModel model = table.getSortableTableModel();
			model.setSortedRowIndexes(newIndexes);
			tableChanged(new TableModelEvent(model));
		}

		private Vector getNewSortedOrderOfRows(int columnToSort) 
		{
		    sortingOrder = -sortingOrder;
			return sortTable(table.getSortableTableModel(), columnToSort);
		}
		
		private synchronized Vector sortTable(TableSortableModel model, int columnToSort)
		{
			class Sorter implements Comparator
			{
				public Sorter(TableSortableModel modelToUse, int column, int sortDirection)
				{
					tableModel = modelToUse;
					columnToSortOn = column;
					sorterDirection = sortDirection;
				}
				public int compare(Object o1, Object o2)
				{
					Comparable obj1 = (Comparable)tableModel.getValueAtDirect(((Integer)(o1)).intValue(), columnToSortOn);
					Comparable obj2 = (Comparable)tableModel.getValueAtDirect(((Integer)(o2)).intValue(), columnToSortOn);
					return obj1.compareTo(obj2) * sorterDirection;
				}
				TableSortableModel tableModel; 
				int columnToSortOn;
				int sorterDirection;
			}

			Vector sortedRowIndexes = new Vector();
			for(int i = 0; i < model.getRowCount(); ++i)
				sortedRowIndexes.add(new Integer(i));

			Collections.sort(sortedRowIndexes, new Sorter(model, columnToSort, sortingOrder));
			return sortedRowIndexes;
		}

		UiSortableTable table;
		int sortingOrder = 1; 
	}
	
}
