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
import com.l2journey.gameserver.network.GameClient;
import com.l2journey.gameserver.network.ServerPackets;

/**
 * @author KenM
 */
public class ExCaptureOrc extends ServerPacket
{
	private static final byte[] _test =
	//@formatter:off
	{
		(byte) 0xE4 ,(byte) 0xAB ,(byte) 0x8E ,(byte) 0xC5 ,(byte) 0xE9 ,(byte) 0xF9 ,(byte) 0x86 ,(byte) 0x7B,
		(byte) 0x9E ,(byte) 0x5D ,(byte) 0x83 ,(byte) 0x14 ,(byte) 0x05 ,(byte) 0xD4 ,(byte) 0x48 ,(byte) 0x01,
		(byte) 0xCD ,(byte) 0xA2 ,(byte) 0x8D ,(byte) 0x90 ,(byte) 0x62 ,(byte) 0x8C ,(byte) 0xDA ,(byte) 0x32,
		(byte) 0x7B ,(byte) 0x1B ,(byte) 0x87 ,(byte) 0x6D ,(byte) 0x08 ,(byte) 0xC4 ,(byte) 0xE1 ,(byte) 0x56,
		(byte) 0x9B ,(byte) 0x3B ,(byte) 0xC3 ,(byte) 0x40 ,(byte) 0xDF ,(byte) 0xE8 ,(byte) 0xD7 ,(byte) 0xE1,
		(byte) 0x98 ,(byte) 0x38 ,(byte) 0x1C ,(byte) 0xA5 ,(byte) 0x8E ,(byte) 0x45 ,(byte) 0x3F ,(byte) 0xF2,
		(byte) 0x5E ,(byte) 0x1C ,(byte) 0x59 ,(byte) 0x8E ,(byte) 0x74 ,(byte) 0x01 ,(byte) 0x9E ,(byte) 0xC2,
		(byte) 0x00 ,(byte) 0x95 ,(byte) 0xB0 ,(byte) 0x1D ,(byte) 0x87 ,(byte) 0xED ,(byte) 0x9C ,(byte) 0x8A
	};
	//@formatter:on
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		ServerPackets.EX_SEARCH_ORC.writeId(this, buffer);
		buffer.writeBytes(_test);
	}
}
