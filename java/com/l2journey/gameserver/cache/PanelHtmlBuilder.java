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
package com.l2journey.gameserver.cache;

import com.l2journey.Config;
import com.l2journey.gameserver.model.actor.Player;
import com.l2journey.gameserver.model.variables.Statistics;

public class PanelHtmlBuilder
{
	private final Player player;
	private final StringBuilder builder = new StringBuilder();
	
	private static final String FONT_COLOR_ACTIVE = "00FA9A";
	private static final String FONT_COLOR_VALUE = "FFE4E1";
	
	public PanelHtmlBuilder(Player player)
	{
		this.player = player;
	}
	
	public String build()
	{
		startHtml();
		addMainOptions();
		addOptionalFeatures();
		addStatistics();
		endHtml();
		return builder.toString();
	}
	
	private void startHtml()
	{
		builder.append("<html><title>User Panel</title><body>");
		builder.append("<table width=245 height=135>");
		builder.append("<tr><td valign=\"top\" align=\"center\">");
		builder.append("<table>");
		builder.append("<tr><td><center>");
		builder.append("<table width=245 height=135>");
		builder.append("<tr><td width=250 height=3 align=center><img src=\"L2UI.SquareBlank\"></td></tr>");
		builder.append("<tr><td><table width=265 background=\"l2ui_ct1.ComboBox_DF_Dropmenu_Bg\"><tr>");
		builder.append("<td width=110 align=\"center\"><font name=hs8 name=CreditTextNormal color=ae9977>Options</font></td>");
		builder.append("<td width=60 align=\"center\"><font name=hs8 name=CreditTextNormal color=ae9977>Action</font></td>");
		builder.append("<td width=60 align=\"center\"><font name=hs8 name=CreditTextSmall color=ae9977>Status</font></td></tr>");
		builder.append("</table></td></tr>");
		builder.append("<tr><td>");
		builder.append("<table border=0 width=270>");
	}
	
	private void addOption(String name, String command, boolean isActive)
	{
		builder.append("<tr>");
		builder.append("<td width=110><font color=").append(FONT_COLOR_ACTIVE).append(">").append(name).append("</font></td>");
		builder.append("<td width=60><button value=\"Change\" action=\"bypass -h voice .").append(command).append("\" width=60 height=21 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_ct1.button_df\"></td>");
		
		builder.append("<td width=70 align=center valign=center>");
		builder.append("<table cellpadding=0 cellspacing=0>");
		builder.append("<tr><td height=10></td></tr>");
		builder.append("<tr>");
		builder.append("<td width=4 valign=top><img src=\"L2UI_CT1.Gauge_DF_Large_").append(isActive ? "MP" : "HP").append("_Left\" width=4 height=14></td>");
		builder.append("<td width=52><img src=\"L2UI_CT1.Gauge_DF_Large_").append(isActive ? "MP" : "HP").append("_Center\" width=52 height=14></td>");
		builder.append("<td width=4 valign=top><img src=\"L2UI_CT1.Gauge_DF_Large_").append(isActive ? "MP" : "HP").append("_Right\" width=4 height=14></td>");
		builder.append("</tr>");
		builder.append("</table>");
		builder.append("</td></tr>");
	}
	
	private void addMainOptions()
	{
		addOption("Block Trade", "tradeprot", player.getVarB("noTrade"));
		addOption("Block Experience", "changeexp", player.getVarB("noExp"));
		addOption("Enchant Animation", "enchantanime", player.getVarB("showEnchantAnime"));
	}
	
	private void addOptionalFeature(String name, String command)
	{
		builder.append("<center>");
		builder.append("<table width=260>");
		builder.append("<tr>");
		builder.append("<td width=195><font color=").append(FONT_COLOR_ACTIVE).append(">").append(name).append("</font></td>");
		builder.append("<td width=30 align=\"center\"><button value=\"").append(name.split(" ")[0]).append("\" action=\"bypass -h voice .").append(command).append("\" width=70 height=21 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_ct1.button_df\"></td>");
		builder.append("<td></td>");
		builder.append("</tr>");
		builder.append("</table>");
		builder.append("<br1>");
	}
	
