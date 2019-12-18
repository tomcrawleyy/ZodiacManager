package com.zodiacmc.ZodiacManager.Malls.Cuboids.Factories;

import com.zodiacmc.ZodiacManager.Cuboids.CuboidFactory;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidType;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Users.User;

public class ShopFactory extends CuboidFactory {
	
	private Mall mall;
	private int price;
	
	public ShopFactory(User u, Mall mall, int price) {
		super(u, CuboidType.SHOP);
		this.mall = mall;
		this.price = price;
	}
	
	public Mall getMall() {
		return mall;
	}
	
	public int getPrice() {
		return price;
	}

}
