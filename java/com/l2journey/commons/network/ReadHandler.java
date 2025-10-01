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
import java.nio.channels.CompletionHandler;

/**
 * Handles the completion of read operations for network clients.<br>
 * This class implements {@link CompletionHandler} to process the data read from the client, converting it into packets and executing them.
 * @param <T> The type of Client associated with this read handler.
 * @author JoeAlisson
 */
public class ReadHandler<T extends Client<Connection<T>>> implements CompletionHandler<Integer, T>
{
	private final PacketHandler<T> _packetHandler;
	private final PacketExecutor<T> _executor;
	
	/**
	 * Constructs a ReadHandler with the specified packet handler and executor.
	 * @param packetHandler The handler responsible for managing packets.
	 * @param executor The executor responsible for executing parsed packets.
	 */
	public ReadHandler(PacketHandler<T> packetHandler, PacketExecutor<T> executor)
	{
		_packetHandler = packetHandler;
		_executor = executor;
	}
	
	@Override
	public void completed(Integer bytesRead, T client)
	{
		// Exit if the client is disconnected or there was a read error.
		if (!client.isConnected())
		{
			return;
		}
		
		// Handle disconnection if no bytes were read.
		if (bytesRead < 0)
		{
			client.disconnect();
			return;
		}
		
		// If partial data is read, resume reading the remaining bytes.
		if (bytesRead < client.getExpectedReadSize())
		{
			client.resumeRead(bytesRead);
			return;
		}
		
		// Process either payload or header based on client state.
		if (client.isReadingPayload())
		{
			handlePayload(client);
		}
		else
		{
			handleHeader(client);
		}
	}
	
	private void handleHeader(T client)
	{
		final ByteBuffer buffer = client.getConnection().getReadingBuffer();
		if (buffer == null)
		{
			client.disconnect();
			return;
		}
		
		buffer.flip();
		
		// Read packet size from header and adjust buffer size accordingly.
		final int dataSize = Short.toUnsignedInt(buffer.getShort()) - ConnectionConfig.HEADER_SIZE;
		if (dataSize > 0)
		{
			client.readPayload(dataSize);
		}
		else
		{
			client.read();
		}
	}
	
	private void handlePayload(T client)
	{
		final ByteBuffer buffer = client.getConnection().getReadingBuffer();
		if (buffer == null)
		{
			client.disconnect();
			return;
		}
		
		buffer.flip();
		
		// Parse the buffer and execute the resulting packet.
		parseAndExecutePacket(client, buffer);
		client.read(); // Continue reading next data.
	}
	
	private void parseAndExecutePacket(T client, ByteBuffer incomingBuffer)
	{
		try
		{
			// Wrap the incoming buffer for readability.
			final ReadableBuffer buffer = ReadableBuffer.of(incomingBuffer);
			
			// Decrypt and process the buffer if decryption succeeds.
			if (client.decrypt(buffer, 0, buffer.remaining()))
			{
				final ReadablePacket<T> packet = _packetHandler.handlePacket(buffer, client);
				
				// If a packet is created, initialize and execute it.
				if (packet != null)
				{
					packet.init(client, buffer);
					execute(packet);
				}
			}
		}
		catch (Exception e)
		{
			// Disconnect the client.
			failed(e, client);
		}
	}
	
	private void execute(ReadablePacket<T> packet)
	{
		// Execute the packet if it was successfully read.
		if (packet.read())
		{
			_executor.execute(packet);
		}
	}
	
	@Override
	public void failed(Throwable e, T client)
	{
		// Disconnect the client on read failure.
		client.disconnect();
	}
}
