package com.l2journey.gameserver.model.talent;

import com.l2journey.gameserver.model.actor.Player;
import com.l2journey.gameserver.model.stats.Stat;
import com.l2journey.gameserver.model.stats.TraitType;

/**
 * Utility class for applying talent bonuses to player stats.
 * @author L2Journey
 */
public class TalentStatCalculator
{
	/**
	 * Calculate and apply talent bonus to a stat value.
	 * @param player the player
	 * @param stat the stat to modify
	 * @param baseValue the base value before talents
	 * @return the final value after applying talent bonuses
	 */
	public static double applyTalentBonus(Player player, Stat stat, double baseValue)
	{
		if ((player == null) || (stat == null))
		{
			return baseValue;
		}

		final TalentTree tree = player.getTalentHolder().getCurrentTalentTree();
		if (tree == null)
		{
			return baseValue;
		}

		double fixedBonus = 0;
		double percentBonus = 0;

		// Iterate through learned talents
		for (PlayerTalent pt : tree.getLearnedTalents().values())
		{
			final Talent talent = pt.getTalent();
			if (talent.getStat() != stat)
			{
				continue;
			}

			final int level = pt.getLevel();

			switch (talent.getBonusType())
			{
				case FIXED:
					fixedBonus += talent.getFixedValueBonus(level);
					break;

				case PERCENT:
					percentBonus += talent.getPercentBonus(level);
					break;

				case COMBINED:
					fixedBonus += talent.getFixedValueBonus(level);
					percentBonus += talent.getPercentBonus(level);
					break;

				case ATTACK_TRAIT:
				case DEFENCE_TRAIT:
				case SPECIAL:
					// These are handled separately
					break;
			}
		}

		// Apply bonuses: (base + fixed) * (1 + percent)
		double result = baseValue + fixedBonus;
		result *= (1.0 + percentBonus);

		return result;
	}

	/**
	 * Apply talent trait bonuses (attack/defense traits).
	 * @param player the player
	 */
	public static void applyTalentTraitBonuses(Player player)
	{
		if (player == null)
		{
			return;
		}

		final TalentTree tree = player.getTalentHolder().getCurrentTalentTree();
		if (tree == null)
		{
			return;
		}

		// Iterate through learned talents
		for (PlayerTalent pt : tree.getLearnedTalents().values())
		{
			final Talent talent = pt.getTalent();
			final int level = pt.getLevel();

			if (talent.getBonusType() == TalentBonusType.ATTACK_TRAIT)
			{
				final TraitType traitType = talent.getTraitType();
				if (traitType != null)
				{
					final float bonus = (float) talent.getPercentBonus(level);
					final float currentBonus = player.getStat().getAttackTrait(traitType);
					player.getStat().getAttackTraits()[traitType.ordinal()] = currentBonus + bonus;
					player.getStat().getAttackTraitsCount()[traitType.ordinal()]++;
				}
			}
			else if (talent.getBonusType() == TalentBonusType.DEFENCE_TRAIT)
			{
				final TraitType traitType = talent.getTraitType();
				if (traitType != null)
				{
					final float bonus = (float) talent.getPercentBonus(level);
					final float currentBonus = player.getStat().getDefenceTrait(traitType);
					player.getStat().getDefenceTraits()[traitType.ordinal()] = currentBonus + bonus;
					player.getStat().getDefenceTraitsCount()[traitType.ordinal()]++;
				}
			}
		}
	}

	/**
	 * Check if a player has a specific special talent.
	 * @param player the player
	 * @param talentName the talent name
	 * @return the total level invested in this talent
	 */
	public static int getSpecialTalentLevel(Player player, String talentName)
	{
		if ((player == null) || (talentName == null))
		{
			return 0;
		}

		final TalentTree tree = player.getTalentHolder().getCurrentTalentTree();
		if (tree == null)
		{
			return 0;
		}

		for (PlayerTalent pt : tree.getLearnedTalents().values())
		{
			final Talent talent = pt.getTalent();
			if (talent.getName().equalsIgnoreCase(talentName) && (talent.getBonusType() == TalentBonusType.SPECIAL))
			{
				return pt.getLevel();
			}
		}

		return 0;
	}
}
