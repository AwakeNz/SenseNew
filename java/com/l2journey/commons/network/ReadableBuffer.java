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
package com.l2journey.commons.network;

import java.nio.ByteBuffer;

import com.l2journey.commons.network.internal.SinglePacketBuffer;

/**
 * Represents a buffer that can be read in various formats.<br>
 * This interface extends {@link Buffer} and provides methods to read data as different primitives from the buffer.
 * @author JoeAlisson
 */
public interface ReadableBuffer extends Buffer
{
	/**
	 * Reads a char value from the buffer.<br>
	 * 16-bit integer (00 00)
	 * @return The char value read from the buffer.
	 */
	char readChar();
	
	/**
	 * Reads a raw byte from the buffer.
	 * @return The byte read from the buffer.
	 */
	byte readByte();
	
	/**
	 * Reads and returns an array of bytes of the specified length from the internal buffer.
	 * @param length The given length of bytes to be read.
	 * @return A byte array containing the read bytes.
	 */
	byte[] readBytes(int length);
	
	/**
	 * Reads bytes into the specified byte array.
	 * @param dst The byte array to fill with data.
	 */
	void readBytes(byte[] dst);
	
	/**
	 * Reads bytes into the specified byte array, starting at the given offset and reading up to the specified length.
	 * @param dst The byte array to fill with data.
	 * @param offset The starting offset in the array.
	 * @param length The number of bytes to read.
	 */
	void readBytes(byte[] dst, int offset, int length);
	
	/**
	 * Reads a short value from the buffer.<br>
	 * 16-bit integer (00 00)
	 * @return The short value read from the buffer.
	 */
	short readShort();
	
	/**
	 * Reads an int value from the buffer.<br>
	 * 32-bit integer (00 00 00 00)
	 * @return The int value read from the buffer.
	 */
	int readInt();
	
	/**
	 * Reads a long value from the buffer.<br>
	 * 64-bit integer (00 00 00 00 00 00 00 00)
	 * @return The long value read from the buffer.
	 */
	long readLong();
	
	/**
	 * Reads a float value from the buffer.<br>
	 * 32-bit float (00 00 00 00)
	 * @return The float value read from the buffer.
	 */
	float readFloat();
	
	/**
	 * Reads a double value from the buffer.<br>
	 * 64-bit float (00 00 00 00 00 00 00 00)
	 * @return The double value read from the buffer.
	 */
	double readDouble();
	
	/**
	 * Returns the number of remaining bytes available for reading.
	 * @return The number of remaining bytes.
	 */
	int remaining();
	
	/**
	 * Creates a new {@link ReadableBuffer} based on the given {@link ByteBuffer}.
	 * @param buffer The underlying ByteBuffer.
	 * @return A new instance of ReadableBuffer.
	 */
	static ReadableBuffer of(ByteBuffer buffer)
	{
		return new SinglePacketBuffer(buffer);
	}
}
