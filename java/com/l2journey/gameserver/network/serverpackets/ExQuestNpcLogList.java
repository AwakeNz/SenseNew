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
import com.l2journey.gameserver.network.GameClient;
import com.l2journey.gameserver.network.ServerPackets;

/**
 * @author UnAfraid
 */
public class ExQuestNpcLogList extends ServerPacket
{
	private final int _questId;
	private final List<NpcHolder> _npcs = new ArrayList<>();
	
	public ExQuestNpcLogList(int questId)
	{
		_questId = questId;
	}
	
	public void addNpc(int npcId, int count)
	{
		_npcs.add(new NpcHolder(npcId, 0, count));
	}
	
	public void addNpc(int npcId, int unknown, int count)
	{
		_npcs.add(new NpcHolder(npcId, unknown, count));
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		ServerPackets.EX_QUEST_NPC_LOG_LIST.writeId(this, buffer);
		buffer.writeInt(_questId);
		buffer.writeByte(_npcs.size());
		for (NpcHolder holder : _npcs)
		{
			buffer.writeInt((holder.getNpcId() + 1000000));
			buffer.writeByte(holder.getUnknown());
			buffer.writeInt(holder.getCount());
		}
	}
	
	private class NpcHolder
	{
		private final int _npcId;
		private final int _unknown;
		private final int _count;
		
		public NpcHolder(int npcId, int unknown, int count)
		{
			_npcId = npcId;
			_unknown = unknown;
			_count = count;
		}
		
		public int getNpcId()
		{
			return _npcId;
		}
		
		public int getUnknown()
		{
			return _unknown;
		}
		
		public int getCount()
		{
			return _count;
		}
	}
}