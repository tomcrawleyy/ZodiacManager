package com.zodiacmc.ZodiacManager.ChunkManager.Commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlock;
import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlockType;
import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Utilities.LocationUtil;
import com.zodiacmc.ZodiacManager.Utilities.StringUtil;

public class CheckOnline extends SubCommand {

	public CheckOnline() {
		super("CheckOnline", false, true);
	}

	@Override
	public boolean processCommand(CommandSender sender, String[] args) {
		if (args.length != 1)
			return this.usage("ChunkManager CheckOnline <Block> <Optional<PageNumber>>");
		WorldBlockType type = null;
		for (WorldBlockType localType : WorldBlockType.values()) {
			if (args[0].equalsIgnoreCase(localType.name())) {
				type = localType;
			}
		}
		if (type == null)
			return this.error("ChunkManager does not monitor a block named " + args[0]);
		List<WorldBlock> blocks = WorldBlock.getLoadedInstances(type);
		if (blocks.size() == 0)
			return this.resolve("There are currently no instances of this block loaded!");
		if (args.length == 1) {
			int count = 10;
			if (blocks.size() < 10)
				count = blocks.size();
			for (int i = 0; i < count; i++) {
				WorldBlock block = blocks.get(i);
				sender.sendMessage(StringUtil.parseColours("&2User: &a" + block.getPlacedBy().getName() + ", &cLocation: &4[&c" + LocationUtil.toString(block.getLocation()) + "&4]"));
			}
		} else {
			int page;
			int maxPages = (int)Math.ceil(blocks.size()/(double)10);
			try {
				page = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				return this.usage("ChunkManager CheckOnline <Block> <Optional<PageNumber>>");
			}
			if (page > maxPages)
				return this.error("Page does not exist! Last page number: " + maxPages);
			int amount = page*10 + 10;
			if (blocks.size() < amount)
				amount = blocks.size();
			for (int i = page*10; i < amount; i++) {
				WorldBlock block = blocks.get(i);
				sender.sendMessage(StringUtil.parseColours("&2User: &a" + block.getPlacedBy().getName() + ", &cLocation: &4[&c" + LocationUtil.toString(block.getLocation()) + "&4]"));
			}
		}
		return true;
	}

	@Override
	public String permissionRequired() {
		return "ChunkManager.CheckOnline";
	}

}
