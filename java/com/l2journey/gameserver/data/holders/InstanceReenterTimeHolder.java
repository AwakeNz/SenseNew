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
package com.l2journey.gameserver.data.holders;

import java.time.DayOfWeek;

/**
 * Simple class for storing Reenter Data for Instances.
 * @author FallenAngel
 */
public class InstanceReenterTimeHolder
{
	private final DayOfWeek _day;
	private final int _hour;
	private final int _minute;
	private final long _time;
	
	public InstanceReenterTimeHolder(long time)
	{
		_time = time;
		_day = null;
		_hour = -1;
		_minute = -1;
	}
	
	public InstanceReenterTimeHolder(DayOfWeek day, int hour, int minute)
	{
		_time = -1;
		_day = day;
		_hour = hour;
		_minute = minute;
	}
	
	public long getTime()
	{
		return _time;
	}
	
	public DayOfWeek getDay()
	{
		return _day;
	}
	
	public int getHour()
	{
		return _hour;
	}
	
	public int getMinute()
	{
		return _minute;
	}
}