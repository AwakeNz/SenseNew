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
package com.l2journey.gameserver.network.serverpackets;

import java.util.List;

import com.l2journey.commons.network.WritableBuffer;
import com.l2journey.gameserver.model.ActionKey;
import com.l2journey.gameserver.model.UIKeysSettings;
import com.l2journey.gameserver.model.actor.Player;
import com.l2journey.gameserver.network.GameClient;
import com.l2journey.gameserver.network.ServerPackets;

/**
 * @author mrTJO
 */
public class ExUISetting extends ServerPacket
{
	private final UIKeysSettings _uiSettings;
	private int buffsize;
	private int categories;
	
	public ExUISetting(Player player)
	{
		_uiSettings = player.getUISettings();
		calcSize();
	}
	
	private void calcSize()
	{
		int size = 16; // initial header and footer
		int category = 0;
		final int numKeyCt = _uiSettings.getKeys().size();
		for (int i = 0; i < numKeyCt; i++)
		{
			size++;
			if (_uiSettings.getCategories().containsKey(category))
			{
				final List<Integer> catElList1 = _uiSettings.getCategories().get(category);
				size += catElList1.size();
			}
			category++;
			size++;
			if (_uiSettings.getCategories().containsKey(category))
			{
				final List<Integer> catElList2 = _uiSettings.getCategories().get(category);
				size += catElList2.size();
			}
			category++;
			size += 4;
			if (_uiSettings.getKeys().containsKey(i))
			{
				final List<ActionKey> keyElList = _uiSettings.getKeys().get(i);
				size += (keyElList.size() * 20);
			}
		}
		buffsize = size;
		categories = category;
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		ServerPackets.EX_UI_SETTING.writeId(this, buffer);
		buffer.writeInt(buffsize);
		buffer.writeInt(categories);
		int category = 0;
		final int numKeyCt = _uiSettings.getKeys().size();
		buffer.writeInt(numKeyCt);
		for (int i = 0; i < numKeyCt; i++)
		{
			if (_uiSettings.getCategories().containsKey(category))
			{
				final List<Integer> catElList1 = _uiSettings.getCategories().get(category);
				buffer.writeByte(catElList1.size());
				for (int cmd : catElList1)
				{
					buffer.writeByte(cmd);
				}
			}
			else
			{
				buffer.writeByte(0);
			}
			category++;
			if (_uiSettings.getCategories().containsKey(category))
			{
				final List<Integer> catElList2 = _uiSettings.getCategories().get(category);
				buffer.writeByte(catElList2.size());
				for (int cmd : catElList2)
				{
					buffer.writeByte(cmd);
				}
			}
			else
			{
				buffer.writeByte(0);
			}
			category++;
			if (_uiSettings.getKeys().containsKey(i))
			{
				final List<ActionKey> keyElList = _uiSettings.getKeys().get(i);
				buffer.writeInt(keyElList.size());
				for (ActionKey akey : keyElList)
				{
					buffer.writeInt(akey.getCommandId());
					buffer.writeInt(akey.getKeyId());
					buffer.writeInt(akey.getToogleKey1());
					buffer.writeInt(akey.getToogleKey2());
					buffer.writeInt(akey.getShowStatus());
				}
			}
			else
			{
				buffer.writeInt(0);
			}
		}
		buffer.writeInt(0x11);
		buffer.writeInt(16);
	}
}
