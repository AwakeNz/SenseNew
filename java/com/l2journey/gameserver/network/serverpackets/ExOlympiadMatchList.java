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
import com.l2journey.gameserver.model.olympiad.AbstractOlympiadGame;
import com.l2journey.gameserver.model.olympiad.OlympiadGameClassed;
import com.l2journey.gameserver.model.olympiad.OlympiadGameManager;
import com.l2journey.gameserver.model.olympiad.OlympiadGameNonClassed;
import com.l2journey.gameserver.model.olympiad.OlympiadGameTask;
import com.l2journey.gameserver.model.olympiad.OlympiadGameTeams;
import com.l2journey.gameserver.network.GameClient;
import com.l2journey.gameserver.network.ServerPackets;

/**
 * @author mrTJO
 */
public class ExOlympiadMatchList extends ServerPacket
{
	private final List<OlympiadGameTask> _games = new ArrayList<>();
	
	public ExOlympiadMatchList()
	{
		OlympiadGameTask task;
		for (int i = 0; i < OlympiadGameManager.getInstance().getNumberOfStadiums(); i++)
		{
			task = OlympiadGameManager.getInstance().getOlympiadTask(i);
			if (task != null)
			{
				if (!task.isGameStarted() || task.isBattleFinished())
				{
					continue; // initial or finished state not shown
				}
				_games.add(task);
			}
		}
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		ServerPackets.EX_RECEIVE_OLYMPIAD.writeId(this, buffer);
		buffer.writeInt(0); // Type 0 = Match List, 1 = Match Result
		buffer.writeInt(_games.size());
		buffer.writeInt(0);
		for (OlympiadGameTask curGame : _games)
		{
			final AbstractOlympiadGame game = curGame.getGame();
			if (game != null)
			{
				buffer.writeInt(game.getStadiumId()); // Stadium Id (Arena 1 = 0)
				if (game instanceof OlympiadGameNonClassed)
				{
					buffer.writeInt(1);
				}
				else if (game instanceof OlympiadGameClassed)
				{
					buffer.writeInt(2);
				}
				else if (game instanceof OlympiadGameTeams)
				{
					buffer.writeInt(-1);
				}
				else
				{
					buffer.writeInt(0);
				}
				buffer.writeInt(curGame.isRunning() ? 2 : 1); // (1 = Standby, 2 = Playing)
				buffer.writeString(game.getPlayerNames()[0]); // Player 1 Name
				buffer.writeString(game.getPlayerNames()[1]); // Player 2 Name
			}
		}
	}
}
