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

import com.l2journey.Config;
import com.l2journey.gameserver.data.xml.AdminData;
import com.l2journey.gameserver.managers.PetitionManager;
import com.l2journey.gameserver.model.actor.Player;
import com.l2journey.gameserver.network.SystemMessageId;
import com.l2journey.gameserver.network.serverpackets.PlaySound;
import com.l2journey.gameserver.network.serverpackets.SystemMessage;

/**
 * <p>
 * Format: (c) Sd
 * <ul>
 * <li>S: content</li>
 * <li>d: type</li>
 * </ul>
 * </p>
 * @author -Wooden-, TempyIncursion
 */
public class RequestPetition extends ClientPacket
{
	private String _content;
	private int _type; // 1 = on : 0 = off;
	
	@Override
	protected void readImpl()
	{
		_content = readString();
		_type = readInt();
	}
	
	@Override
	protected void runImpl()
	{
		final Player player = getPlayer();
		if (player == null)
		{
			return;
		}
		
		if ((_type <= 0) || (_type >= 10))
		{
			return;
		}
		
		if (!AdminData.getInstance().isGmOnline(false))
		{
			player.sendPacket(new PlaySound("systemmsg_e.702"));
			player.sendPacket(SystemMessageId.THERE_ARE_NO_GMS_CURRENTLY_VISIBLE_IN_THE_PUBLIC_LIST_AS_THEY_MAY_BE_PERFORMING_OTHER_FUNCTIONS_AT_THE_MOMENT);
			return;
		}
		
		if (!PetitionManager.getInstance().isPetitioningAllowed())
		{
			player.sendPacket(SystemMessageId.THE_GAME_CLIENT_ENCOUNTERED_AN_ERROR_AND_WAS_UNABLE_TO_CONNECT_TO_THE_PETITION_SERVER);
			return;
		}
		
		if (PetitionManager.getInstance().isPlayerPetitionPending(player))
		{
			player.sendPacket(SystemMessageId.YOU_MAY_ONLY_SUBMIT_ONE_PETITION_ACTIVE_AT_A_TIME);
			return;
		}
		
		if (PetitionManager.getInstance().getPendingPetitionCount() == Config.MAX_PETITIONS_PENDING)
		{
			player.sendPacket(SystemMessageId.THE_PETITION_SYSTEM_IS_CURRENTLY_UNAVAILABLE_PLEASE_TRY_AGAIN_LATER);
			return;
		}
		
		final int totalPetitions = PetitionManager.getInstance().getPlayerTotalPetitionCount(player) + 1;
		if (totalPetitions > Config.MAX_PETITIONS_PER_PLAYER)
		{
			final SystemMessage sm = new SystemMessage(SystemMessageId.WE_HAVE_RECEIVED_S1_PETITIONS_FROM_YOU_TODAY_AND_THAT_IS_THE_MAXIMUM_THAT_YOU_CAN_SUBMIT_IN_ONE_DAY_YOU_CANNOT_SUBMIT_ANY_MORE_PETITIONS);
			sm.addInt(totalPetitions);
			player.sendPacket(sm);
			return;
		}
		
		if (_content.length() > 255)
		{
			player.sendPacket(SystemMessageId.PETITIONS_CANNOT_EXCEED_255_CHARACTERS);
			return;
		}
		
		final int petitionId = PetitionManager.getInstance().submitPetition(player, _content, _type);
		SystemMessage sm = new SystemMessage(SystemMessageId.YOUR_PETITION_APPLICATION_HAS_BEEN_ACCEPTED_N_RECEIPT_NO_IS_S1);
		sm.addInt(petitionId);
		player.sendPacket(sm);
		
		sm = new SystemMessage(SystemMessageId.YOU_HAVE_SUBMITTED_S1_PETITION_S_N_YOU_MAY_SUBMIT_S2_MORE_PETITION_S_TODAY);
		sm.addInt(totalPetitions);
		sm.addInt(Config.MAX_PETITIONS_PER_PLAYER - totalPetitions);
		player.sendPacket(sm);
		
		sm = new SystemMessage(SystemMessageId.THERE_ARE_S1_PETITIONS_CURRENTLY_ON_THE_WAITING_LIST);
		sm.addInt(PetitionManager.getInstance().getPendingPetitionCount());
		player.sendPacket(sm);
	}
}
