/*
 * Copyright 2006, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.swing;

import javax.swing.ImageIcon;

public class ResourceImageIcon extends ImageIcon
{
	public ResourceImageIcon(String fileName)
	{
		super(ResourceImageIcon.class.getClassLoader().getResource(fileName));
	}
}