	private void addOptionalFeatures()
	{
		builder.append("</table>");
		builder.append("<br1>");
		
		if (Config.EPIC_COMMAND)
		{
			addOptionalFeature("Boss Status", "epic");
		}
		if (Config.ENABLE_AUTO_PLAY)
		{
			addOptionalFeature("Auto Play", "play");
		}
		if (Config.COMBINETALISMANS_COMMAND)
		{
			addOptionalFeature("Combine Talismans", "combinetalismans");
		}
		if (Config.DRESSME_ENABLE)
		{
			addOptionalFeature("Visual system", "dressme");
		}
		if (Config.ALLOW_CHANGE_PASSWORD)
		{
			addOptionalFeature("Change Password", "changepassword");
		}
		
		if (Config.MULTILANG_ENABLE)
		{
			addLanguageSection();
		}
	}
	
	private void addLanguageSection()
	{
		builder.append("<center>");
		builder.append("<table width=275 height=23>");
		builder.append("<tr>");
		builder.append("<td background=\"l2ui_ct1.ComboBox_DF_Dropmenu_Bg\" align=\"center\"><font name=hs11 name=CreditTextNormal color=B59A75>Multilingual Support</font></td>");
		builder.append("</tr>");
		builder.append("</table>");
		
		builder.append("<table width=260>");
		builder.append("<tr>");
		builder.append("<td width=70><font color=").append(FONT_COLOR_ACTIVE).append(">Language</font></td>");
		builder.append("<td width=122 align=\"center\"><font color=").append(FONT_COLOR_VALUE).append(">").append(player.getLang()).append("</font></td>");
		builder.append("<td width=1 align=\"center\"><button value=\"Change\" action=\"bypass -h voice .lang\" width=70 height=21 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_ct1.button_df\"></td>");
		builder.append("<td></td>");
		builder.append("</tr>");
		builder.append("</table>");
	}
	
	private void addStatistics()
	{
		if (Config.STATISTIC_PANEL)
		{
			builder.append("<table width=280 height=23>");
			builder.append("<tr>");
			builder.append("<td background=\"l2ui_ct1.ComboBox_DF_Dropmenu_Bg\" align=center><font name=hs11 name=CreditTextNormal color=B59A75>Server Statistics</font></td>");
			builder.append("</tr>");
			builder.append("</table>");
			
			builder.append("<table width=280>");
			builder.append("<tr>");
			builder.append("<td width=15></td>");
			builder.append("<td></td>");
			builder.append("<td></td>");
			builder.append("</tr>");
			builder.append("<tr>");
			builder.append("<td width=11></td>");
			builder.append("<td width=274><font color=").append(FONT_COLOR_ACTIVE).append(">Players Online</font></td>");
			builder.append("<td width=470 align=\"center\"><font color=").append(FONT_COLOR_VALUE).append(">").append(Statistics.getTotalPlayersOn()).append("</font></td>");
			builder.append("</tr>");
			builder.append("<tr>");
			builder.append("<td width=11></td>");
			builder.append("<td width=274><font color=").append(FONT_COLOR_ACTIVE).append(">Active Players</font></td>");
			builder.append("<td width=470 align=\"center\"><font color=").append(FONT_COLOR_VALUE).append(">").append(Statistics.getRealOnline()).append("</font></td>");
			builder.append("</tr>");
			builder.append("<tr>");
			builder.append("<td>");
			builder.append("<td width=11></td>");
			builder.append("<td width=274><font color=").append(FONT_COLOR_ACTIVE).append(">Offline Shop</font></td>");
			builder.append("<td width=274 align=\"center\"><font color=").append(FONT_COLOR_VALUE).append(">").append(Statistics.getOffShops()).append("</font></td>");
			builder.append("</tr><tr><td height=5></td></tr></table>");
		}
	}
	
	private void endHtml()
	{
		builder.append("</td></tr>");
		builder.append("</table><br>");
		builder.append("</body></html>");
	}
}