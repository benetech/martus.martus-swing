/*
 * This file was downloaded on 2006-07-26 from:
 *   http://www.developerdotstar.com/community/node/124
 * Based on the text on that page, the author (Rob MacGrogan) 
 * clearly expects and permits this code to be reused without any limitations.
 * I found this email address: robertmacgrogan@yahoo.com
 *
 * MacGrogan says the code was based on source from two other sources:
 * 1. The tutorial at www.apl.jhu.edu (still available on 2006-07-26), 
 * which has this copyright:
 *   (C) 1999 Marty Hall. All source code freely available for unrestricted use.
 *
 * 2. An anonymous forum post (the domain he lists is not valid)
 *
 * There is little enough code here that it could be re-invented from scratch
 * pretty easily, if necessary. 
 * 
 * Here is the copyright comment as it appears at developerdotstar:
 * ------------------------------------------------------------------------------------
 * Copied from this tutorial:
 *
 * http://www.apl.jhu.edu/~hall/java/Swing-Tutorial/Swing-Tutorial-Printing.html
 *
 * And also from a post on the forums at java.swing.com. My apologies that do not have
 * a link to that post, by my hat goes off to the poster because he/she figured out the
 * sticky problem of paging properly when printing a Swing component.
 */

package org.martus.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.swing.RepaintManager;

public class PrintUtilities implements Printable
{
	private Component componentToBePrinted;
	
	public static void printComponent(Component c)
	{
		new PrintUtilities(c).print();
	}
	
	public PrintUtilities(Component componentToBePrinted)
	{
		this.componentToBePrinted = componentToBePrinted;
	}
	
	public void print()
	{
		PrinterJob printJob = PrinterJob.getPrinterJob();
		printJob.setPrintable(this);
		HashPrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
		if (printJob.printDialog(attributes))
			try
			{
//				System.out.println("Calling PrintJob.print()");
				printJob.print(attributes);
//				System.out.println("End PrintJob.print()");
			} catch (PrinterException pe)
			{
				System.out.println("Error printing: " + pe);
			}
	}
	
	public int print(Graphics g, PageFormat pf, int pageIndex)
	{
		int response = NO_SUCH_PAGE;
		Graphics2D g2 = (Graphics2D) g;
		// for faster printing, turn off double buffering
		disableDoubleBuffering(componentToBePrinted);
		Dimension d = componentToBePrinted.getSize(); // get size of document
		double panelWidth = d.width; // width in pixels
		double panelHeight = d.height; // height in pixels
		double pageHeight = pf.getImageableHeight(); // height of printer
														// page
		double pageWidth = pf.getImageableWidth(); // width of printer page
		double scale = pageWidth / panelWidth;
		int totalNumPages = (int) Math.ceil(scale * panelHeight / pageHeight);
		// make sure not print empty pages
		if (pageIndex >= totalNumPages)
		{
			response = NO_SUCH_PAGE;
		} else
		{
			// shift Graphic to line up with beginning of print-imageable region
			g2.translate(pf.getImageableX(), pf.getImageableY());
			// shift Graphic to line up with beginning of next page to print
			g2.translate(0f, -pageIndex * pageHeight);
			// scale the page so the width fits...
			g2.scale(scale, scale);
			componentToBePrinted.paint(g2); // repaint the page for printing
			enableDoubleBuffering(componentToBePrinted);
			response = Printable.PAGE_EXISTS;
		}
		return response;
	}
	
	public static void disableDoubleBuffering(Component c)
	{
		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(false);
	}
	
	public static void enableDoubleBuffering(Component c)
	{
		RepaintManager currentManager = RepaintManager.currentManager(c);
		currentManager.setDoubleBufferingEnabled(true);
	}
}
