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
package quests.TerritoryWarScripts;

import com.l2journey.gameserver.network.NpcStringId;

/**
 * Make Spears Dull! (735)
 * @author Gigiikun
 */
public class Q00735_MakeSpearsDull extends TerritoryWarSuperClass
{
	public Q00735_MakeSpearsDull()
	{
		super(735);
		CLASS_IDS = new int[]
		{
			23,
			101,
			36,
			108,
			8,
			93,
			2,
			88,
			3,
			89,
			48,
			114,
			46,
			113,
			55,
			117,
			9,
			92,
			24,
			102,
			37,
			109,
			34,
			107,
			21,
			100,
			127,
			131,
			128,
			132,
			129,
			133,
			130,
			134,
			135,
			136
		};
		RANDOM_MIN = 15;
		RANDOM_MAX = 20;
		npcString = new NpcStringId[]
		{
			NpcStringId.YOU_HAVE_DEFEATED_S2_OF_S1_WARRIORS_AND_ROGUES,
			NpcStringId.YOU_WEAKENED_THE_ENEMY_S_ATTACK
		};
	}
}
