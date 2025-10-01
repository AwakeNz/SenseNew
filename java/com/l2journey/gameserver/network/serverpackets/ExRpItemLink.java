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

import com.l2journey.commons.network.WritableBuffer;
import com.l2journey.gameserver.model.item.instance.Item;
import com.l2journey.gameserver.network.GameClient;
import com.l2journey.gameserver.network.ServerPackets;

/**
 * @author KenM
 */
public class ExRpItemLink extends ServerPacket
{
	private final Item _item;
	
	public ExRpItemLink(Item item)
	{
		_item = item;
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		ServerPackets.EX_RP_ITEM_LINK.writeId(this, buffer);
		buffer.writeInt(_item.getObjectId());
		buffer.writeInt(_item.getDisplayId());
		buffer.writeInt(_item.getLocationSlot());
		buffer.writeLong(_item.getCount());
		buffer.writeShort(_item.getTemplate().getType2());
		buffer.writeShort(_item.getCustomType1());
		buffer.writeShort(_item.isEquipped());
		buffer.writeInt(_item.getTemplate().getBodyPart());
		buffer.writeShort(_item.getEnchantLevel());
		buffer.writeShort(_item.getCustomType2());
		if (_item.isAugmented())
		{
			buffer.writeInt(_item.getAugmentation().getAugmentationId());
		}
		else
		{
			buffer.writeInt(0);
		}
		buffer.writeInt(_item.getMana());
		buffer.writeInt(_item.isTimeLimitedItem() ? (int) (_item.getRemainingTime() / 1000) : -9999);
		buffer.writeShort(_item.getAttackElementType());
		buffer.writeShort(_item.getAttackElementPower());
		for (byte i = 0; i < 6; i++)
		{
			buffer.writeShort(_item.getElementDefAttr(i));
		}
		// Enchant Effects
		for (int op : _item.getEnchantOptions())
		{
			buffer.writeShort(op);
		}
	}
}
