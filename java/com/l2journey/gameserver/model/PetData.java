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
package com.l2journey.gameserver.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.l2journey.gameserver.data.xml.SkillData;
import com.l2journey.gameserver.model.skill.holders.SkillHolder;

/**
 * Class hold information about basic pet stats which are same on each level.
 * @author JIV
 */
public class PetData
{
	private final Map<Integer, PetLevelData> _levelStats = new HashMap<>();
	private final List<PetSkillLearn> _skills = new ArrayList<>();
	
	private final int _npcId;
	private final int _itemId;
	private int _load = 20000;
	private int _hungryLimit = 1;
	private int _minLevel = Byte.MAX_VALUE;
	private int _maxLevel = 0;
	private boolean _syncLevel = false;
	private final Set<Integer> _food = new HashSet<>();
	
	public PetData(int npcId, int itemId)
	{
		_npcId = npcId;
		_itemId = itemId;
	}
	
	/**
	 * @return the npc id representing this pet.
	 */
	public int getNpcId()
	{
		return _npcId;
	}
	
	/**
	 * @return the item id that could summon this pet.
	 */
	public int getItemId()
	{
		return _itemId;
	}
	
	/**
	 * @param level the pet's level.
	 * @param data the pet's data.
	 */
	public void addNewStat(int level, PetLevelData data)
	{
		if (_minLevel > level)
		{
			_minLevel = level;
		}
		if (_maxLevel < level)
		{
			_maxLevel = level;
		}
		_levelStats.put(level, data);
	}
	
	/**
	 * @param petLevel the pet's level.
	 * @return the pet data associated to that pet level.
	 */
	public PetLevelData getPetLevelData(int petLevel)
	{
		return _levelStats.get(petLevel);
	}
	
	/**
	 * @return the pet's weight load.
	 */
	public int getLoad()
	{
		return _load;
	}
	
	/**
	 * @return the pet's hunger limit.
	 */
	public int getHungryLimit()
	{
		return _hungryLimit;
	}
	
	/**
	 * @return {@code true} if pet synchronizes it's level with his master's
	 */
	public boolean isSynchLevel()
	{
		return _syncLevel;
	}
	
	/**
	 * @return the pet's minimum level.
	 */
	public int getMinLevel()
	{
		return _minLevel;
	}
	
	/**
	 * @return the pet's maximum level.
	 */
	public int getMaxLevel()
	{
		return _maxLevel;
	}
	
	/**
	 * @return the pet's food list.
	 */
	public Set<Integer> getFood()
	{
		return _food;
	}
	
	/**
	 * @param foodId the pet's food Id to add.
	 */
	public void addFood(Integer foodId)
	{
		_food.add(foodId);
	}
	
	/**
	 * @param load the weight load to set.
	 */
	public void setLoad(int load)
	{
		_load = load;
	}
	
	/**
	 * @param limit the hunger limit to set.
	 */
	public void setHungryLimit(int limit)
	{
		_hungryLimit = limit;
	}
	
	/**
	 * @param value synchronizes level with master or not.
	 */
	public void setSyncLevel(boolean value)
	{
		_syncLevel = value;
	}
	
	// SKILS
	
	/**
	 * @param skillId the skill Id to add.
	 * @param skillLevel the skill level.
	 * @param petLvl the pet's level when this skill is available.
	 */
	public void addNewSkill(int skillId, int skillLevel, int petLvl)
	{
		_skills.add(new PetSkillLearn(skillId, skillLevel, petLvl));
	}
	
	/**
	 * @param skillId the skill Id.
	 * @param petLvl the pet level.
	 * @return the level of the skill for the given skill Id and pet level.
	 */
	public int getAvailableLevel(int skillId, int petLvl)
	{
		int lvl = 0;
		boolean found = false;
		for (PetSkillLearn temp : _skills)
		{
			if (temp.getSkillId() != skillId)
			{
				continue;
			}
			found = true;
			if (temp.getSkillLevel() == 0)
			{
				if (petLvl < 70)
				{
					lvl = (petLvl / 10);
					if (lvl <= 0)
					{
						lvl = 1;
					}
				}
				else
				{
					lvl = (7 + ((petLvl - 70) / 5));
				}
				
				// formula usable for skill that have 10 or more skill levels
				final int maxLevel = SkillData.getInstance().getMaxLevel(temp.getSkillId());
				if (lvl > maxLevel)
				{
					lvl = maxLevel;
				}
				break;
			}
			else if ((temp.getMinLevel() <= petLvl) && (temp.getSkillLevel() > lvl))
			{
				lvl = temp.getSkillLevel();
			}
		}
		if (found && (lvl == 0))
		{
			return 1;
		}
		return lvl;
	}
	
	/**
	 * @return the list with the pet's skill data.
	 */
	public List<PetSkillLearn> getAvailableSkills()
	{
		return _skills;
	}
	
	public static class PetSkillLearn extends SkillHolder
	{
		private final int _minLevel;
		
		/**
		 * @param id the skill Id.
		 * @param lvl the skill level.
		 * @param minLevel the minimum level when this skill is available.
		 */
		public PetSkillLearn(int id, int lvl, int minLevel)
		{
			super(id, lvl);
			_minLevel = minLevel;
		}
		
		/**
		 * @return the minimum level for the pet to get the skill.
		 */
		public int getMinLevel()
		{
			return _minLevel;
		}
	}
}
