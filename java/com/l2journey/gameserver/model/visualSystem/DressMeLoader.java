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

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.l2journey.gameserver.model.visualSystem.data.DressMeArmorData;
import com.l2journey.gameserver.model.visualSystem.data.DressMeHatData;
import com.l2journey.gameserver.model.visualSystem.data.DressMeWeaponData;
import com.l2journey.gameserver.model.visualSystem.dataHolder.DressMeArmorHolder;
import com.l2journey.gameserver.model.visualSystem.dataHolder.DressMeHatHolder;
import com.l2journey.gameserver.model.visualSystem.dataHolder.DressMeWeaponHolder;
import com.l2journey.gameserver.model.visualSystem.dataParser.DressMeArmorParser;
import com.l2journey.gameserver.model.visualSystem.dataParser.DressMeCloakParser;
import com.l2journey.gameserver.model.visualSystem.dataParser.DressMeHatParser;
import com.l2journey.gameserver.model.visualSystem.dataParser.DressMeShieldParser;
import com.l2journey.gameserver.model.visualSystem.dataParser.DressMeWeaponParser;

/**
 * @author KingHanker
 */
public class DressMeLoader
{
	private static final Logger LOGGER = Logger.getLogger(DressMeLoader.class.getName());
	
	public static Map<Integer, DressMeWeaponData> SWORD;
	public static Map<Integer, DressMeWeaponData> BLUNT;
	public static Map<Integer, DressMeWeaponData> DAGGER;
	public static Map<Integer, DressMeWeaponData> BOW;
	public static Map<Integer, DressMeWeaponData> POLE;
	public static Map<Integer, DressMeWeaponData> FIST;
	public static Map<Integer, DressMeWeaponData> DUAL;
	public static Map<Integer, DressMeWeaponData> DUALFIST;
	public static Map<Integer, DressMeWeaponData> BIGSWORD;
	public static Map<Integer, DressMeWeaponData> ROD;
	public static Map<Integer, DressMeWeaponData> BIGBLUNT;
	public static Map<Integer, DressMeWeaponData> CROSSBOW;
	public static Map<Integer, DressMeWeaponData> RAPIER;
	public static Map<Integer, DressMeWeaponData> ANCIENTSWORD;
	public static Map<Integer, DressMeWeaponData> DUALDAGGER;
	
	public static Map<Integer, DressMeArmorData> LIGHT;
	public static Map<Integer, DressMeArmorData> HEAVY;
	public static Map<Integer, DressMeArmorData> ROBE;
	
	public static Map<Integer, DressMeHatData> HAIR;
	public static Map<Integer, DressMeHatData> HAIR2;
	public static Map<Integer, DressMeHatData> HAIR_FULL;
	
	public static void load()
	{
		DressMeArmorParser.getInstance().load();
		DressMeCloakParser.getInstance().load();
		DressMeHatParser.getInstance().load();
		DressMeShieldParser.getInstance().load();
		DressMeWeaponParser.getInstance().load();
		
		SWORD = new HashMap<>();
		BLUNT = new HashMap<>();
		DAGGER = new HashMap<>();
		BOW = new HashMap<>();
		POLE = new HashMap<>();
		FIST = new HashMap<>();
		DUAL = new HashMap<>();
		DUALFIST = new HashMap<>();
		BIGSWORD = new HashMap<>();
		ROD = new HashMap<>();
		BIGBLUNT = new HashMap<>();
		CROSSBOW = new HashMap<>();
		RAPIER = new HashMap<>();
		ANCIENTSWORD = new HashMap<>();
		DUALDAGGER = new HashMap<>();
		
		LIGHT = new HashMap<>();
		HEAVY = new HashMap<>();
		ROBE = new HashMap<>();
		
		HAIR = new HashMap<>();
		HAIR2 = new HashMap<>();
		HAIR_FULL = new HashMap<>();
		
		parseWeapon();
		parseArmor();
		parseHat();
		// VoicedCommandHandler.getInstance().registerHandler(new handlers.voicedcommandhandlers.DressMeVCmd());
	}
	
