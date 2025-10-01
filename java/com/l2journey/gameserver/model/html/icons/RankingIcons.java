package com.l2journey.gameserver.model.html.icons;

/**
 * Utility to return icon names according to the ranking position in the tops.
 * @author KingHanker
 */
public class RankingIcons
{
	/**
	 * Returns the icon name according to the ranking position (1 to 10).
	 * @param rank Player's position (1, 2, 3, ...)
	 * @return String with the icon name
	 */
	public static String getIconForRank(int rank)
	{
		switch (rank)
		{
			case 1:
				return "branchSys.br_xmas_pcwin1_i00";
			case 2:
				return "branchSys.br_xmas_pcwin2_i00";
			case 3:
				return "branchSys.br_xmas_pcwin3_i00";
			case 4:
				return "branchSys.br_xmas_pcwin4_i00";
			case 5:
				return "branchSys.br_xmas_pcwin5_i00";
			case 6:
				return "branchSys.br_xmas_pcwin6_i00";
			case 7:
				return "branchSys.br_xmas_pcwin7_i00";
			case 8:
				return "branchSys.br_xmas_pcwin8_i00";
			case 9:
				return "branchSys.br_xmas_pcwin9_i00";
			case 10:
				return "branchSys.br_xmas_pcwin10_i00";
			default:
				return "";
		}
	}
}
