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
import com.l2journey.gameserver.model.actor.holders.player.MovieHolder;
import com.l2journey.gameserver.network.enums.Movie;

/**
 * @author JIV
 */
public class EndScenePlayer extends ClientPacket
{
	private int _movieId;
	
	@Override
	protected void readImpl()
	{
		_movieId = readInt();
	}
	
	@Override
	protected void runImpl()
	{
		final Player player = getPlayer();
		if ((player == null) || (_movieId == 0))
		{
			return;
		}
		final MovieHolder movieHolder = player.getMovieHolder();
		if (movieHolder == null)
		{
			return;
		}
		final Movie movie = movieHolder.getMovie();
		if (movie.getClientId() != _movieId)
		{
			return;
		}
		player.stopMovie();
		player.setTeleporting(true, false); // avoid to get player removed from World
		player.decayMe();
		player.spawnMe(player.getX(), player.getY(), player.getZ());
		player.setTeleporting(false, false);
	}
}
