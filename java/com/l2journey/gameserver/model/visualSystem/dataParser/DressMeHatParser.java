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
import com.l2journey.gameserver.model.visualSystem.data.DressMeHatData;
import com.l2journey.gameserver.model.visualSystem.dataHolder.DressMeHatHolder;

public final class DressMeHatParser extends AbstractFileParser<DressMeHatHolder>
{
	private static final Logger LOGGER = Logger.getLogger(DressMeHatParser.class.getName());
	
	private final String CLOAK_FILE_PATH = Config.DATAPACK_ROOT + "/data/dressme/hat.xml";
	
	private static final DressMeHatParser _instance = new DressMeHatParser();
	
	public static DressMeHatParser getInstance()
	{
		return _instance;
	}
	
	private DressMeHatParser()
	{
		super(DressMeHatHolder.getInstance());
	}
	
	@Override
	public File getXMLFile()
	{
		return new File(CLOAK_FILE_PATH);
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
						if (d.getNodeName().equalsIgnoreCase("hat"))
						{
							int number = Integer.parseInt(d.getAttributes().getNamedItem("number").getNodeValue());
							int id = Integer.parseInt(d.getAttributes().getNamedItem("id").getNodeValue());
							String name = d.getAttributes().getNamedItem("name").getNodeValue();
							int slot = Integer.parseInt(d.getAttributes().getNamedItem("slot").getNodeValue());
							int itemId = 0;
							long itemCount = 0;
							
							for (Node price = d.getFirstChild(); price != null; price = price.getNextSibling())
							{
								if ("price".equalsIgnoreCase(price.getNodeName()))
								{
									itemId = Integer.parseInt(price.getAttributes().getNamedItem("id").getNodeValue());
									itemCount = Long.parseLong(price.getAttributes().getNamedItem("count").getNodeValue());
								}
							}
							
							getHolder().addHat(new DressMeHatData(number, id, name, slot, itemId, itemCount));
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
