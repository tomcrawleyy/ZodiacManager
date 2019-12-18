package com.zodiacmc.ZodiacManager.Cuboids;

import java.util.HashMap;
import java.util.Map;

import com.zodiacmc.ZodiacManager.Users.User;

public class CuboidFactoryManager {
	
	private static CuboidFactoryManager instance;
	private Map<User, CuboidFactory> users = new HashMap<User, CuboidFactory>();
	
	private CuboidFactoryManager() {
		
	}
	
	public boolean isInSetupMode(User u) {
		return users.containsKey(u);
	}
	
	public CuboidFactory getUser(User u) {
		return users.get(u);
	}
	
	public void removeUser(User u) {
		users.remove(u);
	}
	
	public void addUser(User u, CuboidFactory factory) {
		if (users.containsKey(u))
			return;
		users.put(u,factory);
	}
	
	public static CuboidFactoryManager getInstance() {
		if (instance == null)
			instance = new CuboidFactoryManager();
		return instance;
	}

}
