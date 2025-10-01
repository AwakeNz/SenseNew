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

import java.text.NumberFormat;
import java.util.Locale;

import com.l2journey.gameserver.data.xml.ItemData;
import com.l2journey.gameserver.model.actor.Player;
import com.l2journey.gameserver.model.item.ItemTemplate;
import com.l2journey.gameserver.model.itemcontainer.Inventory;

public class Util
{
	public static String getItemName(int itemId)
	{
		switch (itemId)
		{
			case Inventory.ITEM_ID_FAME:
				return "Fame";
			case Inventory.ITEM_ID_PC_BANG_POINTS:
				return "PC Bang point";
			case Inventory.ITEM_ID_CLAN_REPUTATION_SCORE:
				return "Clan reputation";
			default:
				ItemTemplate template = ItemData.getInstance().getTemplate(itemId);
				return template != null ? template.getName() : "Unknown item";
		}
	}
	
	public static String getItemIcon(int itemId)
	{
		final ItemTemplate template = ItemData.getInstance().getTemplate(itemId);
		return template == null ? "icon.NOIMAGE" : template.getIcon();
	}
	
	public static String formatPay(Player player, long count, int item)
	{
		if (count > 0)
		{
			return formatAdena(count) + " " + getItemName(item);
		}
		return "Free";
	}
	
	private static NumberFormat adenaFormatter = NumberFormat.getIntegerInstance(Locale.FRANCE);
	
	/**
	 * Return amount of adena formatted with " " delimiter
	 * @param amount
	 * @return String formatted adena amount
	 */
	public static String formatAdena(long amount)
	{
		return adenaFormatter.format(amount);
	}
}
