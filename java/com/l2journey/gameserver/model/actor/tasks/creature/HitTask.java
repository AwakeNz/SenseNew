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
package com.l2journey.gameserver.model.actor.tasks.creature;

import com.l2journey.gameserver.model.actor.Creature;

/**
 * Task launching the function onHitTimer().<br>
 * <b><u>Actions</u>:</b>
 * <ul>
 * <li>If the attacker/target is dead or use fake death, notify the AI with CANCEL and send a Server->Client packet ActionFailed (if attacker is a Player)</li>
 * <li>If attack isn't aborted, send a message system (critical hit, missed...) to attacker/target if they are Player</li>
 * <li>If attack isn't aborted and hit isn't missed, reduce HP of the target and calculate reflection damage to reduce HP of attacker if necessary</li>
 * <li>if attack isn't aborted and hit isn't missed, manage attack or cast break of the target (calculating rate, sending message...)</li>
 * </ul>
 * @author xban1x
 */
public class HitTask implements Runnable
{
	private final Creature _creature;
	private final Creature _hitTarget;
	private final int _damage;
	private final boolean _crit;
	private final boolean _miss;
	private final byte _shld;
	private final boolean _soulshot;
	private final boolean _rechargeShots;
	
	public HitTask(Creature creature, Creature target, int damage, boolean crit, boolean miss, byte shld, boolean soulshot, boolean rechargeShots)
	{
		_creature = creature;
		_hitTarget = target;
		_damage = damage;
		_crit = crit;
		_shld = shld;
		_miss = miss;
		_soulshot = soulshot;
		_rechargeShots = rechargeShots;
	}
	
	@Override
	public void run()
	{
		if (_creature != null)
		{
			_creature.onHitTimer(_hitTarget, _damage, _crit, _miss, _shld, _soulshot, _rechargeShots);
		}
	}
}
