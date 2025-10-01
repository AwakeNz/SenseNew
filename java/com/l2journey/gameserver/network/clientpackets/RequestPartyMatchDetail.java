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
package com.l2journey.gameserver.network.clientpackets;

import com.l2journey.gameserver.model.actor.Player;
import com.l2journey.gameserver.model.groups.matching.PartyMatchRoom;
import com.l2journey.gameserver.model.groups.matching.PartyMatchRoomList;
import com.l2journey.gameserver.model.groups.matching.PartyMatchWaitingList;
import com.l2journey.gameserver.network.SystemMessageId;
import com.l2journey.gameserver.network.serverpackets.ExManagePartyRoomMember;
import com.l2journey.gameserver.network.serverpackets.ExPartyRoomMember;
import com.l2journey.gameserver.network.serverpackets.PartyMatchDetail;
import com.l2journey.gameserver.network.serverpackets.SystemMessage;

/**
 * @author Gnacik
 */
public class RequestPartyMatchDetail extends ClientPacket
{
	private int _roomid;
	@SuppressWarnings("unused")
	private int _unk1;
	@SuppressWarnings("unused")
	private int _unk2;
	@SuppressWarnings("unused")
	private int _unk3;
	
	@Override
	protected void readImpl()
	{
		_roomid = readInt();
		// If player click on Room all unk are 0
		// If player click AutoJoin values are -1 1 1
		_unk1 = readInt();
		_unk2 = readInt();
		_unk3 = readInt();
	}
	
	@Override
	protected void runImpl()
	{
		final Player player = getPlayer();
		if (player == null)
		{
			return;
		}
		
		final PartyMatchRoom room = PartyMatchRoomList.getInstance().getRoom(_roomid);
		if (room == null)
		{
			return;
		}
		
		if ((player.getLevel() >= room.getMinLevel()) && (player.getLevel() <= room.getMaxLevel()))
		{
			// Remove from waiting list
			PartyMatchWaitingList.getInstance().removePlayer(player);
			
			player.setPartyRoom(_roomid);
			
			player.sendPacket(new PartyMatchDetail(room));
			player.sendPacket(new ExPartyRoomMember(room, 0));
			for (Player member : room.getPartyMembers())
			{
				if (member == null)
				{
					continue;
				}
				
				member.sendPacket(new ExManagePartyRoomMember(player, room, 0));
				
				final SystemMessage sm = new SystemMessage(SystemMessageId.C1_HAS_ENTERED_THE_PARTY_ROOM);
				sm.addString(player.getName());
				member.sendPacket(sm);
			}
			room.addMember(player);
			
			// Info Broadcast
			player.broadcastUserInfo();
		}
		else
		{
			player.sendPacket(SystemMessageId.YOU_DO_NOT_MEET_THE_REQUIREMENTS_TO_ENTER_THAT_PARTY_ROOM);
		}
	}
}
