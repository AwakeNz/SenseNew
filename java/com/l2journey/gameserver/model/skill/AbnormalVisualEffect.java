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
package com.l2journey.gameserver.model.skill;

/**
 * Abnormal Visual Effect enumerated.
 * @author DrHouse, Zoey76
 */
public enum AbnormalVisualEffect
{
	NONE(0x0000000, 0),
	DOT_BLEEDING(0x00000001, 0),
	DOT_POISON(0x00000002, 0),
	DOT_FIRE(0x00000004, 0),
	DOT_WATER(0x00000008, 0),
	DOT_WIND(0x00000010, 0),
	DOT_SOIL(0x00000020, 0),
	STUN(0x00000040, 0),
	SLEEP(0x00000080, 0),
	SILENCE(0x00000100, 0),
	ROOT(0x00000200, 0),
	PARALYZE(0x00000400, 0),
	FLESH_STONE(0x00000800, 0),
	DOT_MP(0x00001000, 0),
	BIG_HEAD(0x00002000, 0),
	DOT_FIRE_AREA(0x00004000, 0),
	CHANGE_TEXTURE(0x00008000, 0),
	BIG_BODY(0x00010000, 0),
	FLOATING_ROOT(0x00020000, 0),
	DANCE_ROOT(0x00040000, 0),
	GHOST_STUN(0x00080000, 0),
	STEALTH(0x00100000, 0),
	SEIZURE1(0x00200000, 0),
	SEIZURE2(0x00400000, 0),
	MAGIC_SQUARE(0x00800000, 0),
	FREEZING(0x01000000, 0),
	SHAKE(0x02000000, 0),
	BLIND(0x04000000, 0),
	ULTIMATE_DEFENCE(0x08000000, 0),
	VP_UP(0x10000000, 0),
	REAL_TARGET(0x20000000, 0),
	DEATH_MARK(0x40000000, 0),
	TURN_FLEE(0x80000000, 0),
	VP_KEEP(0x10000000, 0), // TODO: Find.
	// Special
	INVINCIBILITY(0x000001, 1),
	AIR_BATTLE_SLOW(0x000002, 1),
	AIR_BATTLE_ROOT(0x000004, 1),
	CHANGE_WP(0x000008, 1),
	CHANGE_HAIR_G(0x000010, 1),
	CHANGE_HAIR_P(0x000020, 1),
	CHANGE_HAIR_B(0x000040, 1),
	STIGMA_OF_SILEN(0x000100, 1),
	SPEED_DOWN(0x000200, 1),
	FROZEN_PILLAR(0x000400, 1),
	CHANGE_VES_S(0x000800, 1),
	CHANGE_VES_C(0x001000, 1),
	CHANGE_VES_D(0x002000, 1),
	TIME_BOMB(0x004000, 1), // High Five
	MP_SHIELD(0x008000, 1), // High Five
	NAVIT_ADVENT(0x080000, 1), // High Five
	// Event
	// TODO: Fix, currently not working.
	BR_NONE(0x000000, 2),
	BR_AFRO_NORMAL(0x000001, 2),
	BR_AFRO_PINK(0x000002, 2),
	BR_AFRO_GOLD(0x000004, 2),
	BR_POWER_OF_EVA(0x000008, 2), // High Five
	BR_HEADPHONE(0x000010, 2), // High Five
	BR_VESPER1(0x000020, 2),
	BR_VESPER2(0x000040, 2),
	BR_VESPER3(0x000080, 2),
	BR_SOUL_AVATAR(0x000100, 2); // High Five
	
	/** Int mask. */
	private final int _mask;
	/** Type: 0 Normal, 1 Special, 2 Event. */
	private final int _type;
	
	private AbnormalVisualEffect(int mask, int type)
	{
		_mask = mask;
		_type = type;
	}
	
	/**
	 * Gets the int bitmask for the abnormal visual effect.
	 * @return the int bitmask
	 */
	public int getMask()
	{
		return _mask;
	}
	
	/**
	 * Verify if it's a special abnormal visual effect.
	 * @return {@code true} it's a special abnormal visual effect, {@code false} otherwise
	 */
	public boolean isSpecial()
	{
		return _type == 1;
	}
	
	/**
	 * Verify if it's an event abnormal visual effect.
	 * @return {@code true} it's an event abnormal visual effect, {@code false} otherwise
	 */
	public boolean isEvent()
	{
		return _type == 2;
	}
}