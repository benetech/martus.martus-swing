/*
 * Copyright 2006, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */

/*
 * Copyright 2006, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.swing;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.FormView;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.ImageView;
import javax.swing.text.html.StyleSheet;

public class HtmlViewer extends UiEditorPane implements HyperlinkListener
{
	public HtmlViewer(String htmlSource, HyperlinkHandler hyperLinkHandler)
	{
		linkHandler = hyperLinkHandler;
		
		setEditable(false);
		setText(htmlSource);
		addHyperlinkListener(this);
	}
	
	public void setText(String text)
	{
		HTMLEditorKit htmlKit = new OurHtmlEditorKit(linkHandler);
		StyleSheet style = htmlKit.getStyleSheet();
		style.addRule("body {background: #ffffff;}");
		htmlKit.setStyleSheet(style);
		setEditorKit(htmlKit);

		Document doc = htmlKit.createDefaultDocument();
		setDocument(doc);
		
		super.setText(text);
		setCaretPosition(0);
	}

	public void hyperlinkUpdate(HyperlinkEvent e)
	{
		if(e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED))
		{
			String clicked = e.getDescription();
			linkHandler.linkClicked(clicked);
		}

	}
	
	class OurHtmlEditorKit extends HTMLEditorKit
	{
		public OurHtmlEditorKit(HyperlinkHandler handler)
		{
			factory = new OurHtmlViewFactory(handler);
			ourStyleSheet = new StyleSheet();
			ourStyleSheet.addStyleSheet(super.getStyleSheet());
		}
		
		public ViewFactory getViewFactory()
		{
			return factory;
		}

		public StyleSheet getStyleSheet()
		{
			return ourStyleSheet;
		}

		public void setStyleSheet(StyleSheet s)
		{
			ourStyleSheet = s;
		}

		ViewFactory factory;
		StyleSheet ourStyleSheet;
	}
	
	class OurHtmlViewFactory extends HTMLEditorKit.HTMLFactory
	{
		public OurHtmlViewFactory(HyperlinkHandler handlerToUse)
		{
			handler = handlerToUse;
		}
		
		public View create(Element elem)
		{
			if(elem.getName().equals("select"))
			{
				return new OurSelectView(elem, handler);
			}
			if(elem.getName().equals("input"))
			{
				AttributeSet attributes = elem.getAttributes();
				Object typeAttribute = attributes.getAttribute(HTML.Attribute.TYPE);
				if(typeAttribute.equals("submit"))
				{
					return new OurButtonView(elem, handler);
				}
			}
			else if(elem.getName().equals("img"))
			{
				return new OurImageView(elem);
			}
			return super.create(elem);
		}
		
		HyperlinkHandler handler;
	}
	
	class OurButtonView extends FormView
	{
		public OurButtonView(Element elem, HyperlinkHandler handlerToUse)
		{
			super(elem);
			handler = handlerToUse;
		}

		protected void submitData(String data)
		{
			String buttonName = (String)getElement().getAttributes().getAttribute(HTML.Attribute.NAME);
			handler.buttonPressed(buttonName);
		}
		
		HyperlinkHandler handler;
	}
	
	class OurSelectView extends FormView implements ItemListener
	{
		public OurSelectView(Element elem, HyperlinkHandler handlerToUse)
		{
			super(elem);
			handler = handlerToUse;
		}

		protected Component createComponent()
		{
			comboBox = (JComboBox)super.createComponent();
			comboBox.addItemListener(this);
			return comboBox;
		}

		public void itemStateChanged(ItemEvent e)
		{
			String name = (String)getElement().getAttributes().getAttribute(HTML.Attribute.NAME);
			handler.valueChanged(name, comboBox.getSelectedItem().toString());
		}
		
		HyperlinkHandler handler;
		JComboBox comboBox;
	}
	
	class OurImageView extends ImageView
	{
		public OurImageView(Element elem)
		{
			super(elem);
			name = (String)elem.getAttributes().getAttribute(HTML.Attribute.SRC);
		}

		public Image getImage()
		{
			if(image == null)
			{
				ImageIcon icon = new ImageIcon(name);
				image = icon.getImage();
			}
			return image;
		}
		
		String name;
		Image image;
	}
	
	HyperlinkHandler linkHandler;
}