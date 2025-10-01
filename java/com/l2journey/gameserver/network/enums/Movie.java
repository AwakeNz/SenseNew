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
package com.l2journey.gameserver.network.enums;

/**
 * This file contains all movies.
 * @author St3eT
 */
public enum Movie
{
	SC_LINDVIOR(1),
	SC_ECHMUS_OPENING(2),
	SC_ECHMUS_SUCCESS(3),
	SC_ECHMUS_FAIL(4),
	SC_BOSS_TIAT_OPENING(5),
	SC_BOSS_TIAT_ENDING_SUCCES(6),
	SC_BOSS_TIAT_ENDING_FAIL(7),
	SSQ_SUSPICIOUS_DEATHS(8),
	SSQ_DYING_MASSAGE(9),
	SSQ_CONTRACT_OF_MAMMON(10),
	SSQ_RITUAL_OF_PRIEST(11),
	SSQ_SEALING_EMPEROR_1ST(12),
	SSQ_SEALING_EMPEROR_2ND(13),
	SSQ_EMBRYO(14),
	SC_BOSS_FREYA_OPENING(15),
	SC_BOSS_FREYA_PHASECH_A(16),
	SC_BOSS_FREYA_PHASECH_B(17),
	SC_BOSS_KEGOR_INTRUSION(18),
	SC_BOSS_FREYA_ENDING_A(19),
	SC_BOSS_FREYA_ENDING_B(20),
	SC_BOSS_FREYA_FORCED_DEFEAT(21),
	SC_BOSS_FREYA_DEFEAT(22),
	SC_ICE_HEAVYKNIGHT_SPAWN(23),
	SSQ2_HOLY_BURIAL_GROUND_OPENING(24),
	SSQ2_HOLY_BURIAL_GROUND_CLOSING(25),
	SSQ2_SOLINA_TOMB_OPENING(26),
	SSQ2_SOLINA_TOMB_CLOSING(27),
	SSQ2_ELYSS_NARRATION(28),
	SSQ2_BOSS_OPENING(29),
	SSQ2_BOSS_CLOSING(30),
	LAND_KSERTH_A(1000),
	LAND_KSERTH_B(1001),
	LAND_UNDEAD_A(1002),
	LAND_DISTRUCTION_A(1003),
	LAND_ANNIHILATION_A(1004);
	
	private final int _clientId;
	
	private Movie(int clientId)
	{
		_clientId = clientId;
	}
	
	/**
	 * @return the client id.
	 */
	public int getClientId()
	{
		return _clientId;
	}
}