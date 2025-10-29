package com.l2journey.gameserver.model.talent;

/**
 * Enum representing the three talent tiers within each branch.
 * Each tier requires 10 points invested in the previous tier to unlock.
 */
public enum TalentTier
{
	TIER_1(1, 0),  // Requires 0 points to unlock
	TIER_2(2, 10), // Requires 10 points in previous tier to unlock
	TIER_3(3, 10); // Requires 10 points in previous tier to unlock

	private final int _tier;
	private final int _requiredPointsInPreviousTier;

	TalentTier(int tier, int requiredPoints)
	{
		_tier = tier;
		_requiredPointsInPreviousTier = requiredPoints;
	}

	public int getTier()
	{
		return _tier;
	}

	public int getRequiredPointsInPreviousTier()
	{
		return _requiredPointsInPreviousTier;
	}

	public static TalentTier getByTier(int tier)
	{
		for (TalentTier t : values())
		{
			if (t.getTier() == tier)
			{
				return t;
			}
		}
		return TIER_1;
	}
}
