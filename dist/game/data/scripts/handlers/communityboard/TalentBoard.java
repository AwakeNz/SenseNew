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
package handlers.communityboard;

import java.text.DecimalFormat;
import java.util.Collection;

import com.l2journey.gameserver.cache.HtmCache;
import com.l2journey.gameserver.data.xml.TalentData;
import com.l2journey.gameserver.handler.CommunityBoardHandler;
import com.l2journey.gameserver.handler.IParseBoardHandler;
import com.l2journey.gameserver.model.actor.Player;
import com.l2journey.gameserver.model.talent.Talent;
import com.l2journey.gameserver.model.talent.TalentBonusType;
import com.l2journey.gameserver.model.talent.TalentBranch;
import com.l2journey.gameserver.model.talent.TalentTier;
import com.l2journey.gameserver.model.talent.PlayerTalent;
import com.l2journey.gameserver.model.talent.TalentTree;

/**
 * Talent Board - Community Board interface for the Talent System.
 * @author L2Journey
 */
public class TalentBoard implements IParseBoardHandler
{
	private static final String[] COMMANDS =
	{
		"_bbstalent",
		"_bbstalent_learn",
		"_bbstalent_reset",
		"_bbstalent_claim",
		"_bbstalent_branch"
	};

	private static final DecimalFormat FORMAT = new DecimalFormat("#.##");

	@Override
	public boolean parseCommunityBoardCommand(String command, Player player)
	{
		// Check if player has 3rd class
		if (!player.getPlayerClass().isThirdClass())
		{
			showError(player, "You must complete your 3rd class change to access the Talent System!");
			return true;
		}

		if (command.equals("_bbstalent"))
		{
			showTalentMain(player);
		}
		else if (command.startsWith("_bbstalent_branch"))
		{
			String branchName = command.substring("_bbstalent_branch ".length());
			TalentBranch branch = TalentBranch.valueOf(branchName.toUpperCase());
			showTalentBranch(player, branch);
		}
		else if (command.startsWith("_bbstalent_learn"))
		{
			int talentId = Integer.parseInt(command.substring("_bbstalent_learn ".length()));
			handleLearnTalent(player, talentId);
		}
		else if (command.equals("_bbstalent_claim"))
		{
			handleClaimPoints(player);
		}
		else if (command.equals("_bbstalent_reset"))
		{
			handleResetTalents(player);
		}

		return true;
	}

	private void showTalentMain(Player player)
	{
		String html = HtmCache.getInstance().getHtm(player, "data/html/CommunityBoard/talent/main.html");

		if (html == null)
		{
			html = generateMainPage(player);
		}
		else
		{
			html = replaceTalentVariables(player, html, null);
		}

		CommunityBoardHandler.separateAndSend(html, player);
	}

	private void showTalentBranch(Player player, TalentBranch branch)
	{
		String html = HtmCache.getInstance().getHtm(player, "data/html/CommunityBoard/talent/branch.html");

		if (html == null)
		{
			html = generateBranchPage(player, branch);
		}
		else
		{
			html = replaceTalentVariables(player, html, branch);
		}

		CommunityBoardHandler.separateAndSend(html, player);
	}

	private void handleLearnTalent(Player player, int talentId)
	{
		if (player.getTalentHolder().learnTalent(talentId))
		{
			Talent talent = TalentData.getInstance().getTalent(talentId);
			showTalentBranch(player, talent.getBranch());
		}
		else
		{
			showError(player, "Cannot learn this talent. Check requirements, tier unlock, and available points.");
		}
	}

	private void handleClaimPoints(Player player)
	{
		int pending = player.getTalentHolder().getPendingTalentPoints();
		if (pending > 0)
		{
			player.getTalentHolder().claimPendingTalentPoints();
			player.sendMessage("You claimed " + pending + " talent point(s)!");
		}
		else
		{
			player.sendMessage("You have no pending talent points.");
		}
		showTalentMain(player);
	}

	private void handleResetTalents(Player player)
	{
		int refunded = player.getTalentHolder().resetTalents();
		if (refunded > 0)
		{
			player.sendMessage("Talents reset! " + refunded + " point(s) refunded.");
		}
		else
		{
			player.sendMessage("You have no talents to reset.");
		}
		showTalentMain(player);
	}

