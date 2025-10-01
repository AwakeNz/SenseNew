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
package handlers.effecthandlers;

import com.l2journey.gameserver.model.StatSet;
import com.l2journey.gameserver.model.actor.Creature;
import com.l2journey.gameserver.model.conditions.Condition;
import com.l2journey.gameserver.model.effects.AbstractEffect;
import com.l2journey.gameserver.model.effects.EffectType;
import com.l2journey.gameserver.model.skill.Skill;
import com.l2journey.gameserver.network.SystemMessageId;

/**
 * Damage Over Time Percent effect implementation.
 * @author Adry_85
 */
public class DamOverTimePercent extends AbstractEffect
{
	private final boolean _canKill;
	private final double _power;
	
	public DamOverTimePercent(Condition attachCond, Condition applyCond, StatSet set, StatSet params)
	{
		super(attachCond, applyCond, set, params);
		
		_canKill = params.getBoolean("canKill", false);
		_power = params.getDouble("power", 0);
	}
	
	@Override
	public EffectType getEffectType()
	{
		return EffectType.DMG_OVER_TIME_PERCENT;
	}
	
	@Override
	public boolean onActionTime(Creature effector, Creature effected, Skill skill)
	{
		if (effected.isDead())
		{
			return false;
		}
		
		double damage = effected.getCurrentHp() * _power * getTicksMultiplier();
		if (damage >= (effected.getCurrentHp() - 1))
		{
			if (skill.isToggle())
			{
				effected.sendPacket(SystemMessageId.YOUR_SKILL_HAS_BEEN_CANCELED_DUE_TO_LACK_OF_HP);
				return false;
			}
			
			// For DOT skills that will not kill effected player.
			if (!_canKill)
			{
				// Fix for players dying by DOTs if HP < 1 since reduceCurrentHP method will kill them
				if (effected.getCurrentHp() <= 1)
				{
					return skill.isToggle();
				}
				damage = effected.getCurrentHp() - 1;
			}
		}
		
		effected.reduceCurrentHpByDOT(damage, effector, skill);
		effected.notifyDamageReceived(damage, effector, skill, false, true);
		return skill.isToggle();
	}
}
