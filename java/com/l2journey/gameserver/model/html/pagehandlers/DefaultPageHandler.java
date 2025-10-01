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
package com.l2journey.gameserver.model.html.pagehandlers;

import com.l2journey.gameserver.model.html.IBypassFormatter;
import com.l2journey.gameserver.model.html.IHtmlStyle;
import com.l2journey.gameserver.model.html.IPageHandler;

/**
 * Creates pager with links 1 2 3 | 9 10 | 998 | 999
 * @author UnAfraid
 */
public class DefaultPageHandler implements IPageHandler
{
	public static final DefaultPageHandler INSTANCE = new DefaultPageHandler(2);
	protected final int _pagesOffset;
	
	public DefaultPageHandler(int pagesOffset)
	{
		_pagesOffset = pagesOffset;
	}
	
	@Override
	public void apply(String bypass, int currentPage, int pages, StringBuilder sb, IBypassFormatter bypassFormatter, IHtmlStyle style)
	{
		final int pagerStart = Math.max(currentPage - _pagesOffset, 0);
		final int pagerFinish = Math.min(currentPage + _pagesOffset + 1, pages);
		
		// Show the initial pages in case we are in the middle or at the end
		if (pagerStart > _pagesOffset)
		{
			for (int i = 0; i < _pagesOffset; i++)
			{
				sb.append(style.applyBypass(bypassFormatter.formatBypass(bypass, i), String.valueOf(i + 1), currentPage == i));
			}
			
			// Separator
			sb.append(style.applySeparator());
		}
		
		// Show current pages
		for (int i = pagerStart; i < pagerFinish; i++)
		{
			sb.append(style.applyBypass(bypassFormatter.formatBypass(bypass, i), String.valueOf(i + 1), currentPage == i));
		}
		
		// Show the last pages
		if (pages > pagerFinish)
		{
			// Separator
			sb.append(style.applySeparator());
			
			for (int i = pages - _pagesOffset; i < pages; i++)
			{
				sb.append(style.applyBypass(bypassFormatter.formatBypass(bypass, i), String.valueOf(i + 1), currentPage == i));
			}
		}
	}
}