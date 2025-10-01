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
 * Creates pager with links << | < | > | >>
 * @author UnAfraid
 */
public class NextPrevPageHandler implements IPageHandler
{
	public static final NextPrevPageHandler INSTANCE = new NextPrevPageHandler();
	
	@Override
	public void apply(String bypass, int currentPage, int pages, StringBuilder sb, IBypassFormatter bypassFormatter, IHtmlStyle style)
	{
		// Beginning
		sb.append(style.applyBypass(bypassFormatter.formatBypass(bypass, 0), "<<", (currentPage - 1) < 0));
		
		// Separator
		sb.append(style.applySeparator());
		
		// Previous
		sb.append(style.applyBypass(bypassFormatter.formatBypass(bypass, currentPage - 1), "<", currentPage <= 0));
		sb.append(style.applySeparator());
		sb.append(String.format("<td align=\"center\">Page: %d/%d</td>", currentPage + 1, pages + 1));
		sb.append(style.applySeparator());
		
		// Next
		sb.append(style.applyBypass(bypassFormatter.formatBypass(bypass, currentPage + 1), ">", currentPage >= pages));
		
		// Separator
		sb.append(style.applySeparator());
		
		// End
		sb.append(style.applyBypass(bypassFormatter.formatBypass(bypass, pages), ">>", (currentPage + 1) > pages));
	}
}