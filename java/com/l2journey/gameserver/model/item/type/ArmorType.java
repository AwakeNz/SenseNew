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
package com.l2journey.gameserver.model.item.type;

/**
 * Armor Type enumerated.
 */
public enum ArmorType implements ItemType
{
	NONE("NONE"),
	LIGHT("LIGHT"),
	HEAVY("HEAVY"),
	MAGIC("ROBE"),
	SIGIL("SIGIL"),
	
	// L2J CUSTOM
	SHIELD("SHIELD");
	
	final int _mask;
	private final String _descr;
	
	/**
	 * Constructor of the ArmorType.
	 * @param descr
	 */
	private ArmorType(String descr)
	{
		_mask = 1 << (ordinal() + WeaponType.values().length);
		_descr = descr;
	}
	
	/**
	 * @return the ID of the ArmorType after applying a mask.
	 */
	@Override
	public int mask()
	{
		return _mask;
	}
	
	public String getDescription()
	{
		return _descr;
	}
}
