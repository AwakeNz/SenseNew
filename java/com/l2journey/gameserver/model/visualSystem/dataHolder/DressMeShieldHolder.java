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
package com.l2journey.gameserver.model.visualSystem.dataHolder;

import java.util.ArrayList;
import java.util.List;

import com.l2journey.gameserver.model.visualSystem.data.DressMeShieldData;

public final class DressMeShieldHolder extends AbstractHolder
{
	private static final DressMeShieldHolder _instance = new DressMeShieldHolder();
	
	public static DressMeShieldHolder getInstance()
	{
		return _instance;
	}
	
	private final List<DressMeShieldData> _shield = new ArrayList<>();
	
	public void addShield(DressMeShieldData shield)
	{
		_shield.add(shield);
	}
	
	public List<DressMeShieldData> getAllShields()
	{
		return _shield;
	}
	
	public DressMeShieldData getShield(int id)
	{
		for (DressMeShieldData shield : _shield)
		{
			if (shield.getId() == id)
			{
				return shield;
			}
		}
		
		return null;
	}
	
	@Override
	public int size()
	{
		return _shield.size();
	}
	
	@Override
	public void clear()
	{
		_shield.clear();
	}
}
