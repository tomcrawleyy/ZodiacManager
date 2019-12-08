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
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.CommandUtil;
import com.zodiacmc.ZodiacManager.Utilities.TimeUtil;

public class CheckRemovalDelay extends SubCommand {

	public CheckRemovalDelay() {
		super("CheckRemovalDelay", false, false);
	}

	@Override
	public boolean processCommand(CommandSender sender, String[] args) {
		if (args.length != 2) {
			if (!(sender instanceof Player)) {
				return CommandUtil.success(sender,
						command.getPrefix() + " &cUsage: /ChunkManager CheckRemovalDelay <Block> <Rank>");
			}
		}
		if (args.length == 1)
			return CommandUtil.success(sender, command.getPrefix()
					+ "&cUsage: /ChunkManager CheckRemovalDelay or /ChunkManager CheckRemovalDelay <Block> <Rank>");
		WorldBlockType type = null;
		for (WorldBlockType localType : WorldBlockType.values()) {
			if (args.length == 2) {
				if (localType.name().equalsIgnoreCase(args[0])) {
					type = localType;
					break;
				}
			} else {
				Player player = (Player) sender;
				ItemStack block = player.getItemInHand();
				if (localType.getID() == block.getTypeId()) {
					if (localType.getData() != -1) {
						if (localType.getData() == (byte) block.getData().getData()) {
							type = localType;
							break;
						}
					} else {
						type = localType;
						break;
					}
				}
			}
		}
		if (type == null) {
			if (args.length == 2)
				return CommandUtil.success(sender,
						command.getPrefix() + " &fBlockType: &a" + args[0] + " &fis not configured in this plugin!");
			Player player = (Player) sender;
			ItemStack item = player.getItemInHand();
			return CommandUtil.success(sender, command.getPrefix() + " &fBlockType &a" + item.getTypeId() + ":"
					+ item.getData().getData() + " &fis not configured in this plugin.");
		}
		Rank r = args.length == 0 ? UserManager.getInstance().getOnlineUser((Player) sender).getRank()
				: RankManager.getInstance().getRank(args[1]);
		if (r == null)
			return CommandUtil.success(sender, command.getPrefix() + " &cError: Rank " + args[1] + " does not exist!");
		WorldBlockConfig config = WorldBlockConfig.getInstance(type);
		if (!config.destroyOnLogout())
			return CommandUtil.success(sender,
					command.getPrefix() + " &aThis block does not get removed after you log out!");
		long removalMinutes = config.getRemovalDelay(r, TimeUnit.MINUTES);
		if (removalMinutes == 0)
			return CommandUtil.success(sender,
					command.getPrefix() + " &aThis block will get removed as soon as you log out!");
		String time = TimeUtil.getReadableTime(removalMinutes, TimeUnit.MINUTES, false);
		if (args.length == 2)
			return CommandUtil.success(sender, command.getPrefix() + " &aBlockType: " + args[0] + " for Rank: " + args[1] + " Will remove " + time + " after Logout.");
		return CommandUtil.success(sender, command.getPrefix() + " &aBlockType:" + type.name() + " Will remove " + time + " after logout.");
	}

	@Override
	public String permissionRequired() {
		return null;
	}
}
