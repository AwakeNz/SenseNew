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
package com.l2journey.gameserver.network.serverpackets;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.l2journey.commons.network.WritableBuffer;
import com.l2journey.gameserver.model.ItemInfo;
import com.l2journey.gameserver.model.item.instance.Item;

/**
 * @author UnAfraid
 */
public abstract class AbstractInventoryUpdate extends AbstractItemPacket
{
	private final Map<Integer, ItemInfo> _items = new HashMap<>();
	
	public AbstractInventoryUpdate()
	{
	}
	
	public AbstractInventoryUpdate(Item item)
	{
		addItem(item);
	}
	
	public AbstractInventoryUpdate(List<ItemInfo> items)
	{
		synchronized (_items)
		{
			for (ItemInfo item : items)
			{
				_items.put(item.getObjectId(), item);
			}
		}
	}
	
	public void addItem(Item item)
	{
		synchronized (_items)
		{
			_items.put(item.getObjectId(), new ItemInfo(item));
		}
	}
	
	public void addNewItem(Item item)
	{
		synchronized (_items)
		{
			_items.put(item.getObjectId(), new ItemInfo(item, 1));
		}
	}
	
	public void addModifiedItem(Item item)
	{
		synchronized (_items)
		{
			_items.put(item.getObjectId(), new ItemInfo(item, 2));
		}
	}
	
	public void addRemovedItem(Item item)
	{
		synchronized (_items)
		{
			_items.put(item.getObjectId(), new ItemInfo(item, 3));
		}
	}
	
	public void addItems(Collection<Item> items)
	{
		synchronized (_items)
		{
			for (Item item : items)
			{
				_items.put(item.getObjectId(), new ItemInfo(item));
			}
		}
	}
	
	public void putAll(Map<Integer, ItemInfo> items)
	{
		synchronized (_items)
		{
			_items.putAll(items);
		}
	}
	
	public Map<Integer, ItemInfo> getItemEntries()
	{
		return _items;
	}
	
	public Collection<ItemInfo> getItems()
	{
		return _items.values();
	}
	
	protected void writeItems(WritableBuffer buffer)
	{
		synchronized (_items)
		{
			buffer.writeShort(_items.size());
			for (ItemInfo item : _items.values())
			{
				buffer.writeShort(item.getChange()); // Update type : 01-add, 02-modify, 03-remove
				writeItem(item, buffer);
			}
			
			_items.clear();
		}
	}
}
