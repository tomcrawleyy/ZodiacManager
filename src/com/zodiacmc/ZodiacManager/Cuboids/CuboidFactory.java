package com.zodiacmc.ZodiacManager.Cuboids;

import org.bukkit.Location;
import org.bukkit.event.Listener;

import com.zodiacmc.ZodiacManager.Users.User;

public abstract class CuboidFactory implements Listener {
	
	private Location loc1;
	private User user;
	private CuboidType type;
	
	protected CuboidFactory(User user, CuboidType type) {
		this.user = user;
		this.type = type;
	}
	
	public CuboidType getType() {
		return type;
	}
	
	public Location getLocation1() {
		return loc1;
	}
	
	public void setLocation1(Location loc) {
		this.loc1 = loc;
		user.sendMessage("&aLocation 1 has been set!");
	}
	
	public Cuboid setLocation2(Location loc) {
		return new Cuboid(loc1, loc, type.shouldIgnoreY(), type);
	}

}
