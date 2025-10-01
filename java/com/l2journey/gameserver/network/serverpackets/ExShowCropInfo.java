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
import com.l2journey.gameserver.managers.CastleManorManager;
import com.l2journey.gameserver.model.CropProcure;
import com.l2journey.gameserver.model.Seed;
import com.l2journey.gameserver.network.GameClient;
import com.l2journey.gameserver.network.ServerPackets;

/**
 * @author l3x
 */
public class ExShowCropInfo extends ServerPacket
{
	private final List<CropProcure> _crops;
	private final int _manorId;
	private final boolean _hideButtons;
	
	public ExShowCropInfo(int manorId, boolean nextPeriod, boolean hideButtons)
	{
		_manorId = manorId;
		_hideButtons = hideButtons;
		final CastleManorManager manor = CastleManorManager.getInstance();
		_crops = (nextPeriod && !manor.isManorApproved()) ? null : manor.getCropProcure(manorId, nextPeriod);
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		ServerPackets.EX_SHOW_CROP_INFO.writeId(this, buffer);
		buffer.writeByte(_hideButtons); // Hide "Crop Sales" button
		buffer.writeInt(_manorId); // Manor ID
		buffer.writeInt(0);
		if (_crops == null)
		{
			buffer.writeInt(0);
			return;
		}
		
		buffer.writeInt(_crops.size());
		for (CropProcure crop : _crops)
		{
			buffer.writeInt(crop.getId()); // Crop id
			buffer.writeLong(crop.getAmount()); // Buy residual
			buffer.writeLong(crop.getStartAmount()); // Buy
			buffer.writeLong(crop.getPrice()); // Buy price
			buffer.writeByte(crop.getReward()); // Reward
			final Seed seed = CastleManorManager.getInstance().getSeedByCrop(crop.getId());
			if (seed == null)
			{
				buffer.writeInt(0); // Seed level
				buffer.writeByte(1); // Reward 1
				buffer.writeInt(0); // Reward 1 - item id
				buffer.writeByte(1); // Reward 2
				buffer.writeInt(0); // Reward 2 - item id
			}
			else
			{
				buffer.writeInt(seed.getLevel()); // Seed level
				buffer.writeByte(1); // Reward 1
				buffer.writeInt(seed.getReward(1)); // Reward 1 - item id
				buffer.writeByte(1); // Reward 2
				buffer.writeInt(seed.getReward(2)); // Reward 2 - item id
			}
		}
	}
}