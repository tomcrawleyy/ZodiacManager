package com.zodiacmc.ZodiacManager.ChunkManager.Commands;

import org.bukkit.command.CommandSender;

import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlockType;
import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Utilities.StringUtil;

public class ListConfiguredBlocks extends SubCommand {
	
	public ListConfiguredBlocks() {
		super("ListConfiguredBlocks", false);
	}
	
	public boolean processCommand(CommandSender sender, String[] args) {
		if (args.length != 0)
			return this.usage("ChunkManager ListConfiguredBlocks");
		sender.sendMessage(StringUtil.parseColours("&d---------&f(&dChunk&fManager)&d---------"));
		sender.sendMessage(StringUtil.parseColours("&fConfigured Blocks:"));
		int i = 0;
		for (WorldBlockType type : WorldBlockType.values()) {
			sender.sendMessage(StringUtil.parseColours("&a" + ++i +"&f) &d" + type.getCapitalization()));
		}
		return true;
	}

}
