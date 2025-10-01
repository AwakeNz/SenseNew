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

import java.util.ArrayList;
import java.util.List;

import com.l2journey.commons.network.WritableBuffer;
import com.l2journey.gameserver.managers.CastleManorManager;
import com.l2journey.gameserver.model.SeedProduction;
import com.l2journey.gameserver.network.GameClient;
import com.l2journey.gameserver.network.ServerPackets;

/**
 * @author l3x
 */
public class BuyListSeed extends ServerPacket
{
	private final int _manorId;
	private final long _money;
	private final List<SeedProduction> _list = new ArrayList<>();
	
	public BuyListSeed(long currentMoney, int castleId)
	{
		_money = currentMoney;
		_manorId = castleId;
		for (SeedProduction s : CastleManorManager.getInstance().getSeedProduction(castleId, false))
		{
			if ((s.getAmount() > 0) && (s.getPrice() > 0))
			{
				_list.add(s);
			}
		}
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		ServerPackets.BUY_LIST_SEED.writeId(this, buffer);
		buffer.writeLong(_money); // current money
		buffer.writeInt(_manorId); // manor id
		if (!_list.isEmpty())
		{
			buffer.writeShort(_list.size()); // list length
			for (SeedProduction s : _list)
			{
				buffer.writeInt(s.getId());
				buffer.writeInt(s.getId());
				buffer.writeInt(0);
				buffer.writeLong(s.getAmount()); // item count
				buffer.writeShort(5); // Custom Type 2
				buffer.writeShort(0); // Custom Type 1
				buffer.writeShort(0); // Equipped
				buffer.writeInt(0); // Body Part
				buffer.writeShort(0); // Enchant
				buffer.writeShort(0); // Custom Type
				buffer.writeInt(0); // Augment
				buffer.writeInt(-1); // Mana
				buffer.writeInt(-9999); // Time
				buffer.writeShort(0); // Element Type
				buffer.writeShort(0); // Element Power
				for (byte i = 0; i < 6; i++)
				{
					buffer.writeShort(0);
				}
				// Enchant Effects
				buffer.writeShort(0);
				buffer.writeShort(0);
				buffer.writeShort(0);
				buffer.writeLong(s.getPrice()); // price
			}
			_list.clear();
		}
		else
		{
			buffer.writeShort(0);
		}
	}
}