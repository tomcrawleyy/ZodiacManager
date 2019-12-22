package com.zodiacmc.ZodiacManager.Listeners;

import java.util.ArrayList;
import java.util.List;

public class PlayerJoinListener {
	
	private static PlayerJoinListener instance;
	
	private List<IPlayerJoinListener> listeners;
	
	private PlayerJoinListener() {
		listeners = new ArrayList<IPlayerJoinListener>();
	}
	
	public static PlayerJoinListener getInstance() {
		if (instance == null)
			instance = new PlayerJoinListener();
		return instance;
	}
	
	public void addListener(IPlayerJoinListener listener) {
		this.listeners.add(listener);
	}
	
	public List<IPlayerJoinListener> getListeners(){
		return this.listeners;
	}
	
	

}
