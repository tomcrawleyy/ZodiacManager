package com.zodiacmc.ZodiacManager.Cuboids;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;

import com.zodiacmc.ZodiacManager.Users.User;

public abstract class CuboidFactory {
	
	private Location loc1;
	private User user;
	private CuboidType type;
	
	
	private static Map<User, CuboidFactory> users = new HashMap<User, CuboidFactory>();
	
	public static boolean isInSetupMode(User u) {
		return users.containsKey(u);
	}
	public static CuboidFactory getUser(User u) {
		return users.get(u);
	}
	
	public static void removeUser(User u) {
		users.remove(u);
	}
	
	public static void addUser(User u, CuboidFactory factory) {
		users.put(u, factory);
	}
	
	protected CuboidFactory(User user, CuboidType type) {
		this.user = user;
		this.type = type;
	}
	
	public CuboidType getType() {
		return type;
	}
	
	public void setLocation1(Location loc) {
		this.loc1 = loc;
		user.sendMessage("&aLocation 1 has been set!");
	}
	
	public Cuboid setLocation2(Location loc) {
		return new Cuboid(loc1, loc, type.shouldIgnoreY());
	}

}
