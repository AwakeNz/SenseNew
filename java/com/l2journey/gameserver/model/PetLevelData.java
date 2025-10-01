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

import com.l2journey.gameserver.model.stats.MoveType;

/**
 * Stats definition for each pet level.
 * @author JIV
 */
public class PetLevelData
{
	private final int _ownerExpTaken;
	private final int _petFeedBattle;
	private final int _petFeedNormal;
	private final float _petMAtk;
	private final long _petMaxExp;
	private final int _petMaxFeed;
	private final float _petMaxHP;
	private final float _petMaxMP;
	private final float _petMDef;
	private final float _petPAtk;
	private final float _petPDef;
	private final float _petRegenHP;
	private final float _petRegenMP;
	private final short _petSoulShot;
	private final short _petSpiritShot;
	private final double _walkSpeedOnRide;
	private final double _runSpeedOnRide;
	private final double _slowSwimSpeedOnRide;
	private final double _fastSwimSpeedOnRide;
	private final double _slowFlySpeedOnRide;
	private final double _fastFlySpeedOnRide;
	
	public PetLevelData(StatSet set)
	{
		_ownerExpTaken = set.getInt("get_exp_type");
		_petMaxExp = (long) set.getDouble("exp");
		_petMaxHP = set.getFloat("org_hp");
		_petMaxMP = set.getFloat("org_mp");
		_petPAtk = set.getFloat("org_pattack");
		_petPDef = set.getFloat("org_pdefend");
		_petMAtk = set.getFloat("org_mattack");
		_petMDef = set.getFloat("org_mdefend");
		_petMaxFeed = set.getInt("max_meal");
		_petFeedBattle = set.getInt("consume_meal_in_battle");
		_petFeedNormal = set.getInt("consume_meal_in_normal");
		_petRegenHP = set.getFloat("org_hp_regen");
		_petRegenMP = set.getFloat("org_mp_regen");
		_petSoulShot = set.getShort("soulshot_count");
		_petSpiritShot = set.getShort("spiritshot_count");
		_walkSpeedOnRide = set.getDouble("walkSpeedOnRide", 0);
		_runSpeedOnRide = set.getDouble("runSpeedOnRide", 0);
		_slowSwimSpeedOnRide = set.getDouble("slowSwimSpeedOnRide", 0);
		_fastSwimSpeedOnRide = set.getDouble("fastSwimSpeedOnRide", 0);
		_slowFlySpeedOnRide = set.getDouble("slowFlySpeedOnRide", 0);
		_fastFlySpeedOnRide = set.getDouble("fastFlySpeedOnRide", 0);
	}
	
	/**
	 * @return the owner's experience points consumed by the pet.
	 */
	public int getOwnerExpTaken()
	{
		return _ownerExpTaken;
	}
	
	/**
	 * @return the pet's food consume rate at battle state.
	 */
	public int getPetFeedBattle()
	{
		return _petFeedBattle;
	}
	
	/**
	 * @return the pet's food consume rate at normal state.
	 */
	public int getPetFeedNormal()
	{
		return _petFeedNormal;
	}
	
	/**
	 * @return the pet's Magical Attack.
	 */
	public float getPetMAtk()
	{
		return _petMAtk;
	}
	
	/**
	 * @return the pet's maximum experience points.
	 */
	public long getPetMaxExp()
	{
		return _petMaxExp;
	}
	
	/**
	 * @return the pet's maximum feed points.
	 */
	public int getPetMaxFeed()
	{
		return _petMaxFeed;
	}
	
	/**
	 * @return the pet's maximum HP.
	 */
	public float getPetMaxHP()
	{
		return _petMaxHP;
	}
	
	/**
	 * @return the pet's maximum MP.
	 */
	public float getPetMaxMP()
	{
		return _petMaxMP;
	}
	
	/**
	 * @return the pet's Magical Defense.
	 */
	public float getPetMDef()
	{
		return _petMDef;
	}
	
	/**
	 * @return the pet's Physical Attack.
	 */
	public float getPetPAtk()
	{
		return _petPAtk;
	}
	
	/**
	 * @return the pet's Physical Defense.
	 */
	public float getPetPDef()
	{
		return _petPDef;
	}
	
	/**
	 * @return the pet's HP regeneration rate.
	 */
	public float getPetRegenHP()
	{
		return _petRegenHP;
	}
	
	/**
	 * @return the pet's MP regeneration rate.
	 */
	public float getPetRegenMP()
	{
		return _petRegenMP;
	}
	
	/**
	 * @return the pet's soulshot use count.
	 */
	public short getPetSoulShot()
	{
		return _petSoulShot;
	}
	
	/**
	 * @return the pet's spiritshot use count.
	 */
	public short getPetSpiritShot()
	{
		return _petSpiritShot;
	}
	
	/**
	 * @param mt movement type
	 * @return the base riding speed of given movement type.
	 */
	public double getSpeedOnRide(MoveType mt)
	{
		switch (mt)
		{
			case WALK:
			{
				return _walkSpeedOnRide;
			}
			case RUN:
			{
				return _runSpeedOnRide;
			}
			case SLOW_SWIM:
			{
				return _slowSwimSpeedOnRide;
			}
			case FAST_SWIM:
			{
				return _fastSwimSpeedOnRide;
			}
			case SLOW_FLY:
			{
				return _slowFlySpeedOnRide;
			}
			case FAST_FLY:
			{
				return _fastFlySpeedOnRide;
			}
		}
		return 0;
	}
}
