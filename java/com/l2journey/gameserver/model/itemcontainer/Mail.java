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
package com.l2journey.gameserver.model.itemcontainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import com.l2journey.commons.database.DatabaseFactory;
import com.l2journey.gameserver.model.World;
import com.l2journey.gameserver.model.actor.Player;
import com.l2journey.gameserver.model.item.enums.ItemLocation;
import com.l2journey.gameserver.model.item.enums.ItemProcessType;
import com.l2journey.gameserver.model.item.instance.Item;

/**
 * @author DS
 */
public class Mail extends ItemContainer
{
	private final int _ownerId;
	private int _messageId;

	public Mail(int objectId, int messageId)
	{
		_ownerId = objectId;
		_messageId = messageId;
	}

	@Override
	public String getName()
	{
		return "Mail";
	}

	@Override
	public Player getOwner()
	{
		return null;
	}

	@Override
	public ItemLocation getBaseLocation()
	{
		return ItemLocation.MAIL;
	}

	public int getMessageId()
	{
		return _messageId;
	}

	public void setNewMessageId(int messageId)
	{
		_messageId = messageId;
		for (Item item : _items)
		{
			if (item == null)
			{
				continue;
			}
			item.setItemLocation(getBaseLocation(), messageId);
		}
		updateDatabase();
	}

	public void returnToWh(ItemContainer wh)
	{
		for (Item item : _items)
		{
			if (item == null)
			{
				continue;
			}
			if (wh != null)
			{
				transferItem(ItemProcessType.TRANSFER, item.getObjectId(), item.getCount(), wh, null, null);
			}
			else
			{
				item.setItemLocation(ItemLocation.WAREHOUSE);
			}
		}
	}

	@Override
	protected void addItem(Item item)
	{
		item.setVisualItemId(0);
		item.setItemLocation(getBaseLocation(), _messageId);
		super.addItem(item);
		item.updateDatabase(true);
	}

	@Override
	public void updateDatabase()
	{
		_items.forEach(i -> i.updateDatabase(true));
	}

	@Override
	public void restore()
	{
		try (Connection con = DatabaseFactory.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT object_id, item_id, count, enchant_level, loc, loc_data, custom_type1, custom_type2, mana_left, time, visual_item_id FROM items WHERE owner_id=? AND loc=? AND loc_data=?"))
		{
			ps.setInt(1, _ownerId);
			ps.setString(2, getBaseLocation().name());
			ps.setInt(3, _messageId);
			try (ResultSet inv = ps.executeQuery())
			{
				Item item;
				while (inv.next())
				{
					item = Item.restoreFromDb(_ownerId, inv);
					if (item == null)
					{
						continue;
					}

					World.getInstance().addObject(item);

					// If stackable item is found just add to current quantity
					if (item.isStackable() && (getItemByItemId(item.getId()) != null))
					{
						addItem(ItemProcessType.RESTORE, item, null, null);
					}
					else
					{
						addItem(item);
					}
				}
			}
		}
		catch (Exception e)
		{
			LOGGER.log(Level.WARNING, "could not restore container:", e);
		}
	}

	@Override
	public int getOwnerId()
	{
		return _ownerId;
	}

	@Override
	public void deleteMe()
	{
		_items.forEach(item ->
		{
			item.updateDatabase(true);
			item.stopAllTasks();
			World.getInstance().removeObject(item);
		});
		_items.clear();
	}
}
