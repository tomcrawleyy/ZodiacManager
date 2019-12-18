package com.zodiacmc.ZodiacManager.AutoRank.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.AutoRank.Ranking.Rank;
import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;

public class Demote extends SubCommand {
	
	public Demote() {
		super("Demote", false);
	}

	@Override
	public boolean processCommand(CommandSender sender, String[] args) {
		if (args.length != 1)
			return this.usage("AutoRank Demote <PlayerName>");
		Player player = Bukkit.getPlayer(args[0]);
		if (player == null)
			return this.error("Player is not online!");
		User user = UserManager.getInstance().getOnlineUser(player);
		Rank rank = user.getRank();
		if (sender instanceof Player) {
			Player source = (Player)sender;
			if (!source.hasPermission("AutoRank.Demote." + rank.getName()))
				return this.error("You do not have the required permissions to demote this user.");
		}
		if (rank.hasPreviousRank()) {
			user.setRank(rank.getPreviousRank());
			return this.success("Demotion for " + user.getName() + " from " + rank.getName() + "to rank " + rank.getPreviousRank().getName() + " processed successfully!");
		}
		return this.error("Player does not have a rankup available!");
	}
	
	

}
