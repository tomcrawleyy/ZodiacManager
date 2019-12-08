package com.zodiacmc.ZodiacManager.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.zodiacmc.ZodiacManager.Users.UserManager;

public class PlayerJoin implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		UserManager.getInstance().login(e.getPlayer());
	}
}
