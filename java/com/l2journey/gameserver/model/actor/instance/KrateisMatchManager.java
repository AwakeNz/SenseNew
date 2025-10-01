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
package com.l2journey.gameserver.model.actor.instance;

import com.l2journey.commons.util.Rnd;
import com.l2journey.gameserver.managers.games.KrateisCubeManager;
import com.l2journey.gameserver.model.actor.Player;
import com.l2journey.gameserver.model.actor.Summon;
import com.l2journey.gameserver.model.actor.templates.NpcTemplate;
import com.l2journey.gameserver.model.krateisCube.KrateiArena;
import com.l2journey.gameserver.network.SystemMessageId;
import com.l2journey.gameserver.network.serverpackets.ExPVPMatchCCRetire;

/**
 * @author LordWinter
 */
public class KrateisMatchManager extends Folk
{
	private int _arenaId = 0;
	
	public KrateisMatchManager(NpcTemplate template)
	{
		super(template);
	}
	
	@Override
	public void onBypassFeedback(Player player, String command)
	{
		final KrateiArena arena = KrateisCubeManager.getInstance().getArenaId(getArenaId());
		if (command.startsWith("TeleToArena"))
		{
			if ((arena != null) && arena.isRegisterPlayer(player))
			{
				if (arena.isActiveNow())
				{
					player.teleToLocation(arena.getBattleLoc().get(Rnd.get(arena.getBattleLoc().size())), true);
					arena.addEffects(player);
				}
				else
				{
					showChatWindow(player, "data/html/krateisCube/" + getId() + "-01.htm");
				}
			}
			else
			{
				player.sendPacket(SystemMessageId.YOU_CANNOT_ENTER_BECAUSE_YOU_DO_NOT_MEET_THE_REQUIREMENTS);
				player.teleToLocation(-70381, -70937, -1428, 0, true);
			}
		}
		else if (command.startsWith("TeleFromArena"))
		{
			if ((arena != null) && arena.isRegisterPlayer(player))
			{
				arena.removePlayer(player);
				player.stopAllEffects();
				player.sendPacket(ExPVPMatchCCRetire.STATIC);
				player.broadcastStatusUpdate();
				player.broadcastUserInfo();
				final Summon pet = player.getSummon();
				if (pet != null)
				{
					pet.stopAllEffects();
				}
			}
			player.teleToLocation(-70381, -70937, -1428, 0, true);
		}
		else
		{
			super.onBypassFeedback(player, command);
		}
	}
	
	@Override
	public String getHtmlPath(int npcId, int val)
	{
		String pom = "";
		
		if (val == 0)
		{
			pom = "" + npcId;
		}
		else
		{
			pom = npcId + "-0" + val;
		}
		
		return "data/html/krateisCube/" + pom + ".htm";
	}
	
	public void setArenaId(int id)
	{
		_arenaId = id;
	}
	
	public int getArenaId()
	{
		return _arenaId;
	}
}
