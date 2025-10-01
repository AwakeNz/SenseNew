/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2journey.tools.dbinstaller;

import java.awt.HeadlessException;
import java.io.File;

import javax.swing.UIManager;

import com.l2journey.tools.dbinstaller.console.DBInstallerConsole;
import com.l2journey.tools.dbinstaller.gui.DBConfigGUI;

/**
 * Contains main class for Database Installer If system doesn't support the graphical UI, start the installer in console mode.
 * @author mrTJO
 */
public class LauncherLS extends AbstractDBLauncher
{
	public static void main(String[] args) throws Exception
	{
		final File file = new File(LauncherLS.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
		final String defDir = file.getParent() + "/sql/login/";
		final String defDatabase = "l2journey";
		
		if ((args != null) && (args.length > 0))
		{
			String dir = getArg("-dir", args);
			if (dir == null)
			{
				dir = defDir;
			}
			
			new DBInstallerConsole(defDatabase, dir, getArg("-h", args), getArg("-p", args), getArg("-u", args), getArg("-pw", args), getArg("-d", args), getArg("-m", args));
			return;
		}
		
		try
		{
			// Set OS Look And Feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			// Ignore.
		}
		
		try
		{
			new DBConfigGUI(defDatabase, defDir);
		}
		catch (HeadlessException e)
		{
			new DBInstallerConsole(defDatabase, defDir);
		}
	}
}
