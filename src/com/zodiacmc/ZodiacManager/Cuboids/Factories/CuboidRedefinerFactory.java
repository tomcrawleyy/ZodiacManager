package com.zodiacmc.ZodiacManager.Cuboids.Factories;

import org.bukkit.Location;

import com.zodiacmc.ZodiacManager.Cuboids.Cuboid;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidContainer;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidFactory;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidType;
import com.zodiacmc.ZodiacManager.Users.User;

public class CuboidRedefinerFactory extends CuboidFactory {

	private CuboidContainer container;
	private boolean ignoreY;
	
	public CuboidRedefinerFactory(User u, CuboidContainer container, boolean ignoreY) {
		super(u, CuboidType.REDEFINE);
		this.container = container;
		this.ignoreY = ignoreY;
	}
	
	public CuboidContainer getContainer() {
		return container;
	}
	
	@Override
	public Cuboid setLocation2(Location loc) {
		return new Cuboid(this.getLocation1(), loc, this.ignoreY, CuboidType.REDEFINE);
	}

}
