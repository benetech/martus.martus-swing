/*

The Martus(tm) free, social justice documentation and
monitoring software. Copyright (C) 2001-2003, Beneficent
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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

//This implementation is needed because of a Java bug 
//which by clicking on a directory changes the file name 
//to be saved to that of the directory name.

public class UiFileChooser extends JFileChooser
{
	private UiFileChooser()
	{
	}

	private UiFileChooser(String title, File currentlySelectedFile, File currentDirectory, String buttonLabel, FileFilter filterToUse)
	{
		super();
		addPropertyChangeListener(JFileChooser.DIRECTORY_CHANGED_PROPERTY,new DirectoryChangeListener());
		addPropertyChangeListener(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY, new FileSelectedChangeListener());
		//setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		
		if(title != null)
			setDialogTitle(title);
		if(currentlySelectedFile != null)
			setSelectedFile(currentlySelectedFile);
		if(currentDirectory != null)
			setCurrentDirectory(currentDirectory);
		if(buttonLabel != null)
			setApproveButtonText(buttonLabel);
		if(filterToUse != null)
			setFileFilter(filterToUse);
	}
	
	static public FileDialogResults displayFileSaveDialog(Component owner, String title, File currentlySelectedFile)
	{
		return displayFileSaveDialog(owner, title, currentlySelectedFile, null);
	}
	
	static public FileDialogResults displayFileSaveDialog(Component owner, String title, File currentlySelectedFile, File currentDirectory)
	{
		UiFileChooser chooser = new UiFileChooser(title, currentlySelectedFile, currentDirectory, null, null);
		return getFileResults(chooser.showSaveDialog(owner), chooser);
	}
	
	
	static public FileDialogResults displayFileOpenDialog(Component owner, String title, File currentlySelectedFile)
	{
		return displayFileOpenDialog(owner, title, currentlySelectedFile, null, null, null);
	}

	static public FileDialogResults displayFileOpenDialog(Component owner, String title, File currentlySelectedFile, File currentDirectory)
	{
		return displayFileOpenDialog(owner, title, currentlySelectedFile, currentDirectory, null, null);
	}
	
	static public FileDialogResults displayFileOpenDialog(Component owner, String title, File currentlySelectedFile, File currentDirectory, String buttonLabel, FileFilter filterToUse)
	{
		UiFileChooser chooser = new UiFileChooser(title, currentlySelectedFile, currentDirectory, buttonLabel, filterToUse);
		return getFileResults(chooser.showOpenDialog(owner), chooser);
	}
	
	private static FileDialogResults getFileResults(int result, UiFileChooser chooser)
	{
		if(result != JFileChooser.APPROVE_OPTION)
			return new FileDialogResults();
		return new FileDialogResults(chooser.getSelectedFile(), false);
	}

	static public class FileDialogResults
	{
		FileDialogResults()
		{
			this(null, true);
		}
		
		FileDialogResults(File fileChoosenToUse, boolean wasCancelChoosen)
		{
			cancelChoosen = wasCancelChoosen;
			fileChoosen = fileChoosenToUse;
		}
		
		public boolean wasCancelChoosen()
		{
			return cancelChoosen;
		}

		public File getFileChoosen()
		{
			return fileChoosen;
		}
		
		public File getCurrentDirectory()
		{
			if(fileChoosen == null)
				return null;
			return fileChoosen.getParentFile();
		}

		private File fileChoosen;
		private boolean cancelChoosen;
	}

	private class DirectoryChangeListener implements PropertyChangeListener 
	{
		public void propertyChange(PropertyChangeEvent e) 
		{
			processDirChanged(e);
		}
	}

	void processDirChanged(PropertyChangeEvent e) 
	{
		if ( previouslySelectedFile == null )
			return; 

		File newSelectedFile = new File(getCurrentDirectory().getPath() + File.separator + previouslySelectedFile.getName());
		previouslySelectedFile = newSelectedFile;
		setSelectedFile(previouslySelectedFile);
	}

	private class FileSelectedChangeListener implements PropertyChangeListener
	{
		public void propertyChange(PropertyChangeEvent e) 
		{
			processFileSelected(e);
		}
	}
	
	void processFileSelected(PropertyChangeEvent e) 
	{
		File selectedFile = getSelectedFile();
		if ( previouslySelectedFile != null && ( selectedFile == null || selectedFile.isDirectory()) ) 
			setSelectedFile(previouslySelectedFile);
		else 
			previouslySelectedFile = getSelectedFile();
	} 

	private File previouslySelectedFile = null;
}
