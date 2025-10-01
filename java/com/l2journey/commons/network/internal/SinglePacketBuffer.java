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
package com.l2journey.commons.network.internal;

import java.nio.ByteBuffer;

import com.l2journey.commons.network.ReadableBuffer;

/**
 * A wrapper around {@link ByteBuffer} that implements {@link ReadableBuffer},<br>
 * providing methods to read different types of data from the underlying byte buffer.
 * @author JoeAlisson
 */
public class SinglePacketBuffer implements ReadableBuffer
{
	private final ByteBuffer _buffer;
	
	public SinglePacketBuffer(ByteBuffer buffer)
	{
		_buffer = buffer;
	}
	
	@Override
	public char readChar()
	{
		return _buffer.getChar();
	}
	
	@Override
	public byte readByte()
	{
		return _buffer.get();
	}
	
	@Override
	public byte readByte(int index)
	{
		return _buffer.get(index);
	}
	
	@Override
	public byte[] readBytes(int length)
	{
		final byte[] result = new byte[length];
		_buffer.get(result);
		return result;
	}
	
	@Override
	public void readBytes(byte[] dst)
	{
		_buffer.get(dst);
	}
	
	@Override
	public void readBytes(byte[] dst, int offset, int length)
	{
		_buffer.get(dst, offset, length);
	}
	
	@Override
	public short readShort()
	{
		return _buffer.getShort();
	}
	
	@Override
	public short readShort(int index)
	{
		return _buffer.getShort(index);
	}
	
	@Override
	public int readInt()
	{
		return _buffer.getInt();
	}
	
	@Override
	public int readInt(int index)
	{
		return _buffer.getInt(index);
	}
	
	@Override
	public float readFloat()
	{
		return _buffer.getFloat();
	}
	
	@Override
	public long readLong()
	{
		return _buffer.getLong();
	}
	
	@Override
	public double readDouble()
	{
		return _buffer.getDouble();
	}
	
	@Override
	public void writeByte(int index, byte value)
	{
		_buffer.put(index, value);
	}
	
	@Override
	public void writeShort(int index, short value)
	{
		_buffer.putShort(index, value);
	}
	
	@Override
	public void writeInt(int index, int value)
	{
		_buffer.putInt(index, value);
	}
	
	@Override
	public int limit()
	{
		return _buffer.limit();
	}
	
	@Override
	public void limit(int newLimit)
	{
		_buffer.limit(newLimit);
	}
	
	@Override
	public int remaining()
	{
		return _buffer.remaining();
	}
}
