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
package com.l2journey.gameserver.model.actor.tasks.cubics;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2journey.commons.util.Rnd;
import com.l2journey.gameserver.model.actor.Creature;
import com.l2journey.gameserver.model.actor.instance.Cubic;
import com.l2journey.gameserver.model.effects.EffectType;
import com.l2journey.gameserver.model.skill.BuffInfo;
import com.l2journey.gameserver.model.skill.Skill;
import com.l2journey.gameserver.model.skill.enums.SkillFinishType;
import com.l2journey.gameserver.network.serverpackets.MagicSkillUse;
import com.l2journey.gameserver.taskmanagers.AttackStanceTaskManager;

/**
 * Cubic action task.
 * @author Zoey76
 */
public class CubicAction implements Runnable
{
	private static final Logger LOGGER = Logger.getLogger(CubicAction.class.getName());
	private final Cubic _cubic;
	private final AtomicInteger _currentCount = new AtomicInteger();
	private final int _chance;
	
	public CubicAction(Cubic cubic, int chance)
	{
		_cubic = cubic;
		_chance = chance;
	}
	
	@Override
	public void run()
	{
		if (_cubic == null)
		{
			return;
		}
		
		try
		{
			if (_cubic.getOwner().isDead() || !_cubic.getOwner().isOnline())
			{
				_cubic.stopAction();
				_cubic.getOwner().getCubics().remove(_cubic.getId());
				_cubic.getOwner().broadcastUserInfo();
				_cubic.cancelDisappear();
				return;
			}
			
			if (!AttackStanceTaskManager.getInstance().hasAttackStanceTask(_cubic.getOwner()))
			{
				if (_cubic.getOwner().hasSummon())
				{
					if (!AttackStanceTaskManager.getInstance().hasAttackStanceTask(_cubic.getOwner().getSummon()))
					{
						_cubic.stopAction();
						return;
					}
				}
				else
				{
					_cubic.stopAction();
					return;
				}
			}
			
			// The cubic has already reached its limit and it will stay idle until its duration ends.
			if ((_cubic.getCubicMaxCount() > -1) && (_currentCount.get() >= _cubic.getCubicMaxCount()))
			{
				_cubic.stopAction();
				return;
			}
			
			// Smart Cubic debuff cancel is 100%
			boolean useCubicCure = false;
			if ((_cubic.getId() >= Cubic.SMART_CUBIC_EVATEMPLAR) && (_cubic.getId() <= Cubic.SMART_CUBIC_SPECTRALMASTER))
			{
				for (BuffInfo info : _cubic.getOwner().getEffectList().getDebuffs())
				{
					if (info.getSkill().canBeDispeled())
					{
						useCubicCure = true;
						info.getEffected().getEffectList().stopSkillEffects(SkillFinishType.REMOVED, info.getSkill());
					}
				}
			}
			
			if (useCubicCure)
			{
				// Smart Cubic debuff cancel is needed, no other skill is used in this activation period
				_cubic.getOwner().broadcastPacket(new MagicSkillUse(_cubic.getOwner(), _cubic.getOwner(), Cubic.SKILL_CUBIC_CURE, 1, 0, 0));
				
				// The cubic has done an action, increase the current count
				_currentCount.incrementAndGet();
			}
			else if (Rnd.get(1, 100) < _chance)
			{
				final Skill skill = _cubic.getSkills().get(Rnd.get(_cubic.getSkills().size()));
				if (skill == null)
				{
					return;
				}
				
				if (skill.getId() == Cubic.SKILL_CUBIC_HEAL)
				{
					// friendly skill, so we look a target in owner's party
					_cubic.cubicTargetForHeal();
				}
				else
				{
					// offensive skill, we look for an enemy target
					_cubic.getCubicTarget();
					if (!Cubic.isInCubicRange(_cubic.getOwner(), _cubic.getTarget()))
					{
						_cubic.setTarget(null);
					}
				}
				final Creature target = _cubic.getTarget();
				if ((target != null) && !target.isDead())
				{
					_cubic.getOwner().broadcastPacket(new MagicSkillUse(_cubic.getOwner(), target, skill.getId(), skill.getLevel(), 0, 0));
					
					if (skill.isContinuous())
					{
						_cubic.useCubicContinuous(skill, Collections.singletonList(target));
					}
					else
					{
						skill.activateSkill(_cubic, Collections.singletonList(target));
					}
					
					if (skill.hasEffectType(EffectType.MAGICAL_ATTACK))
					{
						_cubic.useCubicMdam(skill, Collections.singletonList(target));
					}
					else if (skill.hasEffectType(EffectType.HP_DRAIN))
					{
						_cubic.useCubicDrain(skill, Collections.singletonList(target));
					}
					else if (skill.hasEffectType(EffectType.STUN, EffectType.ROOT, EffectType.PARALYZE))
					{
						_cubic.useCubicDisabler(skill, Collections.singletonList(target));
					}
					else if (skill.hasEffectType(EffectType.DMG_OVER_TIME, EffectType.DMG_OVER_TIME_PERCENT))
					{
						_cubic.useCubicContinuous(skill, Collections.singletonList(target));
					}
					else if (skill.hasEffectType(EffectType.AGGRESSION))
					{
						_cubic.useCubicDisabler(skill, Collections.singletonList(target));
					}
					
					// The cubic has done an action, increase the current count
					_currentCount.incrementAndGet();
				}
			}
		}
		catch (Exception e)
		{
			LOGGER.log(Level.SEVERE, "", e);
		}
	}
}