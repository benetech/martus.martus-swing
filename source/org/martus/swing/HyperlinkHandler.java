/*
 * Copyright 2006, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.swing;

public interface HyperlinkHandler
{
	public void linkClicked(String linkDescription);
	public void valueChanged(String widget, String newValue);
	public void buttonPressed(String buttonName);
}
