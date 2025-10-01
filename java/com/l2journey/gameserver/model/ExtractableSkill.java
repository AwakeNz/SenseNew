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

import java.util.List;

/**
 * Extractable skill DTO.
 * @author Zoey76
 */
public class ExtractableSkill
{
	private final int _hash;
	private final List<ExtractableProductItem> _product;
	
	/**
	 * Instantiates a new extractable skill.
	 * @param hash the hash
	 * @param products the products
	 */
	public ExtractableSkill(int hash, List<ExtractableProductItem> products)
	{
		_hash = hash;
		_product = products;
	}
	
	/**
	 * Gets the skill hash.
	 * @return the skill hash
	 */
	public int getSkillHash()
	{
		return _hash;
	}
	
	/**
	 * Gets the product items.
	 * @return the product items
	 */
	public List<ExtractableProductItem> getProductItems()
	{
		return _product;
	}
}