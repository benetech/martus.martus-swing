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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.martus.util.TokenReplacement;
import org.martus.util.TokenReplacement.TokenInvalidException;


public class UiNotifyDlg extends JDialog implements ActionListener
{

	public UiNotifyDlg(JFrame owner, String title, String[] contents, String[] buttons)
	{
		this(owner, title, contents, buttons, new HashMap());
	}
	
	public UiNotifyDlg(JFrame owner, String title, String[] contents, String[] buttons, Map tokenReplacement)
	{
		super(owner, title , true);
		setComponentOrientation(UiLanguageDirection.getComponentOrientation());
		try
		{
			title = TokenReplacement.replaceTokens(title, tokenReplacement);
			contents = TokenReplacement.replaceTokens(contents, tokenReplacement);
			buttons = TokenReplacement.replaceTokens(buttons, tokenReplacement);
			
			setTitle(title);
			
			Box vbox = Box.createVerticalBox();
			vbox.setComponentOrientation(UiLanguageDirection.getComponentOrientation());
			vbox.add(new JLabel(" "));
			for(int i = 0 ; i < contents.length ; ++i)
				vbox.add(createWrappedTextArea(contents[i]));
			vbox.add(new JLabel(" "));
			
			ok = new JButton(buttons[0]);
			ok.addActionListener(this);
			Box hbox = Box.createHorizontalBox();

			int numberOfButtons = buttons.length;
			JButton[] allButtons = new JButton[numberOfButtons];
			allButtons[0] = ok;
			for(int j = 1 ; j < numberOfButtons; ++j)
			{
				JButton button = new JButton(buttons[j]);
				button.addActionListener(this);
				allButtons[j] = button;
			}
			Utilities.addComponentsRespectingOrientation(hbox, allButtons);
			
			vbox.add(hbox);
			vbox.add(new JLabel(" "));
		
			JPanel panel = new JPanel();	
			panel.setComponentOrientation(UiLanguageDirection.getComponentOrientation());
			panel.setBorder(new EmptyBorder(5,5,5,5));
			panel.add(vbox);

			getContentPane().add(panel, BorderLayout.CENTER);
			
			Utilities.centerDlg(this);
			setResizable(true);
			getRootPane().setDefaultButton(ok);
			ok.requestFocus(true);
			show();
		}
		catch (TokenInvalidException e)
		{
			e.printStackTrace();
		}
	}

	private UiWrappedTextArea createWrappedTextArea(String message)
	{
		UiWrappedTextArea msgArea = new UiWrappedTextArea(message);
		msgArea.addKeyListener(new TabToOkButton());
		return msgArea;
	}

	public class TabToOkButton extends KeyAdapter
	{
		public void keyPressed(KeyEvent ke)
		{
			if (ke.getKeyCode() == KeyEvent.VK_TAB)
			{
				ok.requestFocus();
			}
		}
	}

	public void actionPerformed(ActionEvent ae)
	{
		exit();
	}

	public void exit()
	{
		if(ok.hasFocus())
			result = ok.getText();
		else
			result = "";
		dispose();
	}

	public String getResult()
	{
		return result;
	}

	String result;
	JButton ok;
}
