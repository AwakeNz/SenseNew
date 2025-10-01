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
package com.l2journey.gameserver.model.skill.targets;

/**
 * Affect scope enumerated.
 * @author Zoey76
 */
public enum AffectScope
{
	/** Affects Valakas. */
	VALAKAS_SCOPE,
	/** Affects dead clan mates. */
	DEAD_PLEDGE,
	/** Affects fan area. */
	FAN,
	/** Affects nothing. */
	NONE,
	/** Affects party members. */
	PARTY,
	/** Affects party and clan mates. */
	PARTY_PLEDGE,
	/** Affects clan mates. */
	PLEDGE,
	/** Affects point blank targets, using caster as point of origin. */
	POINT_BLANK,
	/** Affects ranged targets, using selected target as point of origin. */
	RANGE,
	/** Affects ranged targets, using selected target as point of origin. */
	RING_RANGE,
	/** Affects a single target. */
	SINGLE,
	/** Affects targets inside an square area, using selected target as point of origin. */
	SQUARE,
	/** Affects targets inside an square area, using caster as point of origin. */
	SQUARE_PB,
	/** Affects static object targets. */
	STATIC_OBJECT_SCOPE,
	/** Affects wyverns. */
	WYVERN_SCOPE
}
