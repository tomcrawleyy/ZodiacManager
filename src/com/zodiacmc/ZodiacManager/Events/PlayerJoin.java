package com.zodiacmc.ZodiacManager.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.zodiacmc.ZodiacManager.Listeners.IPlayerJoinListener;
import com.zodiacmc.ZodiacManager.Listeners.PlayerJoinListener;
import com.zodiacmc.ZodiacManager.Users.UserManager;

public class PlayerJoin implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		UserManager.getInstance().login(e.getPlayer());
		for (IPlayerJoinListener listener : PlayerJoinListener.getInstance().getListeners()) {
			listener.onJoin(e);
		}
	}
}
