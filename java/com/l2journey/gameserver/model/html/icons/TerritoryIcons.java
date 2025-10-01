package com.l2journey.gameserver.model.html.icons;

/**
 * Utility class to return the territory/castle insignia icon name by its ID or castle name.
 * @author KingHanker
 */
public class TerritoryIcons
{
	/**
	 * Returns the icon name for the territory/castle insignia by its ID.
	 * @param territoryId Territory or castle ID
	 * @return Icon path for HTML usage
	 */
	public static String getIcon(int territoryId)
	{
		switch (territoryId)
		{
			case 1049: // Gludio
			case 911:
			case 912:
			case 910: // Gludin
				return "L2UI_CT1.Minimap_DF_ICN_TerritoryWar_Gludio";
			case 1052: // Dion
			case 916:
				return "L2UI_CT1.Minimap_DF_ICN_TerritoryWar_Dion";
			case 1053: // Giran
			case 918:
				return "L2UI_CT1.Minimap_DF_ICN_TerritoryWar_Giran";
			case 1054: // Oren
				return "L2UI_CT1.Minimap_DF_ICN_TerritoryWar_Oren";
			case 1055: // Innadril
			case 919: // Heine
				return "L2UI_CT1.Minimap_DF_ICN_TerritoryWar_Innadril";
			case 1057: // Rune
			case 1537:
				return "L2UI_CT1.Minimap_DF_ICN_TerritoryWar_Rune";
			case 1060: // Goddard
			case 1538:
				return "L2UI_CT1.Minimap_DF_ICN_TerritoryWar_Godard";
			case 1059: // Schuttgart
			case 1714:
				return "L2UI_CT1.Minimap_DF_ICN_TerritoryWar_Schuttgart";
			case 1248: // Aden
				return "L2UI_CT1.Minimap_DF_ICN_TerritoryWar_Aden";
			default:
				return "L2UI_CT1.Minimap_DF_ICN_TerritoryWar_Aden";
		}
	}
	
	/**
	 * Returns the territory icon by castle name.
	 * @param castleName The name of the castle
	 * @return Icon path for HTML usage
	 */
	public static String getIconByCastleName(String castleName)
	{
		switch (castleName.toLowerCase())
		{
			case "gludio":
			case "gludin":
				return "L2UI_CT1.Minimap_DF_ICN_TerritoryWar_Gludio";
			case "dion":
				return "L2UI_CT1.Minimap_DF_ICN_TerritoryWar_Dion";
			case "giran":
				return "L2UI_CT1.Minimap_DF_ICN_TerritoryWar_Giran";
			case "oren":
				return "L2UI_CT1.Minimap_DF_ICN_TerritoryWar_Oren";
			case "innadril":
			case "heine":
				return "L2UI_CT1.Minimap_DF_ICN_TerritoryWar_Innadril";
			case "rune":
				return "L2UI_CT1.Minimap_DF_ICN_TerritoryWar_Rune";
			case "goddard":
				return "L2UI_CT1.Minimap_DF_ICN_TerritoryWar_Godard";
			case "schuttgart":
				return "L2UI_CT1.Minimap_DF_ICN_TerritoryWar_Schuttgart";
			case "aden":
				return "L2UI_CT1.Minimap_DF_ICN_TerritoryWar_Aden";
			default:
				return "L2UI_CT1.Minimap_DF_ICN_TerritoryWar_Aden";
		}
	}
}
