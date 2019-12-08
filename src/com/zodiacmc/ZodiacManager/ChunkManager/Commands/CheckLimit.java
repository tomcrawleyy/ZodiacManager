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
import com.zodiacmc.ZodiacManager.Utilities.CommandUtil;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;
import com.zodiacmc.ZodiacManager.Utilities.StringUtil;

public class CheckLimit extends SubCommand {

	public CheckLimit() {
		super("CheckLimit", false, false);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean processCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player) && args.length != 2)
			return CommandUtil.success(sender,
					command.getPrefix() + " &cUsage: /ChunkManager CheckLimit <Block> <Rank>");

		if (args.length == 1)
			return CommandUtil.success(sender, command.getPrefix()
					+ " &cUsage: /ChunkManager CheckLimit or /ChunkManager CheckLimit <Block> <Rank>");

		WorldBlockType block = null;
		for (WorldBlockType type : WorldBlockType.values()) {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase(type.name())) {
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
				return CommandUtil.success(sender,
						command.getPrefix() + " &cError: BlockType " + args[0] + " is not configured in this plugin.");
			Player player = (Player) sender;
			ItemStack item = player.getItemInHand();
			return CommandUtil.success(sender, command.getPrefix() + " &fBlockType &a" + item.getTypeId() + ":"
					+ item.getData().getData() + " &fis not configured in this plugin.");
		}

		Rank r = args.length == 0 ? UserManager.getInstance().getOnlineUser((Player) sender).getRank()
				: RankManager.getInstance().getRank(args[1]);
		if (r == null)
			return CommandUtil.success(sender, command.getPrefix() + " &cError: Rank " + args[1] + " does not exist!");

		WorldBlockConfig config = WorldBlockConfig.getInstance(block);
		int limit = config.getLimit(r);
		if (limit == -1)
			return CommandUtil.success(sender, command.getPrefix() + " &aThere is no limit for this item!");
		if (limit == 0)
			return CommandUtil.success(sender, command.getPrefix() + " &aThis item is restricted for your rank!");

		sender.sendMessage(StringUtil.parseColours(command.getPrefix() + " &aThe limit for "
				+ block.getReadable() + "'s at rank " + r.getName() + " is " + limit + "."));
		if (sender instanceof Player && limit > 0) {
			Player player = (Player) sender;
			int amountLeft = limit - UserManager.getInstance().getOnlineUser(player).getWorldBlocks(block).size();
			if (amountLeft > 0) {
				sender.sendMessage(StringUtil.parseColours(
						command.getPrefix() + " &aYou may place another " + amountLeft + " of this block type."));
			} else if (amountLeft == 0) {
				sender.sendMessage(
						StringUtil.parseColours(command.getPrefix() + " &aYou have reached the limit for this rank."));
			} else {
				ConsoleUtil.sendMessage(command.getPrefix() + " &cError:User " + player.getName()
						+ " has exceeded the placement limit for blocktype " + block.name().toLowerCase() + " by "
						+ Math.abs(amountLeft));
			}
		}
		return true;
	}

	@Override
	public String permissionRequired() {
		return null;
	}

}
