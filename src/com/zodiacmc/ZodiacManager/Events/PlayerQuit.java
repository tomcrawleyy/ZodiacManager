package com.zodiacmc.ZodiacManager.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.zodiacmc.ZodiacManager.Users.UserManager;

public class PlayerQuit implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		UserManager.getInstance().logout(e.getPlayer());
	}

}