	private String replaceTalentVariables(Player player, String html, TalentBranch branch)
	{
		TalentTree tree = player.getTalentHolder().getCurrentTalentTree();

		// Global variables
		html = html.replace("%available%", String.valueOf(player.getTalentHolder().getAvailableTalentPoints()));
		html = html.replace("%pending%", String.valueOf(player.getTalentHolder().getPendingTalentPoints()));
		html = html.replace("%spent_total%", String.valueOf(tree.getTotalSpentPoints()));

		// Branch specific
		html = html.replace("%spent_power%", String.valueOf(tree.getSpentPoints(TalentBranch.POWER)));
		html = html.replace("%spent_mastery%", String.valueOf(tree.getSpentPoints(TalentBranch.MASTERY)));
		html = html.replace("%spent_protection%", String.valueOf(tree.getSpentPoints(TalentBranch.PROTECTION)));

		// If viewing a specific branch
		if (branch != null)
		{
			html = html.replace("%branch_name%", branch.getName());
			html = html.replace("%branch_spent%", String.valueOf(tree.getSpentPoints(branch)));
			html = html.replace("%talents%", generateTalentList(player, branch));
		}

		return html;
	}

	private String generateTalentList(Player player, TalentBranch branch)
	{
		StringBuilder sb = new StringBuilder();
		TalentTree tree = player.getTalentHolder().getCurrentTalentTree();

		for (TalentTier tier : TalentTier.values())
		{
			boolean unlocked = tree.isTierUnlocked(branch, tier);
			int spentInTier = tree.getSpentPointsInTier(branch, tier);

			// Tier header
			sb.append("<table width=750 border=0 cellpadding=2 cellspacing=0>");
			sb.append("<tr><td width=750 height=30 align=center>");
			sb.append("<font color=\"").append(unlocked ? "00FF00" : "FF0000").append("\">");
			sb.append("Tier ").append(tier.getTier());
			sb.append(unlocked ? " [UNLOCKED]" : " [LOCKED - Need " + (10 - spentInTier) + " more points in previous tier]");
			sb.append("</font>");
			sb.append("</td></tr></table>");

			// Talents in this tier
			Collection<Talent> talents = TalentData.getInstance().getTalentsByBranchAndTier(branch, tier);

			for (Talent talent : talents)
			{
				int currentLevel = tree.getTalentLevel(talent.getId());
				boolean canLearn = unlocked && (currentLevel < talent.getMaxLevel()) &&
				                  (player.getTalentHolder().getAvailableTalentPoints() > 0);

				sb.append(generateTalentRow(player, talent, currentLevel, canLearn));
			}
		}

		return sb.toString();
	}

