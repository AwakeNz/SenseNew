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
package com.l2journey.gameserver.model;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author xban1x
 */
public class SeedProduction
{
	private final int _seedId;
	private final long _price;
	private final long _startAmount;
	private final AtomicLong _amount;
	
	public SeedProduction(int id, long amount, long price, long startAmount)
	{
		_seedId = id;
		_amount = new AtomicLong(amount);
		_price = price;
		_startAmount = startAmount;
	}
	
	public int getId()
	{
		return _seedId;
	}
	
	public long getAmount()
	{
		return _amount.get();
	}
	
	public long getPrice()
	{
		return _price;
	}
	
	public long getStartAmount()
	{
		return _startAmount;
	}
	
	public void setAmount(long amount)
	{
		_amount.set(amount);
	}
	
	public boolean decreaseAmount(long value)
	{
		long current;
		long next;
		do
		{
			current = _amount.get();
			next = current - value;
			if (next < 0)
			{
				return false;
			}
		}
		while (!_amount.compareAndSet(current, next));
		return true;
	}
}