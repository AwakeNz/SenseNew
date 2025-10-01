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
public interface IBlock
{
	int TYPE_FLAT = 0;
	int TYPE_COMPLEX = 1;
	int TYPE_MULTILAYER = 2;
	
	/** Cells in a block on the x axis */
	int BLOCK_CELLS_X = 8;
	/** Cells in a block on the y axis */
	int BLOCK_CELLS_Y = 8;
	/** Cells in a block */
	int BLOCK_CELLS = BLOCK_CELLS_X * BLOCK_CELLS_Y;
	
	boolean checkNearestNswe(int geoX, int geoY, int worldZ, int nswe);
	
	void setNearestNswe(int geoX, int geoY, int worldZ, byte nswe);
	
	void unsetNearestNswe(int geoX, int geoY, int worldZ, byte nswe);
	
	short getNearestNswe(int geoX, int geoY, int worldZ);
	
	int getNearestZ(int geoX, int geoY, int worldZ);
	
	int getNextLowerZ(int geoX, int geoY, int worldZ);
	
	int getNextHigherZ(int geoX, int geoY, int worldZ);
}
