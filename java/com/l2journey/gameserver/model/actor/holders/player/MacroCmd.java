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
package com.l2journey.gameserver.model.actor.holders.player;

import com.l2journey.gameserver.model.actor.enums.player.MacroType;

/**
 * Macro Cmd DTO.
 * @author Zoey76
 */
public class MacroCmd
{
	private final int _entry;
	private final MacroType _type;
	private final int _d1; // skill_id or page for shortcuts
	private final int _d2; // shortcut
	private final String _cmd;
	
	public MacroCmd(int entry, MacroType type, int d1, int d2, String cmd)
	{
		_entry = entry;
		_type = type;
		_d1 = d1;
		_d2 = d2;
		_cmd = cmd;
	}
	
	/**
	 * Gets the entry index.
	 * @return the entry index
	 */
	public int getEntry()
	{
		return _entry;
	}
	
	/**
	 * Gets the macro type.
	 * @return the macro type
	 */
	public MacroType getType()
	{
		return _type;
	}
	
	/**
	 * Gets the skill ID, item ID, page ID, depending on the marco use.
	 * @return the first value
	 */
	public int getD1()
	{
		return _d1;
	}
	
	/**
	 * Gets the skill level, shortcut ID, depending on the marco use.
	 * @return the second value
	 */
	public int getD2()
	{
		return _d2;
	}
	
	/**
	 * Gets the command.
	 * @return the command
	 */
	public String getCmd()
	{
		return _cmd;
	}
}
