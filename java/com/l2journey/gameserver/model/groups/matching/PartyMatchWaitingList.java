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
package com.l2journey.gameserver.model.groups.matching;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import com.l2journey.gameserver.model.actor.Player;

/**
 * @author Gnacik
 */
public class PartyMatchWaitingList
{
	private final Collection<Player> _members;
	
	protected PartyMatchWaitingList()
	{
		_members = ConcurrentHashMap.newKeySet();
	}
	
	public void addPlayer(Player player)
	{
		// player.setPartyWait(1);
		if (!_members.contains(player))
		{
			_members.add(player);
		}
	}
	
	public void removePlayer(Player player)
	{
		// player.setPartyWait(0);
		if (_members.contains(player))
		{
			_members.remove(player);
		}
	}
	
	public Collection<Player> getPlayers()
	{
		return _members;
	}
	
	public static PartyMatchWaitingList getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private static class SingletonHolder
	{
		protected static final PartyMatchWaitingList INSTANCE = new PartyMatchWaitingList();
	}
}