package com.zodiacmc.ZodiacManager.Patches;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.zodiacmc.ZodiacManager.Utilities.StringUtil;

public class NetherRTP implements Listener {
	
	@EventHandler
	public void onRTP(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().equalsIgnoreCase("/rtp")) {
			if (!(e.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase("world"))){
				e.setCancelled(true);
				e.getPlayer().sendMessage(StringUtil.parseColours("&cError: You cannot use RTP outside of the overworld."));
			}
		}
	}

}
