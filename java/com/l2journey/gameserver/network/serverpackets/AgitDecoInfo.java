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
import com.l2journey.gameserver.model.residences.AuctionableHall;
import com.l2journey.gameserver.model.residences.ClanHall;
import com.l2journey.gameserver.model.residences.ClanHall.ClanHallFunction;
import com.l2journey.gameserver.network.GameClient;
import com.l2journey.gameserver.network.ServerPackets;

/**
 * @author Steuf
 */
public class AgitDecoInfo extends ServerPacket
{
	private final AuctionableHall _clanHall;
	
	public AgitDecoInfo(AuctionableHall clanHall)
	{
		_clanHall = clanHall;
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		ServerPackets.AGIT_DECO_INFO.writeId(this, buffer);
		buffer.writeInt(_clanHall.getId());
		// Fireplace
		ClanHallFunction function = _clanHall.getFunction(ClanHall.FUNC_RESTORE_HP);
		if ((function == null) || (function.getLevel() == 0))
		{
			buffer.writeByte(0);
		}
		else if (((_clanHall.getGrade() == 0) && (function.getLevel() < 220)) || ((_clanHall.getGrade() == 1) && (function.getLevel() < 160)) || ((_clanHall.getGrade() == 2) && (function.getLevel() < 260)) || ((_clanHall.getGrade() == 3) && (function.getLevel() < 300)))
		{
			buffer.writeByte(1);
		}
		else
		{
			buffer.writeByte(2);
		}
		// Carpet - Statue
		function = _clanHall.getFunction(ClanHall.FUNC_RESTORE_MP);
		if ((function == null) || (function.getLevel() == 0))
		{
			buffer.writeByte(0);
			buffer.writeByte(0);
		}
		else if ((((_clanHall.getGrade() == 0) || (_clanHall.getGrade() == 1)) && (function.getLevel() < 25)) || ((_clanHall.getGrade() == 2) && (function.getLevel() < 30)) || ((_clanHall.getGrade() == 3) && (function.getLevel() < 40)))
		{
			buffer.writeByte(1);
			buffer.writeByte(1);
		}
		else
		{
			buffer.writeByte(2);
			buffer.writeByte(2);
		}
		// Chandelier
		function = _clanHall.getFunction(ClanHall.FUNC_RESTORE_EXP);
		if ((function == null) || (function.getLevel() == 0))
		{
			buffer.writeByte(0);
		}
		else if (((_clanHall.getGrade() == 0) && (function.getLevel() < 25)) || ((_clanHall.getGrade() == 1) && (function.getLevel() < 30)) || ((_clanHall.getGrade() == 2) && (function.getLevel() < 40)) || ((_clanHall.getGrade() == 3) && (function.getLevel() < 50)))
		{
			buffer.writeByte(1);
		}
		else
		{
			buffer.writeByte(2);
		}
		// Mirror
		function = _clanHall.getFunction(ClanHall.FUNC_TELEPORT);
		if ((function == null) || (function.getLevel() == 0))
		{
			buffer.writeByte(0);
		}
		else if (function.getLevel() < 2)
		{
			buffer.writeByte(1);
		}
		else
		{
			buffer.writeByte(2);
		}
		// Crystal
		buffer.writeByte(0);
		// Curtain
		function = _clanHall.getFunction(ClanHall.FUNC_DECO_CURTAINS);
		if ((function == null) || (function.getLevel() == 0))
		{
			buffer.writeByte(0);
		}
		else if (function.getLevel() <= 1)
		{
			buffer.writeByte(1);
		}
		else
		{
			buffer.writeByte(2);
		}
		// Magic Curtain
		function = _clanHall.getFunction(ClanHall.FUNC_ITEM_CREATE);
		if ((function == null) || (function.getLevel() == 0))
		{
			buffer.writeByte(0);
		}
		else if (((_clanHall.getGrade() == 0) && (function.getLevel() < 2)) || (function.getLevel() < 3))
		{
			buffer.writeByte(1);
		}
		else
		{
			buffer.writeByte(2);
		}
		// Support? - Flag
		function = _clanHall.getFunction(ClanHall.FUNC_SUPPORT);
		if ((function == null) || (function.getLevel() == 0))
		{
			buffer.writeByte(0);
			buffer.writeByte(0);
		}
		else if (((_clanHall.getGrade() == 0) && (function.getLevel() < 2)) || ((_clanHall.getGrade() == 1) && (function.getLevel() < 4)) || ((_clanHall.getGrade() == 2) && (function.getLevel() < 5)) || ((_clanHall.getGrade() == 3) && (function.getLevel() < 8)))
		{
			buffer.writeByte(1);
			buffer.writeByte(1);
		}
		else
		{
			buffer.writeByte(2);
			buffer.writeByte(2);
		}
		// Front platform
		function = _clanHall.getFunction(ClanHall.FUNC_DECO_FRONTPLATEFORM);
		if ((function == null) || (function.getLevel() == 0))
		{
			buffer.writeByte(0);
		}
		else if (function.getLevel() <= 1)
		{
			buffer.writeByte(1);
		}
		else
		{
			buffer.writeByte(2);
		}
		// Item create?
		function = _clanHall.getFunction(ClanHall.FUNC_ITEM_CREATE);
		if ((function == null) || (function.getLevel() == 0))
		{
			buffer.writeByte(0);
		}
		else if (((_clanHall.getGrade() == 0) && (function.getLevel() < 2)) || (function.getLevel() < 3))
		{
			buffer.writeByte(1);
		}
		else
		{
			buffer.writeByte(2);
		}
	}
}
