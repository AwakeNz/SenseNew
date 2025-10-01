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

/**
 * Represents a buffer for reading and writing different data types.<br>
 * This interface provides methods to read and write primitive data types<br>
 * like byte, short, and int at specific indices within the buffer.<br>
 * Additionally, methods to get and set the buffer's limit are provided.<br>
 * @author JoeAlisson
 */
public interface Buffer
{
	/**
	 * Reads a byte value from the buffer at the specified index.
	 * @param index The index from where the byte should be read.
	 * @return The byte value at the specified index.
	 */
	byte readByte(int index);
	
	/**
	 * Writes a byte value to the buffer at the specified index.
	 * @param index The index at which the byte value should be written.
	 * @param value The byte value to be written.
	 */
	void writeByte(int index, byte value);
	
	/**
	 * Reads a short value (16-bit integer) from the buffer at the specified index.
	 * @param index The index from where the short should be read.
	 * @return The short value at the specified index.
	 */
	short readShort(int index);
	
	/**
	 * Writes a short value (16-bit integer) to the buffer at the specified index.
	 * @param index The index at which the short value should be written.
	 * @param value The short value to be written.
	 */
	void writeShort(int index, short value);
	
	/**
	 * Reads an int value (32-bit integer) from the buffer at the specified index.
	 * @param index The index from where the int should be read.
	 * @return The int value at the specified index.
	 */
	int readInt(int index);
	
	/**
	 * Writes an int value (32-bit integer) to the buffer at the specified index.
	 * @param index The index at which the int value should be written.
	 * @param value The int value to be written.
	 */
	void writeInt(int index, int value);
	
	/**
	 * Retrieves the current limit of the buffer.
	 * @return The buffer's current limit.
	 */
	int limit();
	
	/**
	 * Sets a new limit for the buffer.
	 * @param newLimit The new limit to be set for the buffer.
	 */
	void limit(int newLimit);
}
