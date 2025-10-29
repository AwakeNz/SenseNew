package com.l2journey.gameserver.model.talent;

import com.l2journey.gameserver.model.stats.Stat;
import com.l2journey.gameserver.model.stats.TraitType;

/**
 * Represents a single talent that can be learned by a player.
 * Each talent belongs to a branch and tier, and provides stat bonuses.
 */
public class Talent
{
	private final int _id;
	private final String _name;
	private final String _description;
	private final TalentBranch _branch;
	private final TalentTier _tier;
	private final int _maxLevel;
	private final TalentBonusType _bonusType;
	private final Stat _stat;
	private final TraitType _traitType;
	private final double _valuePerLevel;
	private final double _percentPerLevel;

	public Talent(int id, String name, String description, TalentBranch branch, TalentTier tier, int maxLevel, TalentBonusType bonusType, Stat stat, TraitType traitType, double valuePerLevel, double percentPerLevel)
	{
		_id = id;
		_name = name;
		_description = description;
		_branch = branch;
		_tier = tier;
		_maxLevel = maxLevel;
		_bonusType = bonusType;
		_stat = stat;
		_traitType = traitType;
		_valuePerLevel = valuePerLevel;
		_percentPerLevel = percentPerLevel;
	}

	public int getId()
	{
		return _id;
	}

	public String getName()
	{
		return _name;
	}

	public String getDescription()
	{
		return _description;
	}

	public TalentBranch getBranch()
	{
		return _branch;
	}

	public TalentTier getTier()
	{
		return _tier;
	}

	public int getMaxLevel()
	{
		return _maxLevel;
	}

	public TalentBonusType getBonusType()
	{
		return _bonusType;
	}

	public Stat getStat()
	{
		return _stat;
	}

	public TraitType getTraitType()
	{
		return _traitType;
	}

	public double getValuePerLevel()
	{
		return _valuePerLevel;
	}

	public double getPercentPerLevel()
	{
		return _percentPerLevel;
	}

	/**
	 * Calculate the total fixed value bonus for a given talent level.
	 * @param level the talent level (1-5)
	 * @return the fixed value bonus
	 */
	public double getFixedValueBonus(int level)
	{
		return _valuePerLevel * level;
	}

	/**
	 * Calculate the total percentage bonus for a given talent level.
	 * @param level the talent level (1-5)
	 * @return the percentage bonus (as a decimal, e.g., 0.05 for 5%)
	 */
	public double getPercentBonus(int level)
	{
		return _percentPerLevel * level;
	}
}
