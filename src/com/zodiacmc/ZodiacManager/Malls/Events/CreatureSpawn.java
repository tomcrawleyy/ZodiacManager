package com.zodiacmc.ZodiacManager.Malls.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;

public class CreatureSpawn implements Listener {

	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent e) {
		for (Mall mall : Mall.getMalls()) {
			if (mall.getCuboid().isInCuboid(e.getLocation())) {
				e.setCancelled(true);
			}
		}
	}

}
