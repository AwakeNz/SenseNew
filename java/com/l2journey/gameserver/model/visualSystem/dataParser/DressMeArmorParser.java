/*
 * Copyright (c) 2025 L2Journey Project
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 * ---
 * 
 * Portions of this software are derived from the L2JMobius Project, 
 * shared under the MIT License. The original license terms are preserved where 
 * applicable..
 * 
 */
package com.l2journey.gameserver.model.visualSystem.dataParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.l2journey.Config;
import com.l2journey.gameserver.model.visualSystem.data.DressMeArmorData;
import com.l2journey.gameserver.model.visualSystem.dataHolder.DressMeArmorHolder;

public final class DressMeArmorParser extends AbstractFileParser<DressMeArmorHolder>
{
	private static final Logger LOGGER = Logger.getLogger(DressMeArmorParser.class.getName());
	
	private final String ARMOR_FILE_PATH = Config.DATAPACK_ROOT + "/data/dressme/armor.xml";
	
	private static final DressMeArmorParser _instance = new DressMeArmorParser();
	
	public static DressMeArmorParser getInstance()
	{
		return _instance;
	}
	
	private DressMeArmorParser()
	{
		super(DressMeArmorHolder.getInstance());
	}
	
	@Override
	public File getXMLFile()
	{
		return new File(ARMOR_FILE_PATH);
	}
	
	@Override
	protected void readData()
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setIgnoringComments(true);
		
		File file = getXMLFile();
		
		try
		{
			InputSource in = new InputSource(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			in.setEncoding("UTF-8");
			Document doc = factory.newDocumentBuilder().parse(in);
			
			for (Node n = doc.getFirstChild(); n != null; n = n.getNextSibling())
			{
				if (n.getNodeName().equalsIgnoreCase("list"))
				{
					for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
					{
						if (d.getNodeName().equalsIgnoreCase("dress"))
						{
							int id = Integer.parseInt(d.getAttributes().getNamedItem("id").getNodeValue());
							String name = d.getAttributes().getNamedItem("name").getNodeValue();
							String type = d.getAttributes().getNamedItem("type").getNodeValue();
							
							int itemId = 0;
							long itemCount = 0;
							int chest = 0;
							int legs = 0;
							int gloves = 0;
							int feet = 0;
							
							for (Node att = d.getFirstChild(); att != null; att = att.getNextSibling())
							{
								if ("set".equalsIgnoreCase(att.getNodeName()))
								{
									chest = Integer.parseInt(att.getAttributes().getNamedItem("chest").getNodeValue());
									legs = Integer.parseInt(att.getAttributes().getNamedItem("legs").getNodeValue());
									gloves = Integer.parseInt(att.getAttributes().getNamedItem("gloves").getNodeValue());
									feet = Integer.parseInt(att.getAttributes().getNamedItem("feet").getNodeValue());
								}
								
								if ("price".equalsIgnoreCase(att.getNodeName()))
								{
									itemId = Integer.parseInt(att.getAttributes().getNamedItem("id").getNodeValue());
									itemCount = Long.parseLong(att.getAttributes().getNamedItem("count").getNodeValue());
								}
							}
							
							getHolder().addDress(new DressMeArmorData(id, name, type, chest, legs, gloves, feet, itemId, itemCount));
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			LOGGER.warning(getClass().getSimpleName() + ": Error: " + e);
		}
	}
}
