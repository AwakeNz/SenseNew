package com.l2journey.gameserver.model.talent;

/**
 * Represents a talent that has been learned by a player,
 * including the current level invested.
 */
public class PlayerTalent
{
	private final Talent _talent;
	private int _level;

	public PlayerTalent(Talent talent, int level)
	{
		_talent = talent;
		_level = Math.min(level, talent.getMaxLevel());
	}

	public Talent getTalent()
	{
		return _talent;
	}

	public int getLevel()
	{
		return _level;
	}

	public void setLevel(int level)
	{
		_level = Math.min(level, _talent.getMaxLevel());
	}

	public boolean canLevelUp()
	{
		return _level < _talent.getMaxLevel();
	}

	public void levelUp()
	{
		if (canLevelUp())
		{
			_level++;
		}
	}
}
