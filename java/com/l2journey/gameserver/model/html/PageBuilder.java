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
package com.l2journey.gameserver.model.html;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import com.l2journey.gameserver.model.html.formatters.DefaultFormatter;
import com.l2journey.gameserver.model.html.pagehandlers.DefaultPageHandler;
import com.l2journey.gameserver.model.html.styles.DefaultStyle;

/**
 * @author UnAfraid
 * @param <T>
 */
public class PageBuilder<T>
{
	private final Collection<T> _elements;
	private final int _elementsPerPage;
	private final String _bypass;
	private int _currentPage = 0;
	private IPageHandler _pageHandler = DefaultPageHandler.INSTANCE;
	private IBypassFormatter _formatter = DefaultFormatter.INSTANCE;
	private IHtmlStyle _style = DefaultStyle.INSTANCE;
	private IBodyHandler<T> _bodyHandler;
	
	private PageBuilder(Collection<T> elements, int elementsPerPage, String bypass)
	{
		_elements = elements;
		_elementsPerPage = elementsPerPage;
		_bypass = bypass;
	}
	
	public PageBuilder<T> currentPage(int currentPage)
	{
		_currentPage = Math.max(currentPage, 0);
		return this;
	}
	
	public PageBuilder<T> bodyHandler(IBodyHandler<T> bodyHandler)
	{
		Objects.requireNonNull(bodyHandler, "Body Handler cannot be null!");
		_bodyHandler = bodyHandler;
		return this;
	}
	
	public PageBuilder<T> pageHandler(IPageHandler pageHandler)
	{
		Objects.requireNonNull(pageHandler, "Page Handler cannot be null!");
		_pageHandler = pageHandler;
		return this;
	}
	
	public PageBuilder<T> formatter(IBypassFormatter formatter)
	{
		Objects.requireNonNull(formatter, "Formatter cannot be null!");
		_formatter = formatter;
		return this;
	}
	
	public PageBuilder<T> style(IHtmlStyle style)
	{
		Objects.requireNonNull(style, "Style cannot be null!");
		_style = style;
		return this;
	}
	
	public PageResult build()
	{
		Objects.requireNonNull(_bodyHandler, "Body was not set!");
		
		final StringBuilder pagerTemplate = new StringBuilder();
		final int pages = (_elements.size() / _elementsPerPage) + ((_elements.size() % _elementsPerPage) > 0 ? 1 : 0);
		if (pages > 1)
		{
			_pageHandler.apply(_bypass, _currentPage, pages - 1, pagerTemplate, _formatter, _style);
		}
		
		if (_currentPage > pages)
		{
			_currentPage = pages - 1;
		}
		
		final StringBuilder sb = new StringBuilder();
		final int start = Math.max(_elementsPerPage * _currentPage, 0);
		_bodyHandler.create(_elements, pages, start, _elementsPerPage, sb);
		
		return new PageResult(pages, pagerTemplate, sb);
	}
	
	public static <T> PageBuilder<T> newBuilder(Collection<T> elements, int elementsPerPage, String bypass)
	{
		return new PageBuilder<>(elements, elementsPerPage, bypass.trim());
	}
	
	public static <T> PageBuilder<T> newBuilder(T[] elements, int elementsPerPage, String bypass)
	{
		return new PageBuilder<>(Arrays.asList(elements), elementsPerPage, bypass.trim());
	}
}
