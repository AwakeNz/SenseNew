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
package handlers.actionhandlers;

import com.l2journey.Config;
import com.l2journey.commons.util.Rnd;
import com.l2journey.gameserver.ai.Intention;
import com.l2journey.gameserver.geoengine.GeoEngine;
import com.l2journey.gameserver.handler.IActionHandler;
import com.l2journey.gameserver.model.WorldObject;
import com.l2journey.gameserver.model.actor.Npc;
import com.l2journey.gameserver.model.actor.Player;
import com.l2journey.gameserver.model.actor.enums.creature.InstanceType;
import com.l2journey.gameserver.model.events.EventDispatcher;
import com.l2journey.gameserver.model.events.EventType;
import com.l2journey.gameserver.model.events.holders.actor.npc.OnNpcFirstTalk;
import com.l2journey.gameserver.network.serverpackets.MoveToPawn;

public class NpcAction implements IActionHandler
{
	/**
	 * Manage actions when a player click on the Npc.<br>
	 * <br>
	 * <b><u>Actions on first click on the Npc (Select it)</u>:</b><br>
	 * <li>Set the Npc as target of the Player player (if necessary)</li>
	 * <li>Send a Server->Client packet MyTargetSelected to the Player player (display the select window)</li>
	 * <li>If Npc is autoAttackable, send a Server->Client packet StatusUpdate to the Player in order to update Npc HP bar</li>
	 * <li>Send a Server->Client packet ValidateLocation to correct the Npc position and heading on the client</li><br>
	 * <br>
	 * <b><u>Actions on second click on the Npc (Attack it/Intercat with it)</u>:</b><br>
	 * <li>Send a Server->Client packet MyTargetSelected to the Player player (display the select window)</li>
	 * <li>If Npc is autoAttackable, notify the Player AI with ATTACK (after a height verification)</li>
	 * <li>If Npc is NOT autoAttackable, notify the Player AI with INTERACT (after a distance verification) and show message</li><br>
	 * <font color=#FF0000><b><u>Caution</u>: Each group of Server->Client packet must be terminated by a ActionFailed packet in order to avoid that client wait an other packet</b></font><br>
	 * <br>
	 * <b><u>Example of use</u>:</b><br>
	 * <li>Client packet : Action, AttackRequest</li><br>
	 * @param player The Player that start an action on the Npc
	 */
	@Override
	public boolean action(Player player, WorldObject target, boolean interact)
	{
		final Npc npc = target.asNpc();
		if (!npc.canTarget(player))
		{
			return false;
		}
		
		player.setLastFolkNPC(npc);
		// Check if the Player already target the Npc
		if (target != player.getTarget())
		{
			// Set the target of the Player player
			player.setTarget(target);
			// Check if the player is attackable (without a forced attack)
			if (target.isAutoAttackable(player))
			{
				npc.getAI(); // wake up ai
			}
		}
		else if (interact)
		{
			// Check if the player is attackable (without a forced attack) and isn't dead
			if (target.isAutoAttackable(player) && !target.asCreature().isAlikeDead())
			{
				player.getAI().setIntention(Intention.ATTACK, target);
			}
			else if (!target.isAutoAttackable(player))
			{
				// Calculate the distance between the Player and the Npc
				if (!npc.canInteract(player))
				{
					player.getAI().setIntention(Intention.INTERACT, target);
				}
				else
				{
					// If player is moving and NPC is in interaction distance, change the intention to stop moving.
					if (player.isMoving() && npc.isInsideRadius3D(player, Npc.INTERACTION_DISTANCE))
					{
						player.getAI().setIntention(Intention.ACTIVE);
					}
					
					// Turn NPC to the player.
					player.sendPacket(new MoveToPawn(player, npc, 100));
					if (npc.hasRandomAnimation())
					{
						npc.onRandomAnimation(Rnd.get(8));
					}
					
					// Stop movement when trying to talk to a moving NPC.
					if (npc.isMoving())
					{
						player.stopMove(null);
					}
					
					// Open a chat window on client with the text of the Npc
					if (npc.hasListener(EventType.ON_NPC_QUEST_START))
					{
						player.setLastQuestNpcObject(target.getObjectId());
					}
					if (npc.hasListener(EventType.ON_NPC_FIRST_TALK))
					{
						EventDispatcher.getInstance().notifyEventAsync(new OnNpcFirstTalk(npc, player), npc);
					}
					else
					{
						npc.showChatWindow(player);
					}
					
					if (Config.PLAYER_MOVEMENT_BLOCK_TIME > 0)
					{
						player.updateNotMoveUntil();
					}
					if (npc.isFakePlayer() && GeoEngine.getInstance().canSeeTarget(player, npc))
					{
						player.getAI().setIntention(Intention.FOLLOW, npc);
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public InstanceType getInstanceType()
	{
		return InstanceType.Npc;
	}
}