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
package com.l2journey.loginserver.network;

import java.io.IOException;

import com.l2journey.commons.network.Buffer;
import com.l2journey.commons.util.Rnd;
import com.l2journey.loginserver.crypt.NewCrypt;

/**
 * @author KenM
 */
public class LoginEncryption
{
	private static final byte[] STATIC_BLOWFISH_KEY =
	{
		(byte) 0x6b,
		(byte) 0x60,
		(byte) 0xcb,
		(byte) 0x5b,
		(byte) 0x82,
		(byte) 0xce,
		(byte) 0x90,
		(byte) 0xb1,
		(byte) 0xcc,
		(byte) 0x2b,
		(byte) 0x6c,
		(byte) 0x55,
		(byte) 0x6c,
		(byte) 0x6c,
		(byte) 0x6c,
		(byte) 0x6c
	};
	
	private static final NewCrypt _STATIC_CRYPT = new NewCrypt(STATIC_BLOWFISH_KEY);
	
	private NewCrypt _crypt = null;
	private boolean _static = true;
	
	public void setKey(byte[] key)
	{
		_crypt = new NewCrypt(key);
	}
	
	public boolean decrypt(Buffer data, final int offset, final int size) throws IOException
	{
		_crypt.decrypt(data, offset, size);
		return NewCrypt.verifyChecksum(data, offset, size);
	}
	
	public int encryptedSize(int dataSize)
	{
		dataSize += _static ? 8 : 4;
		dataSize += 8 - (dataSize % 8);
		dataSize += 8;
		return dataSize;
	}
	
	public boolean encrypt(Buffer data, final int offset, int size) throws IOException
	{
		final int encryptedSize = offset + encryptedSize(size);
		data.limit(encryptedSize);
		if (_static)
		{
			NewCrypt.encXORPass(data, offset, encryptedSize, Rnd.nextInt());
			_STATIC_CRYPT.crypt(data, offset, encryptedSize);
			_static = false;
		}
		else
		{
			NewCrypt.appendChecksum(data, offset, encryptedSize);
			_crypt.crypt(data, offset, encryptedSize);
		}
		return true;
	}
}
