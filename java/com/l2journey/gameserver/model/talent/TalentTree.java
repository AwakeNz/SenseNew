package com.l2journey.gameserver.model.talent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.l2journey.gameserver.model.actor.Player;

/**
 * Manages a player's learned talents for a specific subclass.
 * Tracks spent points per branch and learned talents.
 */
public class TalentTree
{
	private final Player _player;
	private final int _classIndex;
	private final Map<Integer, PlayerTalent> _learnedTalents = new ConcurrentHashMap<>();
	private int _spentPowerPoints = 0;
	private int _spentMasteryPoints = 0;
	private int _spentProtectionPoints = 0;

	public TalentTree(Player player, int classIndex)
	{
		_player = player;
		_classIndex = classIndex;
	}

	public Player getPlayer()
	{
		return _player;
	}

	public int getClassIndex()
	{
		return _classIndex;
	}

	/**
	 * Add or update a learned talent.
	 * @param talent the talent to learn
	 * @param level the level to set
	 */
	public void learnTalent(Talent talent, int level)
	{
		PlayerTalent playerTalent = _learnedTalents.get(talent.getId());
		if (playerTalent == null)
		{
			_learnedTalents.put(talent.getId(), new PlayerTalent(talent, level));
		}
		else
		{
			playerTalent.setLevel(level);
		}

		recalculateSpentPoints();
	}

	/**
	 * Remove a learned talent.
	 * @param talentId the talent ID to remove
	 */
	public void removeTalent(int talentId)
	{
		_learnedTalents.remove(talentId);
		recalculateSpentPoints();
	}

	/**
	 * Get a learned talent by ID.
	 * @param talentId the talent ID
	 * @return the PlayerTalent or null if not learned
	 */
	public PlayerTalent getTalent(int talentId)
	{
		return _learnedTalents.get(talentId);
	}

	/**
	 * Get the level of a specific talent (0 if not learned).
	 * @param talentId the talent ID
	 * @return the talent level
	 */
	public int getTalentLevel(int talentId)
	{
		PlayerTalent talent = _learnedTalents.get(talentId);
		return talent != null ? talent.getLevel() : 0;
	}

	/**
	 * Get all learned talents.
	 * @return map of talent ID to PlayerTalent
	 */
	public Map<Integer, PlayerTalent> getLearnedTalents()
	{
		return _learnedTalents;
	}

	/**
	 * Get the number of points spent in a specific branch.
	 * @param branch the talent branch
	 * @return the number of points spent
	 */
	public int getSpentPoints(TalentBranch branch)
	{
		switch (branch)
		{
			case POWER:
				return _spentPowerPoints;
			case MASTERY:
				return _spentMasteryPoints;
			case PROTECTION:
				return _spentProtectionPoints;
			default:
				return 0;
		}
	}

	/**
	 * Get the number of points spent in a specific tier of a branch.
	 * @param branch the talent branch
	 * @param tier the talent tier
	 * @return the number of points spent
	 */
	public int getSpentPointsInTier(TalentBranch branch, TalentTier tier)
	{
		int count = 0;
		for (PlayerTalent pt : _learnedTalents.values())
		{
			if ((pt.getTalent().getBranch() == branch) && (pt.getTalent().getTier() == tier))
			{
				count += pt.getLevel();
			}
		}
		return count;
	}

	/**
	 * Check if a tier is unlocked in a branch.
	 * To unlock a tier, you need 10 points in the previous tier.
	 * @param branch the talent branch
	 * @param tier the talent tier
	 * @return true if the tier is unlocked
	 */
	public boolean isTierUnlocked(TalentBranch branch, TalentTier tier)
	{
		if (tier == TalentTier.TIER_1)
		{
			return true; // Tier 1 is always unlocked
		}

		// Get the previous tier
		TalentTier previousTier = null;
		if (tier == TalentTier.TIER_2)
		{
			previousTier = TalentTier.TIER_1;
		}
		else if (tier == TalentTier.TIER_3)
		{
			previousTier = TalentTier.TIER_2;
		}

		if (previousTier == null)
		{
			return false;
		}

		// Check if 10 points are spent in the previous tier
		return getSpentPointsInTier(branch, previousTier) >= 10;
	}

	/**
	 * Reset all learned talents.
	 */
	public void resetTalents()
	{
		_learnedTalents.clear();
		_spentPowerPoints = 0;
		_spentMasteryPoints = 0;
		_spentProtectionPoints = 0;
	}

	/**
	 * Recalculate spent points per branch.
	 */
	private void recalculateSpentPoints()
	{
		_spentPowerPoints = 0;
		_spentMasteryPoints = 0;
		_spentProtectionPoints = 0;

		for (PlayerTalent pt : _learnedTalents.values())
		{
			switch (pt.getTalent().getBranch())
			{
				case POWER:
					_spentPowerPoints += pt.getLevel();
					break;
				case MASTERY:
					_spentMasteryPoints += pt.getLevel();
					break;
				case PROTECTION:
					_spentProtectionPoints += pt.getLevel();
					break;
			}
		}
	}

	/**
	 * Get the total number of spent points across all branches.
	 * @return the total spent points
	 */
	public int getTotalSpentPoints()
	{
		return _spentPowerPoints + _spentMasteryPoints + _spentProtectionPoints;
	}
}
