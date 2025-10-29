package com.l2journey.gameserver.model.actor.holders.player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2journey.commons.database.DatabaseFactory;
import com.l2journey.gameserver.data.xml.TalentData;
import com.l2journey.gameserver.model.actor.Player;
import com.l2journey.gameserver.model.talent.Talent;
import com.l2journey.gameserver.model.talent.TalentBranch;
import com.l2journey.gameserver.model.talent.TalentTree;

/**
 * Holds and manages talent data for a player character.
 * Handles talent learning, resetting, and database persistence.
 * @author L2Journey
 */
public class TalentHolder
{
	private static final Logger LOGGER = Logger.getLogger(TalentHolder.class.getName());

	// Max points per branch (15 × 3 branches = 45 total)
	public static final int MAX_POINTS_PER_BRANCH = 15;

	// SQL Queries
	private static final String RESTORE_TALENTS = "SELECT talent_id, talent_level FROM character_talents WHERE charId=? AND class_index=?";
	private static final String RESTORE_TALENT_POINTS = "SELECT available_points, spent_power, spent_mastery, spent_protection, pending_points FROM character_talent_points WHERE charId=?";
	private static final String INSERT_TALENT = "REPLACE INTO character_talents (charId, talent_id, talent_level, class_index) VALUES (?,?,?,?)";
	private static final String DELETE_TALENTS = "DELETE FROM character_talents WHERE charId=? AND class_index=?";
	private static final String DELETE_ALL_TALENTS = "DELETE FROM character_talents WHERE charId=?";
	private static final String UPDATE_TALENT_POINTS = "REPLACE INTO character_talent_points (charId, available_points, spent_power, spent_mastery, spent_protection, pending_points) VALUES (?,?,?,?,?,?)";

	private final Player _player;
	private final Map<Integer, TalentTree> _talentTrees = new ConcurrentHashMap<>(); // key: class_index
	private int _availableTalentPoints = 0;
	private int _pendingTalentPoints = 0; // Points earned but not yet claimed

	public TalentHolder(Player player)
	{
		_player = player;
	}

	/**
	 * Restore talents from database for the current class.
	 */
	public void restoreTalents()
	{
		final int classIndex = _player.getClassIndex();

		try (Connection con = DatabaseFactory.getConnection();
			PreparedStatement ps = con.prepareStatement(RESTORE_TALENTS))
		{
			ps.setInt(1, _player.getObjectId());
			ps.setInt(2, classIndex);

			try (ResultSet rs = ps.executeQuery())
			{
				TalentTree tree = getTalentTree(classIndex);
				while (rs.next())
				{
					final int talentId = rs.getInt("talent_id");
					final int talentLevel = rs.getInt("talent_level");

					final Talent talent = TalentData.getInstance().getTalent(talentId);
					if (talent != null)
					{
						tree.learnTalent(talent, talentLevel);
					}
				}
			}
		}
		catch (Exception e)
		{
			LOGGER.log(Level.SEVERE, "Failed to restore talents for player " + _player.getName(), e);
		}
	}

	/**
	 * Restore talent points from database.
	 */
	public void restoreTalentPoints()
	{
		try (Connection con = DatabaseFactory.getConnection();
			PreparedStatement ps = con.prepareStatement(RESTORE_TALENT_POINTS))
		{
			ps.setInt(1, _player.getObjectId());

			try (ResultSet rs = ps.executeQuery())
			{
				if (rs.next())
				{
					_availableTalentPoints = rs.getInt("available_points");
					_pendingTalentPoints = rs.getInt("pending_points");
				}
			}
		}
		catch (Exception e)
		{
			LOGGER.log(Level.SEVERE, "Failed to restore talent points for player " + _player.getName(), e);
		}
	}

	/**
	 * Store talents to database for the current class.
	 */
	public void storeTalents()
	{
		final int classIndex = _player.getClassIndex();
		final TalentTree tree = _talentTrees.get(classIndex);

		if (tree == null)
		{
			return;
		}

		try (Connection con = DatabaseFactory.getConnection())
		{
			// Delete existing talents for this class
			try (PreparedStatement ps = con.prepareStatement(DELETE_TALENTS))
			{
				ps.setInt(1, _player.getObjectId());
				ps.setInt(2, classIndex);
				ps.execute();
			}

			// Insert current talents
			try (PreparedStatement ps = con.prepareStatement(INSERT_TALENT))
			{
				for (var entry : tree.getLearnedTalents().entrySet())
				{
					ps.setInt(1, _player.getObjectId());
					ps.setInt(2, entry.getKey());
					ps.setInt(3, entry.getValue().getLevel());
					ps.setInt(4, classIndex);
					ps.addBatch();
				}
				ps.executeBatch();
			}
		}
		catch (Exception e)
		{
			LOGGER.log(Level.SEVERE, "Failed to store talents for player " + _player.getName(), e);
		}
	}

