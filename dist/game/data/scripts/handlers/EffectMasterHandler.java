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
package handlers;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2journey.gameserver.handler.EffectHandler;
import com.l2journey.gameserver.model.effects.AbstractEffect;

import handlers.effecthandlers.*;

/**
 * Effect Master handler.
 * @author BiggBoss, Zoey76
 */
public class EffectMasterHandler
{
	private static final Logger LOGGER = Logger.getLogger(EffectMasterHandler.class.getName());
	
	private static final Class<?>[] EFFECTS =
	{
		AddHate.class,
		AttackTrait.class,
		Backstab.class,
		Betray.class,
		BigHead.class,
		Blink.class,
		BlockAction.class,
		BlockAbnormalSlot.class,
		BlockChat.class,
		BlockParty.class,
		BlockResurrection.class,
		Bluff.class,
		Buff.class,
		CallParty.class,
		CallPc.class,
		CallSkill.class,
		ChameleonRest.class,
		ChangeFace.class,
		ChangeFishingMastery.class,
		ChangeHairColor.class,
		ChangeHairStyle.class,
		ClanGate.class,
		Confuse.class,
		ConsumeBody.class,
		ConvertItem.class,
		CpDamPercent.class,
		CpHeal.class,
		CpHealOverTime.class,
		CpHealPercent.class,
		CrystalGradeModify.class,
		CubicMastery.class,
		DamOverTime.class,
		DamOverTimePercent.class,
		DeathLink.class,
		Debuff.class,
		DefenceTrait.class,
		DeleteHate.class,
		DeleteHateOfMe.class,
		DetectHiddenObjects.class,
		Detection.class,
		Disarm.class,
		DispelAll.class,
		DispelByCategory.class,
		DispelBySlot.class,
		DispelBySlotProbability.class,
		Distrust.class,
		EnableCloak.class,
		EnemyCharge.class,
		EnergyDamage.class,
		EnlargeAbnormalSlot.class,
		Escape.class,
		FakeDeath.class,
		FatalBlow.class,
		Fear.class,
		Fishing.class,
		Flag.class,
		FlipBlock.class,
		FocusEnergy.class,
		FocusMaxEnergy.class,
		FocusSouls.class,
		GetAgro.class,
		GiveRecommendation.class,
		GiveSp.class,
		Grow.class,
		Harvesting.class,
		HeadquarterCreate.class,
		Heal.class,
		HealOverTime.class,
		HealPercent.class,
		Hide.class,
		HpByLevel.class,
		HpDrain.class,
		ImmobileBuff.class,
		ImmobilePetBuff.class,
		Invincible.class,
		Lethal.class,
		Lucky.class,
		MagicalDamage.class,
		MagicalDamageByAbnormal.class,
		MagicalDamageMp.class,
		MagicalSoulDamage.class,
		ManaDamOverTime.class,
		ManaHeal.class,
		ManaHealByLevel.class,
		ManaHealOverTime.class,
		ManaHealPercent.class,
		MaxCp.class,
		MaxHp.class,
		MpConsumePerLevel.class,
		Mute.class,
		NevitsHourglass.class,
		NoblesseBless.class,
		OpenChest.class,
		OpenCommonRecipeBook.class,
		OpenDoor.class,
		OpenDwarfRecipeBook.class,
		OutpostCreate.class,
		OutpostDestroy.class,
		Paralyze.class,
		Passive.class,
		Petrification.class,
		PhysicalDamage.class,
		PhysicalDamageHpLink.class,
		PhysicalDamageMute.class,
		PhysicalMute.class,
		PhysicalSoulDamage.class,
		PolearmSingleTarget.class,
		ProtectionBlessing.class,
		Pumping.class,
		RandomizeHate.class,
		RebalanceHP.class,
		Recovery.class,
		Reeling.class,
		RefuelAirship.class,
		Relax.class,
		Requiem.class,
		ResistSkill.class,
		Restoration.class,
		RestorationRandom.class,
		Resurrection.class,
		ResurrectionSpecial.class,
		Ride.class,
		Root.class,
		RunAway.class,
		ServitorShare.class,
		SetSkill.class,
		SilentMove.class,
		SkillTurning.class,
		Sleep.class,
		SoulBlow.class,
		SoulEating.class,
		Sow.class,
		Spoil.class,
		StaticDamage.class,
		StealAbnormal.class,
		Stun.class,
		Summon.class,
		SummonAgathion.class,
		SummonCubic.class,
		SummonNpc.class,
		SummonPet.class,
		SummonTrap.class,
		Sweeper.class,
		TakeCastle.class,
		TakeFort.class,
		TakeFortStart.class,
		TakeTerritoryFlag.class,
		TalismanSlot.class,
		TargetCancel.class,
		TargetMe.class,
		TargetMeProbability.class,
		Teleport.class,
		TeleportToTarget.class,
		ThrowUp.class,
		TransferDamage.class,
		TransferHate.class,
		Transformation.class,
		TrapDetect.class,
		TrapRemove.class,
		TriggerSkillByAvoid.class,
		TriggerSkillByDamageDealt.class,
		TriggerSkillByDamageReceived.class,
		TriggerSkillBySkill.class,
		Unsummon.class,
		UnsummonAgathion.class,
		VitalityPointUp.class,
	};
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{
		for (Class<?> c : EFFECTS)
		{
			if (c == null)
			{
				continue; // Disabled handler
			}
			EffectHandler.getInstance().registerHandler((Class<? extends AbstractEffect>) c);
		}
		
		// And lets try get size
		try
		{
			LOGGER.log(Level.INFO, EffectMasterHandler.class.getSimpleName() + ": Loaded " + EffectHandler.getInstance().size() + " effect handlers.");
		}
		catch (Exception e)
		{
			LOGGER.log(Level.WARNING, "Failed invoking size method for handler: " + EffectMasterHandler.class.getSimpleName(), e);
		}
	}
}
