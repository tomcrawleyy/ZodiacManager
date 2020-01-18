package com.zodiacmc.ZodiacManager.ChunkManager.Commands;

import java.util.concurrent.TimeUnit;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.zodiacmc.ZodiacManager.AutoRank.Ranking.Rank;
import com.zodiacmc.ZodiacManager.AutoRank.Ranking.RankManager;
import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlockType;
import com.zodiacmc.ZodiacManager.ChunkManager.Configurations.WorldBlockConfig;
import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Utilities.StringUtil;

public class SetRemovalDelay extends SubCommand {
	
	public SetRemovalDelay() {
		super("SetRemovalDelay", false);
	}
	
	public boolean processCommand(CommandSender sender, String[] args) {
		if (((!(sender instanceof Player)) && (args.length != 4)) | (args.length < 3))
			return this.usage("ChunkManager SetRemovalDelay <BlockType> <Rank> <Delay> <TimeUnit>");
		WorldBlockType blockType = null;
		String rankString = "";
		String delayString = "";
		String timeUnitString = "";
		if (args.length == 3) {
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
			delayString = args[1];
			timeUnitString = args[2];
		} else {
			for (WorldBlockType type : WorldBlockType.values()) {
				if (type.name().equalsIgnoreCase(args[0]) || type.getCapitalization().equalsIgnoreCase(args[0])) {
					blockType = type;
					break;
				}
			}
			if (blockType == null)
				return this.error("BlockType: " + args[0] + " has not been configured in this plugin.");
			rankString = args[1];
			delayString = args[2];
			timeUnitString = args[3];
		}
		Rank r = RankManager.getInstance().getRank(rankString);
		if (r == null)
			return this.error("Rank: " + rankString + " does not exist!");
		Integer delay;
		try {
			delay = Integer.parseInt(delayString);
		} catch (NumberFormatException e) {
			return this.usage("ChunkManager SetRemovalDelay <Rank> <Delay> <TimeUnit>");
		}
		TimeUnit unit = null;
		for (TimeUnit localUnit : TimeUnit.values()) {
			if (localUnit.name().equalsIgnoreCase(timeUnitString)) {
				unit = localUnit;
				break;
			}
		}
		if (unit == null)
			return this.error("TimeUnits Available: Milliseconds, Seconds, Minutes, Hours");
		WorldBlockConfig config = WorldBlockConfig.getInstance(blockType);
		if (!config.destroyOnLogout()) {
			config.setDestroyOnLogout(true);
			sender.sendMessage(StringUtil.parseColours(command.getPrefix()));
		}
		config.setRemovalDelay(r, (long)delay, unit);
		return this.success("Removal delay for " + blockType.getReadable() + " has been updated for " + r.getName() + " to " + delay + " " + unit.name().toLowerCase() + "'s.");
	}

}
