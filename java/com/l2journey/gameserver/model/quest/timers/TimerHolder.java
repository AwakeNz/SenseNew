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
package com.l2journey.gameserver.model.quest.timers;

import java.util.Objects;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.l2journey.commons.threads.ThreadPool;
import com.l2journey.gameserver.model.StatSet;
import com.l2journey.gameserver.model.actor.Npc;
import com.l2journey.gameserver.model.actor.Player;

/**
 * @author UnAfraid
 * @param <T>
 */
public class TimerHolder<T> implements Runnable
{
	private final T _event;
	private final StatSet _params;
	private final long _time;
	private final Npc _npc;
	private final Player _player;
	private final boolean _isRepeating;
	private final IEventTimerEvent<T> _eventScript;
	private final IEventTimerCancel<T> _cancelScript;
	private final TimerExecutor<T> _postExecutor;
	private final ScheduledFuture<?> _task;
	
	public TimerHolder(T event, StatSet params, long time, Npc npc, Player player, boolean isRepeating, IEventTimerEvent<T> eventScript, IEventTimerCancel<T> cancelScript, TimerExecutor<T> postExecutor)
	{
		Objects.requireNonNull(event, getClass().getSimpleName() + ": \"event\" cannot be null!");
		Objects.requireNonNull(eventScript, getClass().getSimpleName() + ": \"script\" cannot be null!");
		Objects.requireNonNull(postExecutor, getClass().getSimpleName() + ": \"postExecutor\" cannot be null!");
		_event = event;
		_params = params;
		_time = time;
		_npc = npc;
		_player = player;
		_isRepeating = isRepeating;
		_eventScript = eventScript;
		_cancelScript = cancelScript;
		_postExecutor = postExecutor;
		_task = isRepeating ? ThreadPool.scheduleAtFixedRate(this, _time, _time) : ThreadPool.schedule(this, _time);
		
		if (npc != null)
		{
			npc.addTimerHolder(this);
		}
		
		if (player != null)
		{
			player.addTimerHolder(this);
		}
	}
	
	/**
	 * @return the event/key of this timer
	 */
	public T getEvent()
	{
		return _event;
	}
	
	/**
	 * @return the parameters of this timer
	 */
	public StatSet getParams()
	{
		return _params;
	}
	
	/**
	 * @return the npc of this timer
	 */
	public Npc getNpc()
	{
		return _npc;
	}
	
	/**
	 * @return the player of this timer
	 */
	public Player getPlayer()
	{
		return _player;
	}
	
	/**
	 * @return {@code true} if the timer will repeat itself, {@code false} otherwise
	 */
	public boolean isRepeating()
	{
		return _isRepeating;
	}
	
	/**
	 * Cancels this timer.
	 */
	public void cancelTimer()
	{
		if (_npc != null)
		{
			_npc.removeTimerHolder(this);
		}
		
		if (_player != null)
		{
			_player.removeTimerHolder(this);
		}
		
		if ((_task == null) || _task.isCancelled() || _task.isDone())
		{
			return;
		}
		
		_task.cancel(false);
		_cancelScript.onTimerCancel(this);
	}
	
	/**
	 * Cancels task related to this quest timer.
	 */
	public void cancelTask()
	{
		if ((_task != null) && !_task.isDone() && !_task.isCancelled())
		{
			_task.cancel(false);
		}
	}
	
	/**
	 * @return the remaining time of the timer, or -1 in case it doesn't exist.
	 */
	public long getRemainingTime()
	{
		if ((_task == null) || _task.isCancelled() || _task.isDone())
		{
			return -1;
		}
		return _task.getDelay(TimeUnit.MILLISECONDS);
	}
	
	/**
	 * @param event
	 * @param npc
	 * @param player
	 * @return {@code true} if event, npc, player are equals to the ones stored in this TimerHolder, {@code false} otherwise
	 */
	public boolean isEqual(T event, Npc npc, Player player)
	{
		return _event.equals(event) && (_npc == npc) && (_player == player);
	}
	
	/**
	 * @param timer the other timer to be compared with.
	 * @return {@code true} of both of timers' npc, event and player match, {@code false} otherwise.
	 */
	public boolean isEqual(TimerHolder<T> timer)
	{
		return _event.equals(timer._event) && (_npc == timer._npc) && (_player == timer._player);
	}
	
	@Override
	public void run()
	{
		// Notify the post executor to remove this timer from the map
		_postExecutor.onTimerPostExecute(this);
		
		// Notify the script that the event has been fired.
		_eventScript.onTimerEvent(this);
	}
	
	@Override
	public String toString()
	{
		return "event: " + _event + " params: " + _params + " time: " + _time + " npc: " + _npc + " player: " + _player + " repeating: " + _isRepeating + " script: " + _eventScript.getClass().getSimpleName() + " postExecutor: " + _postExecutor.getClass().getSimpleName();
	}
}