	private static int parseWeapon()
	{
		int swordCount = 1, bluntCount = 1, daggerCount = 1, bowCount = 1;
		int poleCount = 1, fistCount = 1, dualSwordCount = 1, dualFistCount = 1;
		int bigSwordCount = 1, rodCount = 1, bigBluntCount = 1, crossbowCount = 1;
		int rapierCount = 1, ancientSwordCount = 1, dualDaggerCount = 1;
		
		for (DressMeWeaponData weapon : DressMeWeaponHolder.getInstance().getAllWeapons())
		{
			String type = weapon.getType();
			boolean isBig = weapon.isBig();
			
			switch (type)
			{
				case "SWORD":
					if (!isBig)
					{
						SWORD.put(swordCount++, weapon);
					}
					else
					{
						BIGSWORD.put(bigSwordCount++, weapon);
					}
					break;
				case "BLUNT":
					if (!isBig)
					{
						BLUNT.put(bluntCount++, weapon);
					}
					else
					{
						BIGBLUNT.put(bigBluntCount++, weapon);
					}
					break;
				case "DAGGER":
					DAGGER.put(daggerCount++, weapon);
					break;
				case "BOW":
					BOW.put(bowCount++, weapon);
					break;
				case "POLE":
					POLE.put(poleCount++, weapon);
					break;
				case "FIST":
					FIST.put(fistCount++, weapon);
					break;
				case "DUAL":
					DUAL.put(dualSwordCount++, weapon);
					break;
				case "DUALFIST":
					DUALFIST.put(dualFistCount++, weapon);
					break;
				case "FISHINGROD":
					ROD.put(rodCount++, weapon);
					break;
				case "CROSSBOW":
					CROSSBOW.put(crossbowCount++, weapon);
					break;
				case "RAPIER":
					RAPIER.put(rapierCount++, weapon);
					break;
				case "ANCIENTSWORD":
					ANCIENTSWORD.put(ancientSwordCount++, weapon);
					break;
				case "DUALDAGGER":
					DUALDAGGER.put(dualDaggerCount++, weapon);
					break;
				default:
					LOGGER.info("DressMe system: Can't find type: " + type);
					break;
			}
		}
		
		LOGGER.info("-------------------------------------------------=[ DressMe ]");
		LOGGER.info("DressMe: " + (swordCount - 1) + " Sword(s).");
		LOGGER.info("DressMe: " + (bluntCount - 1) + " Blunt(s).");
		LOGGER.info("DressMe: " + (daggerCount - 1) + " Dagger(s).");
		LOGGER.info("DressMe: " + (bowCount - 1) + " Bow(s).");
		LOGGER.info("DressMe: " + (poleCount - 1) + " Pole(s).");
		LOGGER.info("DressMe: " + (fistCount - 1) + " Fist(s).");
		LOGGER.info("DressMe: " + (dualSwordCount - 1) + " Dual Sword(s).");
		LOGGER.info("DressMe: " + (dualFistCount - 1) + " Dual Fist(s).");
		LOGGER.info("DressMe: " + (bigSwordCount - 1) + " Big Sword(s).");
		LOGGER.info("DressMe: " + (rodCount - 1) + " Rod(s).");
		LOGGER.info("DressMe: " + (bigBluntCount - 1) + " Big Blunt(s).");
		LOGGER.info("DressMe: " + (crossbowCount - 1) + " Crossbow(s).");
		LOGGER.info("DressMe: " + (rapierCount - 1) + " Rapier(s).");
		LOGGER.info("DressMe: " + (ancientSwordCount - 1) + " Ancient Sword(s).");
		LOGGER.info("DressMe: " + (dualDaggerCount - 1) + " Dual Dagger(s).");
		// System.out.println("-------------------------------------------------------------");
		
		return 0;
	}
	
	private static int parseArmor()
	{
		int lightCount = 1, heavyCount = 1, robeCount = 1;
		
		for (DressMeArmorData armor : DressMeArmorHolder.getInstance().getAllDress())
		{
			switch (armor.getType())
			{
				case "LIGHT":
					LIGHT.put(lightCount++, armor);
					break;
				case "HEAVY":
					HEAVY.put(heavyCount++, armor);
					break;
				case "ROBE":
					ROBE.put(robeCount++, armor);
					break;
				default:
					LOGGER.info("DressMe system: Can't find type: " + armor.getType());
					break;
			}
		}
		
		LOGGER.info("DressMe: " + (heavyCount - 1) + " Heavy Armor(s).");
		LOGGER.info("DressMe: " + (lightCount - 1) + " Light Armor(s).");
		LOGGER.info("DressMe: " + (robeCount - 1) + " Robe Armor(s).");
		
		return 0;
	}
	
	private static int parseHat()
	{
		int hairCount = 1, hair2Count = 1, fullHairCount = 1;
		
		for (DressMeHatData hat : DressMeHatHolder.getInstance().getAllHats())
		{
			switch (hat.getSlot())
			{
				case 1:
					HAIR.put(hairCount++, hat);
					break;
				case 2:
					HAIR2.put(hair2Count++, hat);
					break;
				case 3:
					HAIR_FULL.put(fullHairCount++, hat);
					break;
				default:
					LOGGER.info("DressMe system: Can't find slot: " + hat.getSlot());
					break;
			}
		}
		
		LOGGER.info("DressMe: " + (hairCount - 1) + " Hair(s).");
		LOGGER.info("DressMe: " + (hair2Count - 1) + " Hair2(s).");
		LOGGER.info("DressMe: " + (fullHairCount - 1) + " Full Hair(s).");
		// LOGGER.info("-------------------------------------------------------------");
		
		return 0;
	}
}
