package com.zodiacmc.ZodiacManager.ChunkManager.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlock;
import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlockType;
import com.zodiacmc.ZodiacManager.ChunkManager.Configurations.WorldBlockConfig;
import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Scheduling.ScheduledTask;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Utilities.CommandUtil;

public class UnloadBlocks extends SubCommand {

	public UnloadBlocks() {
		super("UnloadBlocks", false, true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		if (args.length != 1)
			return CommandUtil.success(sender,
					command.getPrefix() + " &cUsage: /ChunkManager UnloadBlocks (<BlockType>|'All')");
		int count = 0;
		if (args[0].equalsIgnoreCase("all")) {
			for (WorldBlock block : WorldBlock.getScheduledBlocks()) {
				block.destroy();
				count++;
			}
			for (User user : WorldBlock.getScheduledRemovals().keySet()) {
				for (ScheduledTask task : WorldBlock.getScheduledRemovals().get(user)) {
					task.cancel();
				}
			}
			return CommandUtil.success(sender, command.getPrefix() + " &a" + count + " blocks successfully unloaded!");
		}
		WorldBlockType block = null;
		for (WorldBlockType type : WorldBlockType.values()) {
			if (type.name().equalsIgnoreCase(args[0])) {
				block = type;
				continue;
			}
		}
		if (block == null)
			return CommandUtil.success(sender,
					command.getPrefix() + " &cError: BlockType: " + args[0] + " is not configured in this plugin.");
		WorldBlockConfig config = WorldBlockConfig.getInstance(block);
		if (!config.destroyOnLogout())
			return CommandUtil.success(sender, command.getPrefix() + " &cError: BlockType: " + args[0]
					+ " has been configured to disallow unloading.");
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (!player.hasPermission("ChunkManager.ClearAll." + block.getCapitalization()))
				return CommandUtil.success(sender,  command.getPrefix() + " &cError: You do not have the permission ChunkManager.ClearAll." + block.getCapitalization());
		}
		for (WorldBlock localBlock : WorldBlock.getScheduledBlocks()) {
			if (localBlock.getType() == block) {
				localBlock.destroy();
				count++;
			}
		}
		return CommandUtil.success(sender,
				command.getPrefix() + " &a" + count + " " + block.name() + " blocks successfully unloaded!");
	}

	public String permissionRequired() {
		return "ChunkManager.UnloadBlocks";
	}

}
