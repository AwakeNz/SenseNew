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
package handlers.admincommandhandlers;

import java.util.StringTokenizer;

import com.l2journey.gameserver.data.xml.TalentData;
import com.l2journey.gameserver.handler.IAdminCommandHandler;
import com.l2journey.gameserver.model.WorldObject;
import com.l2journey.gameserver.model.actor.Player;
import com.l2journey.gameserver.model.talent.Talent;
import com.l2journey.gameserver.model.talent.PlayerTalent;
import com.l2journey.gameserver.model.talent.TalentTree;
import com.l2journey.gameserver.network.SystemMessageId;

/**
 * Admin commands for talent system management and testing.
 * @author L2Journey
 */
public class AdminTalents implements IAdminCommandHandler
{
	private static final String[] ADMIN_COMMANDS =
	{
		"admin_talent_add_points",
		"admin_talent_reset",
		"admin_talent_learn",
		"admin_talent_info",
		"admin_talent_claim"
	};

	@Override
	public boolean useAdminCommand(String command, Player activeChar)
	{
		final StringTokenizer st = new StringTokenizer(command, " ");
		final String actualCommand = st.nextToken();

		// Get target player (self if no target)
		WorldObject target = activeChar.getTarget();
		final Player targetPlayer = (target != null) && target.isPlayer() ? target.asPlayer() : activeChar;

		switch (actualCommand.toLowerCase())
		{
			case "admin_talent_add_points":
			{
				if (!st.hasMoreTokens())
				{
					activeChar.sendMessage("Usage: //talent_add_points <amount>");
					return false;
				}

				try
				{
					final int amount = Integer.parseInt(st.nextToken());
					for (int i = 0; i < amount; i++)
					{
						targetPlayer.getTalentHolder().addPendingTalentPoint();
					}

					activeChar.sendMessage("Added " + amount + " pending talent points to " + targetPlayer.getName());
					targetPlayer.sendMessage("Admin granted you " + amount + " talent points.");
					return true;
				}
				catch (NumberFormatException e)
				{
					activeChar.sendMessage("Invalid number format.");
					return false;
				}
			}

			case "admin_talent_reset":
			{
				final int refunded = targetPlayer.getTalentHolder().resetTalents();
				activeChar.sendMessage("Reset talents for " + targetPlayer.getName() + ". " + refunded + " points refunded.");
				targetPlayer.sendMessage("Your talents have been reset by an admin. " + refunded + " points refunded.");
				return true;
			}

			case "admin_talent_learn":
			{
				if (!st.hasMoreTokens())
				{
					activeChar.sendMessage("Usage: //talent_learn <talent_id>");
					return false;
				}

				try
				{
					final int talentId = Integer.parseInt(st.nextToken());
					final Talent talent = TalentData.getInstance().getTalent(talentId);

					if (talent == null)
					{
						activeChar.sendMessage("Talent ID " + talentId + " not found.");
						return false;
					}

					if (targetPlayer.getTalentHolder().learnTalent(talentId))
					{
						activeChar.sendMessage("Learned talent " + talent.getName() + " for " + targetPlayer.getName());
						targetPlayer.sendMessage("Admin granted you talent: " + talent.getName());
						return true;
					}
					else
					{
						activeChar.sendMessage("Failed to learn talent. Check points, tier unlock, and max level.");
						return false;
					}
				}
				catch (NumberFormatException e)
				{
					activeChar.sendMessage("Invalid talent ID.");
					return false;
				}
			}

			case "admin_talent_info":
			{
				final TalentTree tree = targetPlayer.getTalentHolder().getCurrentTalentTree();
				final int available = targetPlayer.getTalentHolder().getAvailableTalentPoints();
				final int pending = targetPlayer.getTalentHolder().getPendingTalentPoints();
				final int spentPower = tree.getSpentPoints(com.l2journey.gameserver.model.talent.TalentBranch.POWER);
				final int spentMastery = tree.getSpentPoints(com.l2journey.gameserver.model.talent.TalentBranch.MASTERY);
				final int spentProtection = tree.getSpentPoints(com.l2journey.gameserver.model.talent.TalentBranch.PROTECTION);

				activeChar.sendMessage("=== Talent Info for " + targetPlayer.getName() + " ===");
				activeChar.sendMessage("Available Points: " + available);
				activeChar.sendMessage("Pending Points: " + pending);
				activeChar.sendMessage("Power: " + spentPower + "/30");
				activeChar.sendMessage("Mastery: " + spentMastery + "/30");
				activeChar.sendMessage("Protection: " + spentProtection + "/30");
				activeChar.sendMessage("Total Spent: " + tree.getTotalSpentPoints() + "/90");

				if (!tree.getLearnedTalents().isEmpty())
				{
					activeChar.sendMessage("Learned Talents:");
					for (PlayerTalent pt : tree.getLearnedTalents().values())
					{
						activeChar.sendMessage("  - " + pt.getTalent().getName() + " (Lv" + pt.getLevel() + ")");
					}
				}

				return true;
			}

			case "admin_talent_claim":
			{
				final int pending = targetPlayer.getTalentHolder().getPendingTalentPoints();
				if (pending > 0)
				{
					targetPlayer.getTalentHolder().claimPendingTalentPoints();
					activeChar.sendMessage("Claimed " + pending + " pending talent points for " + targetPlayer.getName());
					targetPlayer.sendMessage("Admin claimed your pending talent points. " + pending + " points now available.");
				}
				else
				{
					activeChar.sendMessage(targetPlayer.getName() + " has no pending talent points.");
				}
				return true;
			}

			default:
			{
				activeChar.sendMessage("Unknown talent command. Available commands:");
				activeChar.sendMessage("//talent_add_points <amount> - Add pending talent points");
				activeChar.sendMessage("//talent_reset - Reset all talents");
				activeChar.sendMessage("//talent_learn <id> - Learn a specific talent");
				activeChar.sendMessage("//talent_info - Show talent information");
				activeChar.sendMessage("//talent_claim - Claim pending points");
				return false;
			}
		}
	}

	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}
