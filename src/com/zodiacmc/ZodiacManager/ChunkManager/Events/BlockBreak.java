package com.zodiacmc.ZodiacManager.ChunkManager.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlock;
import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlockType;
import com.zodiacmc.ZodiacManager.ChunkManager.Configurations.WorldBlockConfig;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;
import com.zodiacmc.ZodiacManager.Utilities.LocationUtil;

public class BlockBreak implements Listener {
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		for (WorldBlockType type : WorldBlockType.values()) {
			if (e.getBlock().getTypeId() != type.getID())
				continue;
			if (type.getData() != -1)
				if (e.getBlock().getData() != (byte)type.getData())
					continue;
			WorldBlockConfig config = WorldBlockConfig.getInstance(type);
			WorldBlock block = config.getPlacedAt(e.getBlock().getLocation());
			if (block == null) {
				ConsoleUtil.sendMessage("&cError: BlockBreak 29 at " + LocationUtil.toString(e.getBlock().getLocation()));
			}
			block.destroy();
			block.getPlacedBy().getWorldBlocks(type).remove(block);
			config.removeInstance(block);
		}
	}

}
