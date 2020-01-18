package com.zodiacmc.ZodiacManager.ChunkManager.Events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlockType;
import com.zodiacmc.ZodiacManager.Utilities.StringUtil;

public class FillerPatch implements Listener {
	
	@EventHandler
	public void onFiller(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player))
			return;
		Player player = (Player)e.getWhoClicked();
		if (ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Filler")) {
			for (WorldBlockType type : WorldBlockType.values()) {
				if (e.getCurrentItem().getTypeId() == type.getID() && e.getCurrentItem().getData().getData() == (byte)type.getData()) {
					e.setCancelled(true);
					player.sendMessage(StringUtil.parseColours("&cError: You cannot place this item into a filler!"));
					return;
				}
			}
		}
	}

}
