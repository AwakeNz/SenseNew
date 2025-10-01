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
package com.l2journey.commons.network.internal;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A thread factory used for creating threads for MMO server tasks.<br>
 * This factory assigns custom names and priorities to the threads it creates, aiding in identification and management.
 * @author JoeAlisson
 */
public class MMOThreadFactory implements ThreadFactory
{
	private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
	
	private final AtomicInteger _threadNumber = new AtomicInteger(1);
	private final String _namePrefix;
	private final int _priority;
	
	public MMOThreadFactory(String name, int priority)
	{
		_namePrefix = name + "-MMO-pool-" + POOL_NUMBER.getAndIncrement() + "-thread-";
		_priority = priority;
	}
	
	@Override
	public Thread newThread(Runnable r)
	{
		final Thread thread = new Thread(null, r, _namePrefix + _threadNumber.getAndIncrement(), 0);
		thread.setPriority(_priority);
		thread.setDaemon(false);
		return thread;
	}
}
