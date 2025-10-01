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
package com.l2journey.gameserver.model.groups;

/**
 * @author NosBit
 */
public enum PartyDistributionType
{
	FINDERS_KEEPERS(0, 487),
	RANDOM(1, 488),
	RANDOM_INCLUDING_SPOIL(2, 798),
	BY_TURN(3, 799),
	BY_TURN_INCLUDING_SPOIL(4, 800);
	
	private final int _id;
	private final int _sysStringId;
	
	/**
	 * Constructs a party distribution type.
	 * @param id the id used by packets.
	 * @param sysStringId the sysstring id
	 */
	private PartyDistributionType(int id, int sysStringId)
	{
		_id = id;
		_sysStringId = sysStringId;
	}
	
	/**
	 * Gets the id used by packets.
	 * @return the id
	 */
	public int getId()
	{
		return _id;
	}
	
	/**
	 * Gets the sysstring id used by system messages.
	 * @return the sysstring-e id
	 */
	public int getSysStringId()
	{
		return _sysStringId;
	}
	
	/**
	 * Finds the {@code PartyDistributionType} by its id
	 * @param id the id
	 * @return the {@code PartyDistributionType} if it is found, {@code null} otherwise.
	 */
	public static PartyDistributionType findById(int id)
	{
		for (PartyDistributionType partyDistributionType : values())
		{
			if (partyDistributionType.getId() == id)
			{
				return partyDistributionType;
			}
		}
		return null;
	}
}
