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

import java.awt.event.FocusEvent;
import javax.swing.CellEditor;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableModel;


public class UiTableWithCellEditingProtection extends UiTable
{

	public UiTableWithCellEditingProtection()
	{
		super();
	}
	public UiTableWithCellEditingProtection(TableModel model)
	{
		super(model);
	}

	// These are needed to work around a horrible quirk in swing:
	// If an editor is active when the table loses focus, or column resized,
	// the editing is not stopped, so the edits are lost.
	// We avoid this by stopping editing whenever we lose focus.
	protected void processFocusEvent(FocusEvent e) 
	{
		if(e.getID() == FocusEvent.FOCUS_LOST)
			saveCellContents();
		super.processFocusEvent(e);
	}

	public void columnMarginChanged(ChangeEvent e)
	{
		saveCellContents();
		super.columnMarginChanged(e);
	}
	
	private void saveCellContents()
	{
		CellEditor editor = getCellEditor();
		if(editor != null)
			editor.stopCellEditing();
	}
}
