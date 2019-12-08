package com.zodiacmc.ZodiacManager.Malls.Cuboids;

import com.zodiacmc.ZodiacManager.Cuboids.CuboidFactory;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidType;
import com.zodiacmc.ZodiacManager.Users.User;

public class MallFactory extends CuboidFactory {
	
	private String name;
	public MallFactory(String name, User u) {
		super(u, CuboidType.MALL);
	}
	
	public String getName() {
		return name;
	}

}
