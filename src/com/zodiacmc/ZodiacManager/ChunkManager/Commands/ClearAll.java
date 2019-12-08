package com.zodiacmc.ZodiacManager.ChunkManager.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlock;
import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlockType;
import com.zodiacmc.ZodiacManager.ChunkManager.Configurations.WorldBlockConfig;
import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Utilities.CommandUtil;

public class ClearAll extends SubCommand {
	
	public ClearAll() {
		super("ClearAll", false, true);
	}
	
	public boolean processCommand(CommandSender sender, String[] args) {
		if (args.length != 1)
			return CommandUtil.success(sender, command.getPrefix() + " &cUsage: /ChunkManager ClearAll <BlockType>");
		WorldBlockType block = null;
		for (WorldBlockType type : WorldBlockType.values()) {
			if (type.name().equalsIgnoreCase(args[0])) {
				block = type;
				continue;
			}
		}
		if (block == null)
			return CommandUtil.success(sender, command.getPrefix() + " &cError: BlockType: " + args[0] + " is not configured in this plugin.");
		if (sender instanceof Player) {
			Player player = (Player)sender;
			if (!player.hasPermission("ChunkManager.ClearAll." + block.getCapitalization()))
				return CommandUtil.success(sender,  command.getPrefix() + " &cError: You do not have the permission ChunkManager.ClearAll." + block.getCapitalization());
		}
		WorldBlockConfig config = WorldBlockConfig.getInstance(block);
		int blocksDestroyed = 0;
		for (WorldBlock localBlock : config.getInstances()) {
			localBlock.destroy();
			blocksDestroyed++;
			config.removeInstance(localBlock);
		}
		return CommandUtil.success(sender, command.getPrefix() + " &aA total of " + blocksDestroyed + " have been removed from the world!");
	}
	
	public String permissionRequired() {
		return "ChunkManager.ClearAll";
	}

}
