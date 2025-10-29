package com.l2journey.gameserver.data.xml;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.l2journey.commons.util.IXmlReader;
import com.l2journey.gameserver.model.stats.Stat;
import com.l2journey.gameserver.model.stats.TraitType;
import com.l2journey.gameserver.model.talent.Talent;
import com.l2journey.gameserver.model.talent.TalentBonusType;
import com.l2journey.gameserver.model.talent.TalentBranch;
import com.l2journey.gameserver.model.talent.TalentTier;

/**
 * Loads and manages talent definitions from XML.
 * @author L2Journey
 */
public class TalentData implements IXmlReader
{
	private static final Logger LOGGER = Logger.getLogger(TalentData.class.getName());

	private final Map<Integer, Talent> _talents = new ConcurrentHashMap<>();

	protected TalentData()
	{
		load();
	}

	@Override
	public void load()
	{
		_talents.clear();
		parseDatapackFile("data/stats/talents/Talents.xml");
		LOGGER.info(getClass().getSimpleName() + ": Loaded " + _talents.size() + " talents.");
	}

	@Override
	public void parseDocument(Document document, File file)
	{
		for (Node n = document.getFirstChild(); n != null; n = n.getNextSibling())
		{
			if ("list".equalsIgnoreCase(n.getNodeName()))
			{
				for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
				{
					if ("talent".equalsIgnoreCase(d.getNodeName()))
					{
						parseTalent(d);
					}
				}
			}
		}
	}

	private void parseTalent(Node talentNode)
	{
		final NamedNodeMap attrs = talentNode.getAttributes();

		final int id = parseInteger(attrs, "id");
		final String name = parseString(attrs, "name");
		final String description = parseString(attrs, "desc", "");
		final TalentBranch branch = TalentBranch.valueOf(parseString(attrs, "branch").toUpperCase());
		final int tier = parseInteger(attrs, "tier");
		final TalentTier talentTier = TalentTier.getByTier(tier);
		final int maxLevel = parseInteger(attrs, "maxLevel", 5);
		final TalentBonusType bonusType = TalentBonusType.valueOf(parseString(attrs, "bonusType").toUpperCase());

		Stat stat = null;
		if (attrs.getNamedItem("stat") != null)
		{
			stat = Stat.valueOf(parseString(attrs, "stat").toUpperCase());
		}

		TraitType traitType = null;
		if (attrs.getNamedItem("trait") != null)
		{
			traitType = TraitType.valueOf(parseString(attrs, "trait").toUpperCase());
		}

		final double valuePerLevel = parseDouble(attrs, "valuePerLevel", 0.0);
		final double percentPerLevel = parseDouble(attrs, "percentPerLevel", 0.0);

		final Talent talent = new Talent(id, name, description, branch, talentTier, maxLevel, bonusType, stat, traitType, valuePerLevel, percentPerLevel);
		_talents.put(id, talent);
	}

	/**
	 * Get a talent by ID.
	 * @param talentId the talent ID
	 * @return the Talent or null if not found
	 */
	public Talent getTalent(int talentId)
	{
		return _talents.get(talentId);
	}

	/**
	 * Get all talents.
	 * @return collection of all talents
	 */
	public Collection<Talent> getTalents()
	{
		return _talents.values();
	}

	/**
	 * Get talents by branch.
	 * @param branch the talent branch
	 * @return collection of talents in the branch
	 */
	public Collection<Talent> getTalentsByBranch(TalentBranch branch)
	{
		return _talents.values().stream()
			.filter(t -> t.getBranch() == branch)
			.toList();
	}

	/**
	 * Get talents by branch and tier.
	 * @param branch the talent branch
	 * @param tier the talent tier
	 * @return collection of talents in the branch and tier
	 */
	public Collection<Talent> getTalentsByBranchAndTier(TalentBranch branch, TalentTier tier)
	{
		return _talents.values().stream()
			.filter(t -> (t.getBranch() == branch) && (t.getTier() == tier))
			.toList();
	}

	/**
	 * Gets the single instance of TalentData.
	 * @return single instance of TalentData
	 */
	public static TalentData getInstance()
	{
		return SingletonHolder.INSTANCE;
	}

	private static class SingletonHolder
	{
		protected static final TalentData INSTANCE = new TalentData();
	}
}
