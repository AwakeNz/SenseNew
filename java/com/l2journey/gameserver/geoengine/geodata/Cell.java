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
package com.l2journey.gameserver.geoengine.geodata;

/**
 * @author HorridoJoho
 */
public class Cell
{
	/** East NSWE flag */
	public static final byte NSWE_EAST = 1 << 0;
	/** West NSWE flag */
	public static final byte NSWE_WEST = 1 << 1;
	/** South NSWE flag */
	public static final byte NSWE_SOUTH = 1 << 2;
	/** North NSWE flag */
	public static final byte NSWE_NORTH = 1 << 3;
	
	/** North-East NSWE flags */
	public static final byte NSWE_NORTH_EAST = NSWE_NORTH | NSWE_EAST;
	/** North-West NSWE flags */
	public static final byte NSWE_NORTH_WEST = NSWE_NORTH | NSWE_WEST;
	/** South-East NSWE flags */
	public static final byte NSWE_SOUTH_EAST = NSWE_SOUTH | NSWE_EAST;
	/** South-West NSWE flags */
	public static final byte NSWE_SOUTH_WEST = NSWE_SOUTH | NSWE_WEST;
	
	/** All directions NSWE flags */
	public static final byte NSWE_ALL = NSWE_EAST | NSWE_WEST | NSWE_SOUTH | NSWE_NORTH;
	
	private Cell()
	{
	}
}
