package com.zodiacmc.ZodiacManager.ChunkManager.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.zodiacmc.ZodiacManager.AutoRank.Ranking.Rank;
import com.zodiacmc.ZodiacManager.AutoRank.Ranking.RankManager;
import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlockType;
import com.zodiacmc.ZodiacManager.ChunkManager.Configurations.WorldBlockConfig;
import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.StringUtil;

public class CheckLimit extends SubCommand {

	public CheckLimit() {
		super("CheckLimit", false);
	}

	@Override
	public boolean processCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player) && args.length != 2)
			return this.usage("ChunkManager CheckLimit <Block> <Rank>");

		if (args.length == 1)
			return this.usage("ChunkManager CheckLimit or /ChunkManager CheckLimit <Block> <Rank>");

		WorldBlockType block = null;
		for (WorldBlockType type : WorldBlockType.values()) {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase(type.name()) || args[0].equalsIgnoreCase(type.getCapitalization())) {
					block = type;
					break;
				}
			} else {
				Player player = (Player) sender;
				if (player.getItemInHand().getTypeId() == type.getID()) {
					if (type.getData() != -1) {
						if (player.getItemInHand().getData().getData() == (byte) type.getData()) {
							block = type;
							break;
						}
					} else {
						block = type;
						break;
					}
				}
			}
		}
		if (block == null) {
			if (args.length == 2)
				return this.error("BlockType: " + args[0] + " is not configured in this plugin.");
			Player player = (Player) sender;
			ItemStack item = player.getItemInHand();
			return this.error("BlockType: " + item.getTypeId() + ":"
					+ item.getData().getData() + " is not configured in this plugin.");
		}

		Rank r = args.length == 0 ? UserManager.getInstance().getOnlineUser((Player) sender).getRank()
				: RankManager.getInstance().getRank(args[1]);
		if (r == null)
			return this.error("Rank " + args[1] + " does not exist!");

		WorldBlockConfig config = WorldBlockConfig.getInstance(block);
		int limit = config.getLimit(r);
		if (limit == -1)
			return this.resolve("There is no limit for this item!");
		if (limit == 0)
			return this.resolve("This item is restricted for your rank!");

		sender.sendMessage(StringUtil.parseColours(command.getPrefix() + " &aThe limit for "
				+ block.getReadable() + "'s at rank " + r.getName() + " is " + limit + "."));
		if (sender instanceof Player && limit > 0) {
			Player player = (Player) sender;
			int amountLeft = limit - UserManager.getInstance().getOnlineUser(player).getWorldBlocks(block).size();
			if (amountLeft > 0) {
				return this.resolve("You may place another " + amountLeft + " of this block type.");
			} else if (amountLeft == 0) {
				return this.resolve("You have reached the limit for this rank.");
			} else {
				return this.error("User " + player.getName()
						+ " has exceeded the placement limit for blocktype " + block.name().toLowerCase() + " by "
						+ Math.abs(amountLeft));
			}
		}
		return true;
	}

}
