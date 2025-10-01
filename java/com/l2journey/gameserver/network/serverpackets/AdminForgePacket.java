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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.l2journey.commons.network.WritableBuffer;
import com.l2journey.gameserver.network.GameClient;

/**
 * This class is made to create packets with any format
 * @author Maktakien
 */
public class AdminForgePacket extends ServerPacket
{
	private final List<Part> _parts = new ArrayList<>();
	
	private static class Part
	{
		public byte b;
		public String str;
		
		public Part(byte bb, String string)
		{
			b = bb;
			str = string;
		}
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		for (Part p : _parts)
		{
			generate(p.b, p.str, buffer);
		}
	}
	
	/**
	 * @param type
	 * @param value
	 * @param buffer
	 * @return
	 */
	public boolean generate(byte type, String value, WritableBuffer buffer)
	{
		if ((type == 'C') || (type == 'c'))
		{
			buffer.writeByte(Integer.decode(value));
			return true;
		}
		else if ((type == 'D') || (type == 'd'))
		{
			buffer.writeInt(Integer.decode(value));
			return true;
		}
		else if ((type == 'H') || (type == 'h'))
		{
			buffer.writeShort(Integer.decode(value));
			return true;
		}
		else if ((type == 'F') || (type == 'f'))
		{
			buffer.writeDouble(Double.parseDouble(value));
			return true;
		}
		else if ((type == 'S') || (type == 's'))
		{
			buffer.writeString(value);
			return true;
		}
		else if ((type == 'B') || (type == 'b') || (type == 'X') || (type == 'x'))
		{
			buffer.writeBytes(new BigInteger(value).toByteArray());
			return true;
		}
		else if ((type == 'Q') || (type == 'q'))
		{
			buffer.writeLong(Long.decode(value));
			return true;
		}
		return false;
	}
	
	public void addPart(byte b, String string)
	{
		_parts.add(new Part(b, string));
	}
}