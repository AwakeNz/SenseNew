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
package com.l2journey.gameserver.model;

import java.awt.Polygon;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

import com.l2journey.commons.util.Rnd;

/**
 * Dimensional Rift Room.
 * @author xban1x
 */
public class DimensionalRiftRoom
{
	private final byte _type;
	private final byte _room;
	private final int _xMin;
	private final int _xMax;
	private final int _yMin;
	private final int _yMax;
	private final int _zMin;
	private final int _zMax;
	private final Location _teleportCoords;
	private final Shape _s;
	private final boolean _isBossRoom;
	private final List<Spawn> _roomSpawns = new ArrayList<>();
	private boolean _partyInside = false;
	
	public DimensionalRiftRoom(byte type, byte room, int xMin, int xMax, int yMin, int yMax, int zMin, int zMax, int xT, int yT, int zT, boolean isBossRoom)
	{
		_type = type;
		_room = room;
		_xMin = (xMin + 128);
		_xMax = (xMax - 128);
		_yMin = (yMin + 128);
		_yMax = (yMax - 128);
		_zMin = zMin;
		_zMax = zMax;
		_teleportCoords = new Location(xT, yT, zT);
		_isBossRoom = isBossRoom;
		_s = new Polygon(new int[]
		{
			xMin,
			xMax,
			xMax,
			xMin
		}, new int[]
		{
			yMin,
			yMin,
			yMax,
			yMax
		}, 4);
	}
	
	public byte getType()
	{
		return _type;
	}
	
	public byte getRoom()
	{
		return _room;
	}
	
	public int getRandomX()
	{
		return Rnd.get(_xMin, _xMax);
	}
	
	public int getRandomY()
	{
		return Rnd.get(_yMin, _yMax);
	}
	
	public Location getTeleportCoorinates()
	{
		return _teleportCoords;
	}
	
	public boolean checkIfInZone(int x, int y, int z)
	{
		return _s.contains(x, y) && (z >= _zMin) && (z <= _zMax);
	}
	
	public boolean isBossRoom()
	{
		return _isBossRoom;
	}
	
	public List<Spawn> getSpawns()
	{
		return _roomSpawns;
	}
	
	public void spawn()
	{
		for (Spawn spawn : _roomSpawns)
		{
			spawn.doSpawn(false);
			spawn.startRespawn();
		}
	}
	
	public DimensionalRiftRoom unspawn()
	{
		for (Spawn spawn : _roomSpawns)
		{
			spawn.stopRespawn();
			if (spawn.getLastSpawn() != null)
			{
				spawn.getLastSpawn().deleteMe();
			}
		}
		return this;
	}
	
	/**
	 * Returns if party is inside the room.
	 * @return {@code true} if there is a party inside, {@code false} otherwise
	 */
	public boolean isPartyInside()
	{
		return _partyInside;
	}
	
	/**
	 * Sets the party inside.
	 * @param partyInside
	 */
	public void setPartyInside(boolean partyInside)
	{
		_partyInside = partyInside;
	}
}
