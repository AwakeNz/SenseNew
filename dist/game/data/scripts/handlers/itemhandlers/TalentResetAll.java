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
 */
package handlers.itemhandlers;

import com.l2journey.gameserver.handler.IItemHandler;
import com.l2journey.gameserver.model.actor.Playable;
import com.l2journey.gameserver.model.actor.Player;
import com.l2journey.gameserver.model.item.instance.Item;
import com.l2journey.gameserver.network.SystemMessageId;

/**
 * Handler for Scroll of Oblivion (resets all talent points).
 * @author L2Journey
 */
public class TalentResetAll implements IItemHandler
{
	@Override
	public boolean useItem(Playable playable, Item item, boolean forceUse)
	{
		if (!playable.isPlayer())
		{
			playable.sendPacket(SystemMessageId.YOUR_PET_CANNOT_CARRY_THIS_ITEM);
			return false;
		}

		final Player player = playable.asPlayer();

		// Check if player has any talents learned
		final int spentPoints = player.getTalentHolder().getCurrentTalentTree().getTotalSpentPoints();
		if (spentPoints == 0)
		{
			player.sendMessage("You don't have any learned talents to reset.");
			return false;
		}

		// Consume the item
		if (!player.destroyItem("TalentResetAll", item, 1, null, true))
		{
			return false;
		}

		// Reset all talents and refund points
		final int refundedPoints = player.getTalentHolder().resetTalents();

		// Send confirmation
		player.sendMessage("All your talents have been reset. " + refundedPoints + " talent point(s) refunded.");
		player.sendMessage("Scroll of Oblivion has been consumed.");

		return true;
	}
}
