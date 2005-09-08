package org.martus.swing;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;
abstract public class TableSortableModel extends AbstractTableModel
{
	public abstract Object getValueAtDirect(int rowIndex, int columnIndex);

	public Object getValueAt(int rowIndex, int columnIndex) 
	{
		int sortedRowIndex = getSortedRowIndex(rowIndex);
		return getValueAtDirect(sortedRowIndex, columnIndex);
	}

	public int getSortedRowIndex(int rowIndex)
	{
		if(sortedRowIndexes.isEmpty())
			return rowIndex;
		return ((Integer)sortedRowIndexes.get(new Integer(rowIndex))).intValue();
	}
	
	public void setSortedRowIndexes(Vector newIndexes)
	{
		sortedRowIndexes.clear();
		for(int i = 0; i < getRowCount(); ++i)
		{
			sortedRowIndexes.put(new Integer(i), newIndexes.get(i));
		}
	}
	
	HashMap sortedRowIndexes = new HashMap();
}
