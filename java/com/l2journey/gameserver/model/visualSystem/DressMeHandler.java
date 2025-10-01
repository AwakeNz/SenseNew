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
package com.l2journey.gameserver.model.visualSystem;

/**
 * @author KingHanker
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

import com.l2journey.Config;
import com.l2journey.commons.database.DatabaseFactory;
import com.l2journey.gameserver.model.actor.Player;
import com.l2journey.gameserver.model.item.ItemTemplate;
import com.l2journey.gameserver.model.item.instance.Item;
import com.l2journey.gameserver.model.visualSystem.data.DressMeArmorData;
import com.l2journey.gameserver.model.visualSystem.data.DressMeHatData;
import com.l2journey.gameserver.model.visualSystem.data.DressMeWeaponData;
import com.l2journey.gameserver.model.visualSystem.dataParser.Util;

public class DressMeHandler
{
	private static final Logger LOGGER = Logger.getLogger(DressMeHandler.class.getName());
	
	public static void visuality(Player player, Item item, int visual)
	{
		if (item == null)
		{
			player.sendMessage("No equipped item to apply the " + Util.getItemName(visual) + " visual to.");
			return;
		}
		
		item.setVisualItemId(visual);
		updateVisualInDb(item, visual);
		
		if (visual > 0)
		{
			player.sendMessage(item.getName() + " visual change to " + Util.getItemName(visual));
		}
		else
		{
			player.sendMessage("Visual removed from " + item.getName() + ".");
		}
		
		player.broadcastUserInfo();
	}
	
	public static void updateVisualInDb(Item item, int visual)
	{
		try (Connection con = DatabaseFactory.getConnection();
			PreparedStatement ps = con.prepareStatement("UPDATE items SET visual_item_id=? " + "WHERE object_id = ?"))
		{
			ps.setInt(1, visual);
			ps.setInt(2, item.getObjectId());
			ps.executeUpdate();
		}
		catch (SQLException e)
		{
			if (Config.DEVELOPER)
			{
				LOGGER.info("Could not update DressMe item in DB: Reason: " + e.getMessage());
			}
		}
	}
	
	public static Map<Integer, DressMeWeaponData> initWeaponMap(String type, Item slot)
	{
		Map<Integer, DressMeWeaponData> weaponMap;
		switch (type)
		{
			case "SWORD":
				weaponMap = (slot.getTemplate().getBodyPart() == ItemTemplate.SLOT_LR_HAND) ? DressMeLoader.BIGSWORD : DressMeLoader.SWORD;
				break;
			case "BLUNT":
				weaponMap = (slot.getTemplate().getBodyPart() == ItemTemplate.SLOT_LR_HAND) ? DressMeLoader.BIGBLUNT : DressMeLoader.BLUNT;
				break;
			case "DAGGER":
				weaponMap = DressMeLoader.DAGGER;
				break;
			case "BOW":
				weaponMap = DressMeLoader.BOW;
				break;
			case "POLE":
				weaponMap = DressMeLoader.POLE;
				break;
			case "FIST":
				weaponMap = DressMeLoader.FIST;
				break;
			case "DUAL":
				weaponMap = DressMeLoader.DUAL;
				break;
			case "DUALFIST":
				weaponMap = DressMeLoader.DUALFIST;
				break;
			case "FISHINGROD":
				weaponMap = DressMeLoader.ROD;
				break;
			case "CROSSBOW":
				weaponMap = DressMeLoader.CROSSBOW;
				break;
			case "RAPIER":
				weaponMap = DressMeLoader.RAPIER;
				break;
			case "ANCIENTSWORD":
				weaponMap = DressMeLoader.ANCIENTSWORD;
				break;
			case "DUALDAGGER":
				weaponMap = DressMeLoader.DUALDAGGER;
				break;
			default:
				LOGGER.info("DressMe system: Unknown weapon type: " + type);
				return Collections.emptyMap();
		}
		
		return weaponMap;
	}
	
	public static Map<Integer, DressMeArmorData> initArmorMap(String type)
	{
		switch (type)
		{
			case "LIGHT":
				return DressMeLoader.LIGHT;
			case "HEAVY":
				return DressMeLoader.HEAVY;
			case "ROBE":
				return DressMeLoader.ROBE;
			default:
				LOGGER.info("DressMe system: Unknown armor type: " + type);
				return Collections.emptyMap();
		}
	}
	
	public static Map<Integer, DressMeHatData> initHatMap(Item slot)
	{
		switch (slot.getLocationSlot())
		{
			case 2:
				return slot.getTemplate().getBodyPart() == ItemTemplate.SLOT_HAIRALL ? DressMeLoader.HAIR_FULL : DressMeLoader.HAIR;
			case 3:
				return DressMeLoader.HAIR2;
			default:
				LOGGER.info("DressMe system: Unknown hat slot: " + slot.getLocationSlot());
				return Collections.emptyMap();
		}
	}
}
