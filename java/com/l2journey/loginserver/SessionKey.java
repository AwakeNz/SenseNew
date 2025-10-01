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
package com.l2journey.loginserver;

import com.l2journey.Config;

/**
 * This class is used to represent session keys used by the client to authenticate in the gameserver<br>
 * A SessionKey is made up of two 8 bytes keys. One is send in the {@link com.l2journey.loginserver.network.serverpackets.LoginOk#LoginOk} packet and the other is sent in {@link com.l2journey.loginserver.network.serverpackets.PlayOk#PlayOk}
 * @author -Wooden-
 */
public class SessionKey
{
	public int playOkID1;
	public int playOkID2;
	public int loginOkID1;
	public int loginOkID2;
	
	public SessionKey(int loginOK1, int loginOK2, int playOK1, int playOK2)
	{
		playOkID1 = playOK1;
		playOkID2 = playOK2;
		loginOkID1 = loginOK1;
		loginOkID2 = loginOK2;
	}
	
	@Override
	public String toString()
	{
		return "PlayOk: " + playOkID1 + " " + playOkID2 + " LoginOk:" + loginOkID1 + " " + loginOkID2;
	}
	
	public boolean checkLoginPair(int loginOk1, int loginOk2)
	{
		return (loginOkID1 == loginOk1) && (loginOkID2 == loginOk2);
	}
	
	/**
	 * Only checks the PlayOk part of the session key if server doesn't show the license when player logs in.
	 * @param object the SessionKey object
	 * @return true if keys are equal.
	 */
	@Override
	public boolean equals(Object object)
	{
		if (this == object)
		{
			return true;
		}
		
		if (!(object instanceof SessionKey))
		{
			return false;
		}
		
		// When server doesn't show license it doesn't send the LoginOk packet, client doesn't have this part of the key then.
		final SessionKey key = (SessionKey) object;
		if (Config.SHOW_LICENCE)
		{
			return (playOkID1 == key.playOkID1) && (loginOkID1 == key.loginOkID1) && (playOkID2 == key.playOkID2) && (loginOkID2 == key.loginOkID2);
		}
		return (playOkID1 == key.playOkID1) && (playOkID2 == key.playOkID2);
	}
}