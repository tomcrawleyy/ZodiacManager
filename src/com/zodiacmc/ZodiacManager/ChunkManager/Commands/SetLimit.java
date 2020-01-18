package com.zodiacmc.ZodiacManager.ChunkManager.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.zodiacmc.ZodiacManager.AutoRank.Ranking.Rank;
import com.zodiacmc.ZodiacManager.AutoRank.Ranking.RankManager;
import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlockType;
import com.zodiacmc.ZodiacManager.ChunkManager.Configurations.WorldBlockConfig;
import com.zodiacmc.ZodiacManager.Commands.SubCommand;

public class SetLimit extends SubCommand {

	public SetLimit() {
		super("SetLimit", false);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		if (((!(sender instanceof Player)) && (args.length != 3)) | (args.length < 2))
			return this.usage("ChunkManager SetLimit <BlockType> <Rank> <NewLimit>");
		WorldBlockType blockType = null;
		String rankString = "";
		String newLimitString = "";
		if (args.length == 2) {
			Player player = (Player)sender;
			ItemStack item = player.getItemInHand();
			for (WorldBlockType type : WorldBlockType.values()) {
				if (item.getTypeId() == type.getID() && item.getData().getData() == (byte)type.getData()) {
					blockType = type;
					break;
				}
			}
			if (blockType == null)
				return this.error("BlockType: " + item.getTypeId() + ":" + item.getData().getData() + " has not been configured in this plugin.");
			rankString = args[0];
			newLimitString = args[1];
		} else {
			for (WorldBlockType type : WorldBlockType.values()) {
				if (type.name().equalsIgnoreCase(args[0]) || type.getCapitalization().equalsIgnoreCase(args[0])) {
					blockType = type;
					break;
				}
			}
			if (blockType == null)
				return this.error("The block " + args[0] + " has not been configured in this plugin.");
			rankString = args[1];
			newLimitString = args[2];
		}
		Rank r = RankManager.getInstance().getRank(rankString);
		if (r == null)
			return this.error("Rank: " + rankString + " does not exist!");
		Integer limit;
		try {
			limit = Integer.parseInt(newLimitString);
		} catch (NumberFormatException e) {
			return this.usage("ChunkManager SetLimit <BlockType> <Rank> <NewLimit>");
		}
		WorldBlockConfig config = WorldBlockConfig.getInstance(blockType);
		config.setLimit(r, limit);
		return this.success("Limit for " + blockType.getReadable() + " has been updated for " + r.getName() + " to " + limit + ".");
	}

}
