package com.zodiacmc.ZodiacManager.MinimumPrices.Models;

import com.zodiacmc.ZodiacManager.Models.WorldItem;

public class ShopSign {
	
	private int amount;
	private int price;
	private WorldItem item;
	
	public ShopSign(int amount, WorldItem item, int price) {
		this.amount = amount;
		this.price = price;
		this.item = item;
	}
	
	public int getPrice() {
		return price;
	}
	
	public WorldItem getItem() {
		return item;
	}
	
	public int getAmount() {
		return amount;
	}
	
	

}
