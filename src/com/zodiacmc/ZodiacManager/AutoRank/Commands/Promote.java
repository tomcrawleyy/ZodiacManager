package com.zodiacmc.ZodiacManager.AutoRank.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.AutoRank.Ranking.Rank;
import com.zodiacmc.ZodiacManager.AutoRank.Ranking.RankUpdater;
import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.CommandUtil;

public class Promote extends SubCommand {
	
	public Promote() {
		super("Promote", false, true);
	}
	
	@Override
	public boolean processCommand(CommandSender sender, String[] args) {
		if (args.length != 1)
			return CommandUtil.success(sender, command.getPrefix() + " &cUsage: /AR Promote %PlayerName%");
		Player player = Bukkit.getPlayer(args[0]);
		if (player == null)
			return CommandUtil.success(sender, command.getPrefix() + " &cError: Player is not online!");
		User user = UserManager.getInstance().getOnlineUser(player);
		Rank rank = user.getRank();
		if (sender instanceof Player) {
			Player source = (Player)sender;
			if (!source.hasPermission("AutoRank.Promote." + rank.getName()))
				return CommandUtil.success(sender, command.getPrefix() + " &cError: You do not have the required permissions to rank up this user.");
		}
		if (rank.hasNextRank()) {
			RankUpdater.getInstance().processRankUp(user, rank.getNextRank());
			return CommandUtil.success(sender, command.getPrefix()+ " &aPlayer promotion for " + user.getName() + " from " + rank.getName() + " to rank " + rank.getNextRank().getName() + " processed successfully!");
		}
		return CommandUtil.success(sender, command.getPrefix() + " &cError: Player does not have a rankup available!");
	}
	
	public String permissionRequired() {
		return "AutoRank.Promote";
	}

}
