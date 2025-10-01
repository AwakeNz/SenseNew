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
import java.io.IOException;
import java.util.logging.Logger;

import com.l2journey.gameserver.model.visualSystem.dataHolder.AbstractHolder;

/**
 * @author Claww
 * @author Hl4p3x
 * @param <H>
 */
public abstract class AbstractFileParser<H extends AbstractHolder>extends AbstractParser<H>
{
	protected static final Logger LOG = Logger.getLogger(AbstractFileParser.class.getName());
	
	protected AbstractFileParser(H paramH)
	{
		super(paramH);
	}
	
	public abstract File getXMLFile();
	
	@Override
	protected final void parse()
	{
		File file = getXMLFile();
		
		if (!file.exists())
		{
			LOG.warning("File " + file.getAbsolutePath() + " does not exist.");
			return;
		}
		
		try (FileInputStream fis = new FileInputStream(file))
		{
			parseDocument(fis, file.getName());
		}
		catch (IOException e)
		{
			LOG.warning("IOException while parsing file: " + e.getMessage());
		}
		catch (Exception e)
		{
			LOG.warning("Exception while parsing file: " + e);
		}
	}
}
