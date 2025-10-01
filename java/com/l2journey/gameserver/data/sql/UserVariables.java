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
package com.l2journey.gameserver.data.sql;

import java.sql.ResultSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.l2journey.commons.database.DatabaseFactory;
import com.l2journey.gameserver.model.actor.Player;

public class UserVariables
{
	private final Logger LOGGER = Logger.getLogger(UserVariables.class.getName());
	private final Map<String, String> databaseVariablesMap = new ConcurrentHashMap<>();
	private final Map<String, Object> memoryVariablesMap = new ConcurrentHashMap<>();
	
	private final Player player;
	
	public UserVariables(Player player)
	{
		this.player = player;
		loadVariables();
	}
	
	public void setVar(String variableName, String value)
	{
		final String statementQuery = "REPLACE INTO user_variables (obj_id, name, value) VALUES (?,?,?)";
		try (var conn = DatabaseFactory.getConnection();
			var query = conn.prepareStatement(statementQuery))
		{
			query.setInt(1, this.player.getObjectId());
			query.setString(2, variableName);
			query.setString(3, String.valueOf(value));
			query.execute();
			this.databaseVariablesMap.put(variableName, value);
		}
		catch (Exception ex)
		{
			LOGGER.log(Level.WARNING, getClass().getSimpleName() + ": Couldn't update variable '" + variableName + "' for: " + getPlayer(), ex);
		}
	}
	
	public void unsetVar(String variableName)
	{
		if ((variableName == null) || variableName.isEmpty())
		{
			return;
		}
		
		final String statementQuery = "DELETE FROM `user_variables` WHERE `obj_id`=?";
		try (var conn = DatabaseFactory.getConnection();
			var query = conn.prepareStatement(statementQuery))
		{
			query.setInt(1, this.player.getObjectId());
			query.setString(2, variableName);
			query.execute();
			this.databaseVariablesMap.remove(variableName);
		}
		catch (Exception ex)
		{
			LOGGER.log(Level.WARNING, getClass().getSimpleName() + ": Couldn't delete variable '" + variableName + "' for: " + getPlayer(), ex);
		}
	}
	
	public String getVar(String variableName, String defaultValue)
	{
		final String str = this.databaseVariablesMap.get(variableName);
		if ((str == null) || str.isEmpty())
		{
			return defaultValue;
		}
		return str;
	}
	
	public boolean getVarB(String variableName, boolean defaultValue)
	{
		final String str = this.databaseVariablesMap.get(variableName);
		if ((str == null) || str.isEmpty())
		{
			return defaultValue;
		}
		return !str.equals("0") && !str.equalsIgnoreCase("false");
	}
	
	public boolean getVarB(String variableName)
	{
		final String str = this.databaseVariablesMap.get(variableName);
		return (str != null) && !str.isEmpty() && !str.equals("0") && !str.equalsIgnoreCase("false");
	}
	
	public Map<String, String> getVars()
	{
		return this.databaseVariablesMap;
	}
	
	public void loadVariables()
	{
		final String statementQuery = "SELECT name, value FROM user_variables WHERE obj_id = ?";
		try (var conn = DatabaseFactory.getConnection();
			var query = conn.prepareStatement(statementQuery))
		{
			query.setInt(1, this.player.getObjectId());
			ResultSet queryResults = query.executeQuery();
			
			while (queryResults.next())
			{
				setVar(queryResults.getString("name"), queryResults.getString("value"));
			}
		}
		catch (Exception ex)
		{
			LOGGER.log(Level.WARNING, getClass().getSimpleName() + ": Couldn't load variables for: " + getPlayer(), ex);
		}
	}
	
	public void setQuickVar(String variableName, Object value)
	{
		this.memoryVariablesMap.put(variableName, value);
	}
	
	public String getQuickVar(String variableName, String... paramVarArgs)
	{
		if (this.memoryVariablesMap.containsKey(variableName))
		{
			return (String) this.memoryVariablesMap.get(variableName);
		}
		
		if (paramVarArgs.length > 0)
		{
			return paramVarArgs[0];
		}
		
		return null;
	}
	
	public boolean getQuickVarB(String variableName, boolean... paramVarArgs)
	{
		if (this.memoryVariablesMap.containsKey(variableName))
		{
			return ((Boolean) this.memoryVariablesMap.get(variableName)).booleanValue();
		}
		
		if (paramVarArgs.length > 0)
		{
			return paramVarArgs[0];
		}
		
		return false;
	}
	
	public int getQuickVarI(String variableName, int... paramVarArgs)
	{
		if (this.memoryVariablesMap.containsKey(variableName))
		{
			return ((Integer) this.memoryVariablesMap.get(variableName)).intValue();
		}
		
		if (paramVarArgs.length > 0)
		{
			return paramVarArgs[0];
		}
		
		return -1;
	}
	
	public long getQuickVarL(String variableName, long... paramVarArgs)
	{
		if (this.memoryVariablesMap.containsKey(variableName))
		{
			return ((Long) this.memoryVariablesMap.get(variableName)).longValue();
		}
		
		if (paramVarArgs.length > 0)
		{
			return paramVarArgs[0];
		}
		
		return -1L;
	}
	
	public Object getQuickVarO(String variableName, Object... paramVarArgs)
	{
		if (this.memoryVariablesMap.containsKey(variableName))
		{
			return this.memoryVariablesMap.get(variableName);
		}
		
		if (paramVarArgs.length > 0)
		{
			return paramVarArgs[0];
		}
		
		return null;
	}
	
	public boolean containsQuickVar(String variableName)
	{
		return this.memoryVariablesMap.containsKey(variableName);
	}
	
	public void deleteQuickVar(String variableName)
	{
		this.memoryVariablesMap.remove(variableName);
	}
	
	public Player getPlayer()
	{
		return this.player;
	}
}
