/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2journey.gameserver.model.stats.functions;

import com.l2journey.gameserver.data.xml.EnchantItemHPBonusData;
import com.l2journey.gameserver.model.actor.Creature;
import com.l2journey.gameserver.model.conditions.Condition;
import com.l2journey.gameserver.model.item.instance.Item;
import com.l2journey.gameserver.model.skill.Skill;
import com.l2journey.gameserver.model.stats.Stat;

/**
 * @author Yamaneko
 */
public class FuncEnchantHp extends AbstractFunction
{
	public FuncEnchantHp(Stat stat, int order, Object owner, double value, Condition applyCond)
	{
		super(stat, order, owner, value, applyCond);
	}
	
	@Override
	public double calc(Creature effector, Creature effected, Skill skill, double initVal)
	{
		if ((getApplyCond() != null) && !getApplyCond().test(effector, effected, skill))
		{
			return initVal;
		}
		
		final Item item = (Item) getFuncOwner();
		if (item.getEnchantLevel() > 0)
		{
			return initVal + EnchantItemHPBonusData.getInstance().getHPBonus(item);
		}
		return initVal;
	}
}
