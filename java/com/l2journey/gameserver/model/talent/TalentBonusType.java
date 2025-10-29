package com.l2journey.gameserver.model.talent;

/**
 * Enum representing the type of bonus a talent provides.
 */
public enum TalentBonusType
{
	/** Fixed value bonus (e.g., +10 P.Def) */
	FIXED,

	/** Percentage bonus (e.g., +1% P.Def) */
	PERCENT,

	/** Combined fixed and percentage bonus (e.g., +40 P.Atk and +1% P.Atk) */
	COMBINED,

	/** Attack trait bonus (increases attack effectiveness with a specific trait) */
	ATTACK_TRAIT,

	/** Defence trait bonus (increases resistance to a specific trait) */
	DEFENCE_TRAIT,

	/** Special effects (clarity, vampiric, etc.) */
	SPECIAL
}