	/**
	 * Store talent points to database.
	 */
	public void storeTalentPoints()
	{
		try (Connection con = DatabaseFactory.getConnection();
			PreparedStatement ps = con.prepareStatement(UPDATE_TALENT_POINTS))
		{
			final TalentTree tree = getCurrentTalentTree();

			ps.setInt(1, _player.getObjectId());
			ps.setInt(2, _availableTalentPoints);
			ps.setInt(3, tree != null ? tree.getSpentPoints(TalentBranch.POWER) : 0);
			ps.setInt(4, tree != null ? tree.getSpentPoints(TalentBranch.MASTERY) : 0);
			ps.setInt(5, tree != null ? tree.getSpentPoints(TalentBranch.PROTECTION) : 0);
			ps.setInt(6, _pendingTalentPoints);
			ps.execute();
		}
		catch (Exception e)
		{
			LOGGER.log(Level.SEVERE, "Failed to store talent points for player " + _player.getName(), e);
		}
	}

	/**
	 * Get the talent tree for a specific class index.
	 * @param classIndex the class index
	 * @return the talent tree
	 */
	public TalentTree getTalentTree(int classIndex)
	{
		return _talentTrees.computeIfAbsent(classIndex, k -> new TalentTree(_player, classIndex));
	}

	/**
	 * Get the current talent tree for the active class.
	 * @return the current talent tree
	 */
	public TalentTree getCurrentTalentTree()
	{
		return getTalentTree(_player.getClassIndex());
	}

	/**
	 * Get available talent points.
	 * @return available talent points
	 */
	public int getAvailableTalentPoints()
	{
		return _availableTalentPoints;
	}

	/**
	 * Get pending talent points (earned but not claimed).
	 * @return pending talent points
	 */
	public int getPendingTalentPoints()
	{
		return _pendingTalentPoints;
	}

	/**
	 * Add pending talent points (when player gains 100% XP at level 80+).
	 */
	public void addPendingTalentPoint()
	{
		_pendingTalentPoints++;
	}

	/**
	 * Claim pending talent points.
	 */
	public void claimPendingTalentPoints()
	{
		if (_pendingTalentPoints > 0)
		{
			_availableTalentPoints += _pendingTalentPoints;
			_pendingTalentPoints = 0;
			storeTalentPoints();
		}
	}

	/**
	 * Learn a talent.
	 * @param talentId the talent ID
	 * @return true if successful
	 */
	public boolean learnTalent(int talentId)
	{
		final Talent talent = TalentData.getInstance().getTalent(talentId);
		if (talent == null)
		{
			return false;
		}

		final TalentTree tree = getCurrentTalentTree();
		final int currentLevel = tree.getTalentLevel(talentId);

		// Check if can level up
		if (currentLevel >= talent.getMaxLevel())
		{
			return false;
		}

		// Check if tier is unlocked
		if (!tree.isTierUnlocked(talent.getBranch(), talent.getTier()))
		{
			return false;
		}

		// Check if player has enough points
		if (_availableTalentPoints <= 0)
		{
			return false;
		}

		// Check if branch has reached max points (15 per branch)
		final int branchPoints = tree.getSpentPoints(talent.getBranch());
		if (branchPoints >= MAX_POINTS_PER_BRANCH)
		{
			return false;
		}

		// Learn the talent
		tree.learnTalent(talent, currentLevel + 1);
		_availableTalentPoints--;

		// Store to database
		storeTalents();
		storeTalentPoints();

		// Recalculate stats
		_player.getStat().recalculateStats(true);

		return true;
	}

	/**
	 * Reset all talents for the current class.
	 * @return the number of points refunded
	 */
	public int resetTalents()
	{
		final TalentTree tree = getCurrentTalentTree();
		final int pointsToRefund = tree.getTotalSpentPoints();

		if (pointsToRefund == 0)
		{
			return 0;
		}

		tree.resetTalents();
		_availableTalentPoints += pointsToRefund;

		// Store to database
		storeTalents();
		storeTalentPoints();

		// Recalculate stats
		_player.getStat().recalculateStats(true);

		return pointsToRefund;
	}

	/**
	 * Delete all talent data for this player.
	 */
	public void deleteAllTalents()
	{
		try (Connection con = DatabaseFactory.getConnection())
		{
			try (PreparedStatement ps = con.prepareStatement(DELETE_ALL_TALENTS))
			{
				ps.setInt(1, _player.getObjectId());
				ps.execute();
			}
		}
		catch (Exception e)
		{
			LOGGER.log(Level.SEVERE, "Failed to delete talents for player " + _player.getName(), e);
		}
	}
}