	private String generateTalentRow(Player player, Talent talent, int currentLevel, boolean canLearn)
	{
		StringBuilder sb = new StringBuilder();

		sb.append("<table width=750 border=0 cellpadding=2 cellspacing=0 bgcolor=").append(currentLevel > 0 ? "333333" : "222222").append(">");
		sb.append("<tr>");

		// Talent name and level
		sb.append("<td width=300 height=25>");
		sb.append("<font color=\"").append(currentLevel > 0 ? "LEVEL" : "FFFFFF").append("\">");
		sb.append(talent.getName());
		sb.append("</font>");
		sb.append("</td>");

		// Level indicator [■■■□□]
		sb.append("<td width=150 align=center>");
		for (int i = 1; i <= talent.getMaxLevel(); i++)
		{
			if (i <= currentLevel)
			{
				sb.append("<font color=\"00FF00\">■</font>");
			}
			else
			{
				sb.append("<font color=\"666666\">□</font>");
			}
		}
		sb.append(" (").append(currentLevel).append("/").append(talent.getMaxLevel()).append(")");
		sb.append("</td>");

		// Bonus display
		sb.append("<td width=200 align=center>");
		sb.append("<font color=\"LEVEL\">");
		if (currentLevel > 0)
		{
			sb.append(getTalentBonusText(talent, currentLevel));
		}
		else
		{
			sb.append("Not learned");
		}
		sb.append("</font>");
		sb.append("</td>");

		// Learn button
		sb.append("<td width=100 align=center>");
		if (canLearn)
		{
			sb.append("<button value=\"Learn\" action=\"bypass _bbstalent_learn ").append(talent.getId()).append("\" width=75 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">");
		}
		else if (currentLevel >= talent.getMaxLevel())
		{
			sb.append("<font color=\"FFD700\">MAXED</font>");
		}
		else
		{
			sb.append("<font color=\"666666\">---</font>");
		}
		sb.append("</td>");

		sb.append("</tr>");

		// Talent description
		sb.append("<tr><td colspan=4 height=15>");
		sb.append("<font color=\"B09878\">").append(talent.getDescription()).append("</font>");
		sb.append("</td></tr>");

		sb.append("</table><br>");

		return sb.toString();
	}

	private String getTalentBonusText(Talent talent, int level)
	{
		StringBuilder sb = new StringBuilder();

		switch (talent.getBonusType())
		{
			case FIXED:
				sb.append("+").append((int) talent.getFixedValueBonus(level));
				break;
			case PERCENT:
				sb.append("+").append(FORMAT.format(talent.getPercentBonus(level) * 100)).append("%");
				break;
			case COMBINED:
				sb.append("+").append((int) talent.getFixedValueBonus(level));
				sb.append(" & +").append(FORMAT.format(talent.getPercentBonus(level) * 100)).append("%");
				break;
			case ATTACK_TRAIT:
			case DEFENCE_TRAIT:
				sb.append("+").append(FORMAT.format(talent.getPercentBonus(level) * 100)).append("%");
				break;
			case SPECIAL:
				sb.append("Special Effect");
				break;
		}

		return sb.toString();
	}

	private void showError(Player player, String message)
	{
		String html = "<html><body><br><br><center>";
		html += "<font color=\"FF0000\">ERROR</font><br1>";
		html += message;
		html += "<br><br><button value=\"Back\" action=\"bypass _bbstalent\" width=75 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">";
		html += "</center></body></html>";

		CommunityBoardHandler.separateAndSend(html, player);
	}

	private String generateMainPage(Player player)
	{
		TalentTree tree = player.getTalentHolder().getCurrentTalentTree();

		StringBuilder html = new StringBuilder();
		html.append("<html><body><br>");
		html.append("<center>");
		html.append("<font color=\"LEVEL\">═══ Talent System ═══</font><br1>");
		html.append("<table width=750>");

		// Points display
		html.append("<tr><td width=250 align=center>Available Points: <font color=\"00FF00\">").append(player.getTalentHolder().getAvailableTalentPoints()).append("</font></td>");
		html.append("<td width=250 align=center>Pending Points: <font color=\"FFFF00\">").append(player.getTalentHolder().getPendingTalentPoints()).append("</font></td>");
		html.append("<td width=250 align=center>Total Spent: <font color=\"LEVEL\">").append(tree.getTotalSpentPoints()).append("/45</font></td></tr>");
		html.append("</table><br>");

		// Claim/Reset buttons
		html.append("<table width=750><tr>");
		html.append("<td width=375 align=center><button value=\"Claim Pending Points\" action=\"bypass _bbstalent_claim\" width=150 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>");
		html.append("<td width=375 align=center><button value=\"Reset All Talents\" action=\"bypass _bbstalent_reset\" width=150 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td>");
		html.append("</tr></table><br1>");

		// Branch selection
		for (TalentBranch branch : TalentBranch.values())
		{
			int spent = tree.getSpentPoints(branch);
			html.append("<table width=750 border=0 cellpadding=5 cellspacing=0 bgcolor=333333>");
			html.append("<tr><td width=500>");
			html.append("<font color=\"LEVEL\">").append(branch.getName()).append(" Branch</font><br>");
			html.append("<font color=\"B09878\">Points Spent: ").append(spent).append("/15</font>");
			html.append("</td>");
			html.append("<td width=250 align=center>");
			html.append("<button value=\"View Talents\" action=\"bypass _bbstalent_branch ").append(branch.name().toLowerCase()).append("\" width=120 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">");
			html.append("</td></tr></table><br>");
		}

		html.append("</center></body></html>");

		return html.toString();
	}

	private String generateBranchPage(Player player, TalentBranch branch)
	{
		TalentTree tree = player.getTalentHolder().getCurrentTalentTree();

		StringBuilder html = new StringBuilder();
		html.append("<html><body><br>");
		html.append("<center>");
		html.append("<font color=\"LEVEL\">").append(branch.getName()).append(" Branch</font><br1>");
		html.append("<table width=750>");
		html.append("<tr><td width=250>Available Points: <font color=\"00FF00\">").append(player.getTalentHolder().getAvailableTalentPoints()).append("</font></td>");
		html.append("<td width=250>Branch Spent: <font color=\"LEVEL\">").append(tree.getSpentPoints(branch)).append("/15</font></td>");
		html.append("<td width=250><button value=\"Back\" action=\"bypass _bbstalent\" width=75 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></td></tr>");
		html.append("</table><br>");

		html.append(generateTalentList(player, branch));

		html.append("</center></body></html>");

		return html.toString();
	}

	@Override
	public String[] getCommunityBoardCommands()
	{
		return COMMANDS;
	}
}
