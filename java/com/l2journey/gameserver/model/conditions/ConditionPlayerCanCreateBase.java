/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2journey.gameserver.model.conditions;

import com.l2journey.gameserver.managers.CastleManager;
import com.l2journey.gameserver.managers.FortManager;
import com.l2journey.gameserver.managers.FortSiegeManager;
import com.l2journey.gameserver.managers.SiegeManager;
import com.l2journey.gameserver.model.actor.Creature;
import com.l2journey.gameserver.model.actor.Player;
import com.l2journey.gameserver.model.clan.Clan;
import com.l2journey.gameserver.model.item.ItemTemplate;
import com.l2journey.gameserver.model.siege.Castle;
import com.l2journey.gameserver.model.siege.Fort;
import com.l2journey.gameserver.model.skill.Skill;
import com.l2journey.gameserver.model.zone.ZoneId;
import com.l2journey.gameserver.network.SystemMessageId;
import com.l2journey.gameserver.network.serverpackets.SystemMessage;

/**
 * Player Can Create Base condition implementation.
 * @author Adry_85
 */
public class ConditionPlayerCanCreateBase extends Condition
{
	private final boolean _value;
	
	public ConditionPlayerCanCreateBase(boolean value)
	{
		_value = value;
	}
	
	@Override
	public boolean testImpl(Creature effector, Creature effected, Skill skill, ItemTemplate item)
	{
		if ((effector == null) || !effector.isPlayer())
		{
			return !_value;
		}
		
		final Player player = effector.asPlayer();
		boolean canCreateBase = true;
		final Clan clan = player.getClan();
		if (player.isAlikeDead() || player.isCursedWeaponEquipped() || (clan == null))
		{
			canCreateBase = false;
		}
		
		final Castle castle = CastleManager.getInstance().getCastle(player);
		final Fort fort = FortManager.getInstance().getFort(player);
		final SystemMessage sm;
		if ((castle == null) && (fort == null))
		{
			sm = new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS);
			sm.addSkillName(skill);
			player.sendPacket(sm);
			canCreateBase = false;
		}
		else if (((castle != null) && !castle.getSiege().isInProgress()) || ((fort != null) && !fort.getSiege().isInProgress()))
		{
			sm = new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS);
			sm.addSkillName(skill);
			player.sendPacket(sm);
			canCreateBase = false;
		}
		else if (((castle != null) && (castle.getSiege().getAttackerClan(clan) == null)) || ((fort != null) && (fort.getSiege().getAttackerClan(clan) == null)))
		{
			sm = new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS);
			sm.addSkillName(skill);
			player.sendPacket(sm);
			canCreateBase = false;
		}
		else if (!player.isClanLeader())
		{
			sm = new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS);
			sm.addSkillName(skill);
			player.sendPacket(sm);
			canCreateBase = false;
		}
		else if (((castle != null) && (castle.getSiege().getAttackerClan(clan).getNumFlags() >= SiegeManager.getInstance().getFlagMaxCount())) || ((fort != null) && (fort.getSiege().getAttackerClan(clan).getNumFlags() >= FortSiegeManager.getInstance().getFlagMaxCount())))
		{
			sm = new SystemMessage(SystemMessageId.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS);
			sm.addSkillName(skill);
			player.sendPacket(sm);
			canCreateBase = false;
		}
		else if (!player.isInsideZone(ZoneId.HQ))
		{
			player.sendPacket(SystemMessageId.YOU_CANNOT_SET_UP_A_BASE_HERE);
			canCreateBase = false;
		}
		return _value == canCreateBase;
	}
}