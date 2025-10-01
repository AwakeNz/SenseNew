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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.l2journey.Config;
import com.l2journey.gameserver.data.xml.UIData;
import com.l2journey.gameserver.model.ActionKey;
import com.l2journey.gameserver.model.actor.Player;
import com.l2journey.gameserver.network.ConnectionState;

/**
 * @author mrTJO
 */
public class RequestSaveKeyMapping extends ClientPacket
{
	private final Map<Integer, List<ActionKey>> _keyMap = new HashMap<>();
	private final Map<Integer, List<Integer>> _catMap = new HashMap<>();
	
	@Override
	protected void readImpl()
	{
		int category = 0;
		readInt(); // Unknown
		readInt(); // Unknown
		final int _tabNum = readInt();
		for (int i = 0; i < _tabNum; i++)
		{
			final int cmd1Size = readByte();
			for (int j = 0; j < cmd1Size; j++)
			{
				UIData.addCategory(_catMap, category, readByte());
			}
			category++;
			
			final int cmd2Size = readByte();
			for (int j = 0; j < cmd2Size; j++)
			{
				UIData.addCategory(_catMap, category, readByte());
			}
			category++;
			
			final int cmdSize = readInt();
			for (int j = 0; j < cmdSize; j++)
			{
				final int cmd = readInt();
				final int key = readInt();
				final int tgKey1 = readInt();
				final int tgKey2 = readInt();
				final int show = readInt();
				UIData.addKey(_keyMap, i, new ActionKey(i, cmd, key, tgKey1, tgKey2, show));
			}
		}
		readInt();
		readInt();
	}
	
	@Override
	protected void runImpl()
	{
		final Player player = getPlayer();
		if (!Config.STORE_UI_SETTINGS || (player == null) || (getClient().getConnectionState() != ConnectionState.IN_GAME))
		{
			return;
		}
		
		player.getUISettings().storeAll(_catMap, _keyMap);
	}
}
