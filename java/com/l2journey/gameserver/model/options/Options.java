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
package com.l2journey.gameserver.model.options;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.l2journey.gameserver.model.actor.Creature;
import com.l2journey.gameserver.model.actor.Player;
import com.l2journey.gameserver.model.item.instance.Item;
import com.l2journey.gameserver.model.skill.Skill;
import com.l2journey.gameserver.model.stats.functions.AbstractFunction;
import com.l2journey.gameserver.model.stats.functions.FuncTemplate;
import com.l2journey.gameserver.network.serverpackets.SkillCoolTime;

/**
 * @author UnAfraid
 */
public class Options
{
	private final int _id;
	private final List<FuncTemplate> _funcs = new ArrayList<>();
	private Skill _activeSkill = null;
	private Skill _passiveSkill = null;
	private final List<OptionSkillHolder> _activationSkills = new ArrayList<>();
	
	/**
	 * @param id
	 */
	public Options(int id)
	{
		_id = id;
	}
	
	public int getId()
	{
		return _id;
	}
	
	public boolean hasFuncs()
	{
		return !_funcs.isEmpty();
	}
	
	public List<AbstractFunction> getStatFuncs(Item item, Creature creature)
	{
		if (_funcs.isEmpty())
		{
			return Collections.<AbstractFunction> emptyList();
		}
		
		final List<AbstractFunction> funcs = new ArrayList<>(_funcs.size());
		for (FuncTemplate fuctionTemplate : _funcs)
		{
			final AbstractFunction fuction = fuctionTemplate.getFunc(creature, creature, item, this);
			if (fuction != null)
			{
				funcs.add(fuction);
			}
		}
		return funcs;
	}
	
	public void addFunc(FuncTemplate template)
	{
		_funcs.add(template);
	}
	
	public boolean hasActiveSkill()
	{
		return _activeSkill != null;
	}
	
	public Skill getActiveSkill()
	{
		return _activeSkill;
	}
	
	public void setActiveSkill(Skill skill)
	{
		_activeSkill = skill;
	}
	
	public boolean hasPassiveSkill()
	{
		return _passiveSkill != null;
	}
	
	public Skill getPassiveSkill()
	{
		return _passiveSkill;
	}
	
	public void setPassiveSkill(Skill skill)
	{
		_passiveSkill = skill;
	}
	
	public boolean hasActivationSkills()
	{
		return !_activationSkills.isEmpty();
	}
	
	public boolean hasActivationSkills(OptionSkillType type)
	{
		for (OptionSkillHolder holder : _activationSkills)
		{
			if (holder.getSkillType() == type)
			{
				return true;
			}
		}
		return false;
	}
	
	public List<OptionSkillHolder> getActivationSkills()
	{
		return _activationSkills;
	}
	
	public List<OptionSkillHolder> getActivationSkills(OptionSkillType type)
	{
		final List<OptionSkillHolder> temp = new ArrayList<>();
		for (OptionSkillHolder holder : _activationSkills)
		{
			if (holder.getSkillType() == type)
			{
				temp.add(holder);
			}
		}
		return temp;
	}
	
	public void addActivationSkill(OptionSkillHolder holder)
	{
		_activationSkills.add(holder);
	}
	
	public void apply(Player player)
	{
		if (!_funcs.isEmpty())
		{
			player.addStatFuncs(getStatFuncs(null, player));
		}
		if (hasActiveSkill())
		{
			addSkill(player, _activeSkill);
		}
		if (hasPassiveSkill())
		{
			addSkill(player, _passiveSkill);
		}
		if (!_activationSkills.isEmpty())
		{
			for (OptionSkillHolder holder : _activationSkills)
			{
				player.addTriggerSkill(holder);
			}
		}
		
		player.sendSkillList();
	}
	
	public void remove(Player player)
	{
		if (!_funcs.isEmpty())
		{
			player.removeStatsOwner(this);
		}
		if (hasActiveSkill())
		{
			player.removeSkill(_activeSkill, false, false);
		}
		if (hasPassiveSkill())
		{
			player.removeSkill(_passiveSkill, false, true);
		}
		if (!_activationSkills.isEmpty())
		{
			for (OptionSkillHolder holder : _activationSkills)
			{
				player.removeTriggerSkill(holder);
			}
		}
		player.sendSkillList();
	}
	
	private void addSkill(Player player, Skill skill)
	{
		boolean updateTimeStamp = false;
		player.addSkill(skill, false);
		if (skill.isActive())
		{
			final long remainingTime = player.getSkillRemainingReuseTime(skill.getReuseHashCode());
			if (remainingTime > 0)
			{
				player.addTimeStamp(skill, remainingTime);
				player.disableSkill(skill, remainingTime);
			}
			updateTimeStamp = true;
		}
		if (updateTimeStamp)
		{
			player.sendPacket(new SkillCoolTime(player));
		}
	}
}
