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
package com.l2journey.gameserver.model.stats;

/**
 * @author UnAfraid, NosBit
 */
public enum TraitType
{
	NONE(0),
	SWORD(1),
	BLUNT(1),
	DAGGER(1),
	POLE(1),
	FIST(1),
	BOW(1),
	ETC(1),
	UNK_8(0),
	POISON(3),
	HOLD(3),
	BLEED(3),
	SLEEP(3),
	SHOCK(3),
	DERANGEMENT(3),
	BUG_WEAKNESS(2),
	ANIMAL_WEAKNESS(2),
	PLANT_WEAKNESS(2),
	BEAST_WEAKNESS(2),
	DRAGON_WEAKNESS(2),
	PARALYZE(3),
	DUAL(1),
	DUALFIST(1),
	BOSS(3),
	GIANT_WEAKNESS(2),
	CONSTRUCT_WEAKNESS(2),
	DEATH(3),
	VALAKAS(2),
	ANESTHESIA(2),
	CRITICAL_POISON(3),
	ROOT_PHYSICALLY(3),
	ROOT_MAGICALLY(3),
	RAPIER(1),
	CROSSBOW(1),
	ANCIENTSWORD(1),
	TURN_STONE(3),
	GUST(3),
	PHYSICAL_BLOCKADE(3),
	TARGET(3),
	PHYSICAL_WEAKNESS(3),
	MAGICAL_WEAKNESS(3),
	DUALDAGGER(1);
	
	private final int _type; // 1 = weapon, 2 = weakness, 3 = resistance
	
	TraitType(int type)
	{
		_type = type;
	}
	
	public int getType()
	{
		return _type;
	}
}