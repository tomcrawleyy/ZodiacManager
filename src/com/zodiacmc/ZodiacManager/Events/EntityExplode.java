package com.zodiacmc.ZodiacManager.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplode implements Listener {
	
	@EventHandler
	public void onExplode(EntityExplodeEvent e) {
		e.setCancelled(true);
	}

}
