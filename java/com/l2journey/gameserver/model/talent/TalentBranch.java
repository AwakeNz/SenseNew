package com.l2journey.gameserver.model.talent;

/**
 * Enum representing the three talent branches available to players.
 * Each branch contains 30 talent points worth of abilities across 3 tiers.
 */
public enum TalentBranch
{
	POWER(0, "Power"),
	MASTERY(1, "Mastery"),
	PROTECTION(2, "Protection");

	private final int _id;
	private final String _name;

	TalentBranch(int id, String name)
	{
		_id = id;
		_name = name;
	}

	public int getId()
	{
		return _id;
	}

	public String getName()
	{
		return _name;
	}

	public static TalentBranch getById(int id)
	{
		for (TalentBranch branch : values())
		{
			if (branch.getId() == id)
			{
				return branch;
			}
		}
		return null;
	}
}
