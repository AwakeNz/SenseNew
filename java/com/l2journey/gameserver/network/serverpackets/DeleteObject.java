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
import com.l2journey.gameserver.model.WorldObject;
import com.l2journey.gameserver.network.GameClient;
import com.l2journey.gameserver.network.ServerPackets;

/**
 * Means the client will remove Collision cache, Pathfinding data and Internal footprint references
 * @author KingHanker
 */
public class DeleteObject extends ServerPacket
{
	private final int _objectId;
	
	// c2 = 0 "normal" removal;
	// c2 = 1 "complete" removal;
	private final int _c2;
	
	public DeleteObject(WorldObject obj)
	{
		_objectId = obj.getObjectId();
		_c2 = 0; // Default value
	}
	
	public DeleteObject(int objectId)
	{
		_objectId = objectId;
		_c2 = 0; // Default value
	}
	
	public DeleteObject(WorldObject obj, int c2)
	{
		_objectId = obj.getObjectId();
		_c2 = c2;
	}
	
	public DeleteObject(int objectId, int c2)
	{
		_objectId = objectId;
		_c2 = c2;
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		ServerPackets.DELETE_OBJECT.writeId(this, buffer);
		buffer.writeInt(_objectId);
		buffer.writeInt(_c2); // c2
	}
}
