package com.zodiacmc.ZodiacManager.AutoRank.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.AutoRank.Ranking.Rank;
import com.zodiacmc.ZodiacManager.AutoRank.Ranking.RankManager;
import com.zodiacmc.ZodiacManager.AutoRank.Ranking.RankUpdater;
import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.CommandUtil;

public class SetRank extends SubCommand {

	public SetRank() {
		super("SetRank", false, true);
	}

	@Override
	public boolean processCommand(CommandSender sender, String[] args) {
		if (args.length != 2)
			return CommandUtil.success(sender, command.getPrefix() + " &cUsage: /AR SetRank PlayerName Rank");
		Rank rank = RankManager.getInstance().getRank(args[1]);
		if (rank == null)
			return CommandUtil.success(sender, command.getPrefix() + " &cError: Rank does not exist!");
		if (sender instanceof Player) {
			Player source = (Player) sender;
			if (!source.hasPermission("AutoRank.SetRank." + rank.getName()))
				return CommandUtil.success(sender,
						command.getPrefix() + " &cError: You do not have the required permission for this rank!");
		}
		Player player = Bukkit.getPlayer(args[0]);
		if (player == null)
			return CommandUtil.success(sender, command.getPrefix() + " &cError: Player is not online!");
		User user = UserManager.getInstance().getOnlineUser(player);
		Rank userRank = user.getRank();
		RankUpdater.getInstance().processRankUp(user, rank);
		return CommandUtil.success(sender, command.getPrefix() + " &aRankup for " + user.getName() + " from " + userRank.getName() + " to rank " + rank.getName() + " processed successfully!");
	}

	@Override
	public String permissionRequired() {
		return "AutoRank.SetRank";
	}

}
