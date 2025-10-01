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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.l2journey.commons.network.WritableBuffer;
import com.l2journey.gameserver.managers.CastleManorManager;
import com.l2journey.gameserver.model.Seed;
import com.l2journey.gameserver.model.SeedProduction;
import com.l2journey.gameserver.network.GameClient;
import com.l2journey.gameserver.network.ServerPackets;

/**
 * @author l3x
 */
public class ExShowSeedSetting extends ServerPacket
{
	private final int _manorId;
	private final Set<Seed> _seeds;
	private final Map<Integer, SeedProduction> _current = new HashMap<>();
	private final Map<Integer, SeedProduction> _next = new HashMap<>();
	
	public ExShowSeedSetting(int manorId)
	{
		final CastleManorManager manor = CastleManorManager.getInstance();
		_manorId = manorId;
		_seeds = manor.getSeedsForCastle(_manorId);
		for (Seed s : _seeds)
		{
			// Current period
			SeedProduction sp = manor.getSeedProduct(manorId, s.getSeedId(), false);
			if (sp != null)
			{
				_current.put(s.getSeedId(), sp);
			}
			// Next period
			sp = manor.getSeedProduct(manorId, s.getSeedId(), true);
			if (sp != null)
			{
				_next.put(s.getSeedId(), sp);
			}
		}
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		ServerPackets.EX_SHOW_SEED_SETTING.writeId(this, buffer);
		buffer.writeInt(_manorId); // manor id
		buffer.writeInt(_seeds.size()); // size
		SeedProduction sp;
		for (Seed s : _seeds)
		{
			buffer.writeInt(s.getSeedId()); // seed id
			buffer.writeInt(s.getLevel()); // level
			buffer.writeByte(1);
			buffer.writeInt(s.getReward(1)); // reward 1 id
			buffer.writeByte(1);
			buffer.writeInt(s.getReward(2)); // reward 2 id
			buffer.writeInt(s.getSeedLimit()); // next sale limit
			buffer.writeInt(s.getSeedReferencePrice()); // price for castle to produce 1
			buffer.writeInt(s.getSeedMinPrice()); // min seed price
			buffer.writeInt(s.getSeedMaxPrice()); // max seed price
			// Current period
			if (_current.containsKey(s.getSeedId()))
			{
				sp = _current.get(s.getSeedId());
				buffer.writeLong(sp.getStartAmount()); // sales
				buffer.writeLong(sp.getPrice()); // price
			}
			else
			{
				buffer.writeLong(0);
				buffer.writeLong(0);
			}
			// Next period
			if (_next.containsKey(s.getSeedId()))
			{
				sp = _next.get(s.getSeedId());
				buffer.writeLong(sp.getStartAmount()); // sales
				buffer.writeLong(sp.getPrice()); // price
			}
			else
			{
				buffer.writeLong(0);
				buffer.writeLong(0);
			}
		}
		_current.clear();
		_next.clear();
	}
}