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

import java.io.InputStream;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.io.SAXReader;

import com.l2journey.gameserver.model.visualSystem.dataHolder.AbstractHolder;

/**
 * @author Claww
 * @author Hl4p3x
 * @param <H>
 */
public abstract class AbstractParser<H extends AbstractHolder>
{
	private static final Logger LOG = Logger.getLogger(AbstractParser.class.getName());
	
	protected final H _holder;
	protected String _currentFile;
	protected SAXReader _reader;
	protected DocumentBuilderFactory _factory;
	protected Encoding _encoding;
	
	protected abstract void readData();
	
	protected abstract void parse();
	
	public enum Encoding
	{
		UTF_8("UTF-8"),
		UTF_16("UTF-16");
		
		private final String _encoding;
		
		Encoding(String paramString1)
		{
			_encoding = paramString1;
		}
		
		public final String get()
		{
			return _encoding;
		}
	}
	
	protected AbstractParser(H paramH)
	{
		_holder = paramH;
		_reader = new SAXReader();
		_reader.setValidation(true);
		_encoding = Encoding.UTF_8;
		_factory = DocumentBuilderFactory.newInstance();
		_factory.setValidating(false);
		_factory.setIgnoringComments(true);
	}
	
	protected void parseDocument(InputStream paramInputStream, String paramString)
	{
		_currentFile = paramString;
		readData();
	}
	
	protected DocumentBuilderFactory getFactory()
	{
		return _factory;
	}
	
	protected Encoding getEncoding()
	{
		return _encoding;
	}
	
	protected H getHolder()
	{
		return _holder;
	}
	
	public String getCurrentFileName()
	{
		return _currentFile;
	}
	
	public void load()
	{
		parse();
		_holder.process();
		_holder.log();
	}
	
	public void reload()
	{
		LOG.info("Reload start...");
		_holder.clear();
		load();
	}
}
