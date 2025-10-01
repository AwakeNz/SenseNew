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

import java.util.List;

import com.l2journey.commons.network.WritableBuffer;
import com.l2journey.gameserver.managers.TerritoryWarManager;
import com.l2journey.gameserver.managers.TerritoryWarManager.Territory;
import com.l2journey.gameserver.model.actor.Player;
import com.l2journey.gameserver.model.clan.Clan;
import com.l2journey.gameserver.network.GameClient;
import com.l2journey.gameserver.network.ServerPackets;

/**
 * @author GodKratos
 */
public class ExShowDominionRegistry extends ServerPacket
{
	private static final int MINID = 80;
	
	private final int _castleId;
	private int _clanReq = 0;
	private int _mercReq = 0;
	private boolean _isMercRegistered = false;
	private boolean _isClanRegistered = false;
	private int _warTime = (int) (System.currentTimeMillis() / 1000);
	private final int _currentTime = (int) (System.currentTimeMillis() / 1000);
	
	public ExShowDominionRegistry(int castleId, Player player)
	{
		_castleId = castleId;
		if (TerritoryWarManager.getInstance().getRegisteredClans(castleId) != null)
		{
			_clanReq = TerritoryWarManager.getInstance().getRegisteredClans(castleId).size();
			
			final Clan clan = player.getClan();
			if (clan != null)
			{
				_isClanRegistered = (TerritoryWarManager.getInstance().getRegisteredClans(castleId).contains(clan));
			}
		}
		if (TerritoryWarManager.getInstance().getRegisteredMercenaries(castleId) != null)
		{
			_mercReq = TerritoryWarManager.getInstance().getRegisteredMercenaries(castleId).size();
			_isMercRegistered = (TerritoryWarManager.getInstance().getRegisteredMercenaries(castleId).contains(player.getObjectId()));
		}
		_warTime = (int) (TerritoryWarManager.getInstance().getTWStartTimeInMillis() / 1000);
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		ServerPackets.EX_SHOW_DOMINION_REGISTRY.writeId(this, buffer);
		buffer.writeInt(MINID + _castleId); // Current Territory Id
		if (TerritoryWarManager.getInstance().getTerritory(_castleId) == null)
		{
			// something is wrong
			buffer.writeString("No Owner"); // Owners Clan
			buffer.writeString("No Owner"); // Owner Clan Leader
			buffer.writeString("No Ally"); // Owner Alliance
		}
		else
		{
			final Clan clan = TerritoryWarManager.getInstance().getTerritory(_castleId).getOwnerClan();
			if (clan == null)
			{
				// something is wrong
				buffer.writeString("No Owner"); // Owners Clan
				buffer.writeString("No Owner"); // Owner Clan Leader
				buffer.writeString("No Ally"); // Owner Alliance
			}
			else
			{
				buffer.writeString(clan.getName()); // Owners Clan
				buffer.writeString(clan.getLeaderName()); // Owner Clan Leader
				buffer.writeString(clan.getAllyName()); // Owner Alliance
			}
		}
		buffer.writeInt(_clanReq); // Clan Request
		buffer.writeInt(_mercReq); // Merc Request
		buffer.writeInt(_warTime); // War Time
		buffer.writeInt(_currentTime); // Current Time
		buffer.writeInt(_isClanRegistered); // is Cancel clan registration
		buffer.writeInt(_isMercRegistered); // is Cancel mercenaries registration
		buffer.writeInt(1); // unknown
		final List<Territory> territoryList = TerritoryWarManager.getInstance().getAllTerritories();
		buffer.writeInt(territoryList.size()); // Territory Count
		for (Territory t : territoryList)
		{
			buffer.writeInt(t.getTerritoryId()); // Territory Id
			buffer.writeInt(t.getOwnedWardIds().size()); // Emblem Count
			for (int i : t.getOwnedWardIds())
			{
				buffer.writeInt(i); // Emblem ID - should be in for loop for emblem count
			}
		}
	}
}
