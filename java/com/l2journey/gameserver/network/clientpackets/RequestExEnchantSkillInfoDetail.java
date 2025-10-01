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
package com.l2journey.gameserver.network.clientpackets;

import com.l2journey.gameserver.data.xml.EnchantSkillGroupsData;
import com.l2journey.gameserver.model.EnchantSkillLearn;
import com.l2journey.gameserver.model.actor.Player;
import com.l2journey.gameserver.network.serverpackets.ExEnchantSkillInfoDetail;

/**
 * Format (ch) ddd c: (id) 0xD0 h: (subid) 0x31 d: type d: skill id d: skill level
 * @author -Wooden-
 */
public class RequestExEnchantSkillInfoDetail extends ClientPacket
{
	private int _type;
	private int _skillId;
	private int _skillLevel;
	
	@Override
	protected void readImpl()
	{
		_type = readInt();
		_skillId = readInt();
		_skillLevel = readInt();
	}
	
	@Override
	protected void runImpl()
	{
		if ((_skillId <= 0) || (_skillLevel <= 0))
		{
			return;
		}
		
		final Player player = getPlayer();
		if (player == null)
		{
			return;
		}
		
		int reqskillLevel = -2;
		if ((_type == 0) || (_type == 1))
		{
			reqskillLevel = _skillLevel - 1; // enchant
		}
		else if (_type == 2)
		{
			reqskillLevel = _skillLevel + 1; // untrain
		}
		else if (_type == 3)
		{
			reqskillLevel = _skillLevel; // change route
		}
		
		final int playerskillLevel = player.getSkillLevel(_skillId);
		
		// does not have such skill
		if (playerskillLevel == 0)
		{
			return;
		}
		
		// if reqlevel is 100,200,.. check base skill level enchant
		if ((reqskillLevel % 100) == 0)
		{
			final EnchantSkillLearn esl = EnchantSkillGroupsData.getInstance().getSkillEnchantmentBySkillId(_skillId);
			if (esl != null)
			{
				// if player does not have min level to enchant
				if (playerskillLevel != esl.getBaseLevel())
				{
					return;
				}
			}
			// enchant data does not exist?
			else
			{
				return;
			}
		}
		// change route is different skill level but same enchant
		else if ((playerskillLevel != reqskillLevel) && (_type == 3) && ((playerskillLevel % 100) != (_skillLevel % 100)))
		{
			return;
		}
		
		// send skill enchantment detail
		player.sendPacket(new ExEnchantSkillInfoDetail(_type, _skillId, _skillLevel, player));
	}
}
