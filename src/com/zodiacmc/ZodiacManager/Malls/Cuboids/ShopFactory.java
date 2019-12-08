package com.zodiacmc.ZodiacManager.Malls.Cuboids;

import com.zodiacmc.ZodiacManager.Cuboids.CuboidFactory;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidType;
import com.zodiacmc.ZodiacManager.Malls.Models.Mall;
import com.zodiacmc.ZodiacManager.Users.User;

public class ShopFactory extends CuboidFactory {
	
	private Mall mall;
	
	public ShopFactory(User u, Mall mall) {
		super(u, CuboidType.SHOP);
		this.mall = mall;
	}
	
	public Mall getMall() {
		return mall;
	}

}
