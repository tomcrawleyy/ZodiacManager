package com.zodiacmc.ZodiacManager.ChunkManager.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlock;
import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlockType;
import com.zodiacmc.ZodiacManager.ChunkManager.Configurations.WorldBlockConfig;
import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;

public class ClearAll extends SubCommand {
	
	public ClearAll() {
		super("ClearAll", false);
	}
	
	public boolean processCommand(CommandSender sender, String[] args) {
		if (args.length != 1)
			return this.usage("ChunkManager ClearAll <BlockType>");
		WorldBlockType block = null;
		for (WorldBlockType type : WorldBlockType.values()) {
			if (type.name().equalsIgnoreCase(args[0]) || type.getCapitalization().equalsIgnoreCase(args[0])) {
				block = type;
				break;
			}
		}
		if (block == null)
			return this.error("BlockType: " + args[0] + " is not configured in this plugin.");
		if (sender instanceof Player) {
			Player player = (Player)sender;
			if (!player.hasPermission("ChunkManager.ClearAll." + block.getCapitalization()))
				return this.error("You do not have the permission ChunkManager.ClearAll." + block.getCapitalization());
		}
		WorldBlockConfig config = WorldBlockConfig.getInstance(block);
		int blocksDestroyed = 0;
		ConsoleUtil.sendMessage("instanceSize: " + config.getInstances().size());
		for (WorldBlock localBlock : config.getInstances()) {
			localBlock.destroy();
			blocksDestroyed++;
			localBlock.getPlacedBy().getWorldBlocks(localBlock.getType()).remove(localBlock);
		}
		config.clearInstances();
		return this.success("A total of " + blocksDestroyed + " have been removed from the world!");
	}
}
