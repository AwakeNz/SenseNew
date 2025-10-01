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
import com.l2journey.gameserver.model.actor.Creature;
import com.l2journey.gameserver.network.GameClient;
import com.l2journey.gameserver.network.ServerPackets;

/**
 * @author -Wooden-
 */
public class ExFishingHpRegen extends ServerPacket
{
	private final Creature _creature;
	private final int _time;
	private final int _fishHP;
	private final int _hpMode;
	private final int _anim;
	private final int _goodUse;
	private final int _penalty;
	private final int _hpBarColor;
	
	public ExFishingHpRegen(Creature creature, int time, int fishHP, int hpMode, int goodUse, int anim, int penalty, int hpBarColor)
	{
		_creature = creature;
		_time = time;
		_fishHP = fishHP;
		_hpMode = hpMode;
		_goodUse = goodUse;
		_anim = anim;
		_penalty = penalty;
		_hpBarColor = hpBarColor;
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		ServerPackets.EX_FISHING_HP_REGEN.writeId(this, buffer);
		buffer.writeInt(_creature.getObjectId());
		buffer.writeInt(_time);
		buffer.writeInt(_fishHP);
		buffer.writeByte(_hpMode); // 0 = HP stop, 1 = HP raise
		buffer.writeByte(_goodUse); // 0 = none, 1 = success, 2 = failed
		buffer.writeByte(_anim); // Anim: 0 = none, 1 = reeling, 2 = pumping
		buffer.writeInt(_penalty); // Penalty
		buffer.writeByte(_hpBarColor); // 0 = normal hp bar, 1 = purple hp bar
	}
}