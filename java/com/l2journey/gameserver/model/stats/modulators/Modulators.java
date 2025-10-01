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
package com.l2journey.gameserver.model.stats.modulators;

/**
 * This file is part of the integrated balancing system created by the L2Journey project<br>
 * Modifiers to final damage
 * @author KingHanker
 */
public class Modulators
{
	public static double MCRIT_REDUCE = 0.7; // 30% reduction for magic critical damage
	
	public static final double SWORD_VS_HEAVY = 0.8; // -20%
	public static final double SWORD_VS_LIGHT = 1.1; // +10%
	public static final double SWORD_VS_ROBE = 1.0; // 0%
	
	public static final double BLUNT_VS_HEAVY = 1.2; // +20%
	public static final double BLUNT_VS_LIGHT = 0.9; // -10%
	public static final double BLUNT_VS_ROBE = 1.0; // 0%
	
	public static final double DAGGER_VS_HEAVY = 0.8; // -20%
	public static final double DAGGER_VS_LIGHT = 1.0; // 0%
	public static final double DAGGER_VS_ROBE = 1.2; // +20%
	
	public static final double BOW_VS_HEAVY = 0.9; // -10%
	public static final double BOW_VS_LIGHT = 1.0; // 0%
	public static final double BOW_VS_ROBE = 1.20; // +20%
	
	public static final double POLE_VS_HEAVY = 1.1; // +10%
	public static final double POLE_VS_LIGHT = 1.0; // 0%
	public static final double POLE_VS_ROBE = 0.95; // -5%
	
	public static final double DUAL_VS_HEAVY = 0.9; // -10%
	public static final double DUAL_VS_LIGHT = 1.15; // +15%
	public static final double DUAL_VS_ROBE = 1.05; // +5%
	
	public static final double FIST_VS_HEAVY = 0.9; // -10%
	public static final double FIST_VS_LIGHT = 1.0; // 0%
	public static final double FIST_VS_ROBE = 1.1; // +10%
	
	public static final double RAPIER_VS_HEAVY = 0.7; // -30%
	public static final double RAPIER_VS_LIGHT = 0.7; // -30%
	public static final double RAPIER_VS_ROBE = 0.7; // -30%
	
	public static final double ANCIENTSWORD_VS_HEAVY = 0.7; // -30%
	public static final double ANCIENTSWORD_VS_LIGHT = 0.7; // -30%
	public static final double ANCIENTSWORD_VS_ROBE = 0.7; // -30%
	
	public static final double CROSSBOW_VS_HEAVY = 0.7; // -30%
	public static final double CROSSBOW_VS_LIGHT = 0.7; // -30%
	public static final double CROSSBOW_VS_ROBE = 0.7; // -30%
	
	public static final double DUALDAGGER_VS_HEAVY = 0.8; // -20%
	public static final double DUALDAGGER_VS_LIGHT = 1.1; // +10%
	public static final double DUALDAGGER_VS_ROBE = 1.2; // +20%
	
	private Modulators()
	{
	}
}
