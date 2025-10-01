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
package com.l2journey.gameserver.model.zone.type;

import com.l2journey.commons.threads.ThreadPool;
import com.l2journey.gameserver.data.xml.SkillData;
import com.l2journey.gameserver.model.actor.Creature;
import com.l2journey.gameserver.model.skill.BuffInfo;
import com.l2journey.gameserver.model.skill.Skill;
import com.l2journey.gameserver.model.skill.enums.SkillFinishType;
import com.l2journey.gameserver.model.zone.ZoneId;
import com.l2journey.gameserver.model.zone.ZoneType;

public class HotSpringGreenSpa extends ZoneType
{
	private static final int HOT_SPRING_INFLUENZA = 4553;
	private static final int HOT_SPRING_MALARIA = 4554;
	private static final int REDUCTION_INTERVAL = 1 * 60 * 1000;

	public HotSpringGreenSpa(int id)
	{
		super(id);
	}

	@Override
	protected void onEnter(Creature creature)
	{
		creature.setInsideZone(ZoneId.HOTSPRING_GREEN_SPA, true);
		startDebuffReduction(creature);
	}

	@Override
	protected void onExit(Creature creature)
	{
		creature.setInsideZone(ZoneId.HOTSPRING_GREEN_SPA, false);
	}

	private void startDebuffReduction(Creature creature)
	{
		checkDebuff(creature, HOT_SPRING_INFLUENZA);
		checkDebuff(creature, HOT_SPRING_MALARIA);
	}

	private void checkDebuff(Creature creature, int skillId)
	{
		if (!creature.isAffectedBySkill(skillId))
		{
			return;
		}

		BuffInfo buffInfo = creature.getEffectList().getBuffInfoBySkillId(skillId);
		if (buffInfo != null)
		{
			scheduleNextReduction(creature, skillId, buffInfo.getSkill().getLevel());
		}
	}

	private void scheduleNextReduction(Creature creature, int skillId, int currentLevel)
	{
		if (!creature.isInsideZone(ZoneId.HOTSPRING_GREEN_SPA))
		{
			return;
		}

		ThreadPool.schedule(() ->
		{
			if (creature.isInsideZone(ZoneId.HOTSPRING_GREEN_SPA))
			{
				reduceDebuffLevel(creature, skillId, currentLevel);
			}
		}, REDUCTION_INTERVAL);
	}

	private void reduceDebuffLevel(Creature creature, int skillId, int currentLevel)
	{
		if (!creature.isInsideZone(ZoneId.HOTSPRING_GREEN_SPA))
		{
			return;
		}

		creature.stopSkillEffects(SkillFinishType.REMOVED, skillId);
		if (currentLevel <= 1)
		{
			return;
		}

		int newLevel = currentLevel - 1;
		Skill reducedSkill = SkillData.getInstance().getSkill(skillId, newLevel);
		if (reducedSkill == null)
		{
			return;
		}

		if (creature.isPlayer())
		{
			String skillName = reducedSkill.getName();
			creature.sendMessage("The effects of " + skillName + " has been reduced to level " + newLevel + ".");
		}

		if (creature.isInsideZone(ZoneId.HOTSPRING_GREEN_SPA))
		{
			reducedSkill.applyEffects(creature, creature);
			scheduleNextReduction(creature, skillId, newLevel);
		}
	}
}
