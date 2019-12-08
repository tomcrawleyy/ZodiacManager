package com.zodiacmc.ZodiacManager.ChunkManager.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlock;
import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlockType;
import com.zodiacmc.ZodiacManager.ChunkManager.Configurations.WorldBlockConfig;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;

public class BlockPlace implements Listener {

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		for (WorldBlockType type : WorldBlockType.values()) {
			if (e.getBlock().getTypeId() != type.getID())
				continue;
			if (type.getData() != -1)
				if (e.getBlock().getData() != (byte) type.getData())
					continue;
			User u = UserManager.getInstance().getOnlineUser(e.getPlayer());
			WorldBlockConfig typeInstance = WorldBlockConfig.getInstance(type);
			int limit = typeInstance.getLimit(u.getRank());
			if (limit > -1) {
				if (u.getWorldBlocks(type).size() >= limit) {
					e.setCancelled(true);
					return;
				}
			}
			WorldBlock finalBlock = new WorldBlock(type, e.getBlock().getLocation(), u);
			u.getWorldBlocks(type).add(finalBlock);
			typeInstance.addInstance(finalBlock);
			break;
		}
	}

}
