package com.zodiacmc.ZodiacManager.AutoRank.Commands;

import java.util.concurrent.TimeUnit;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.AutoRank.Ranking.Rank;
import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.CommandUtil;
import com.zodiacmc.ZodiacManager.Utilities.StringUtil;
import com.zodiacmc.ZodiacManager.Utilities.TimeUtil;

public class Check extends SubCommand {
	
	public Check() {
		super("Check", false, true);
	}

	@Override
	public boolean processCommand(CommandSender sender, String[] args) {
		UserManager userManager = UserManager.getInstance();
		if(args.length == 0) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(StringUtil.parseColours(command.getPrefix() + " &cError: This command is for players only!"));
				return true;
			}
			Player player = (Player) sender;
			User u = userManager.getOnlineUser(player);
			player.sendMessage(StringUtil.parseColours(command.getPrefix() + " &aYou have played for: &d" + u.getFormattedTimePlayed() + " &aand are Rank &d" + u.getRank().getName()));
			Rank r = u.getRank().getNextRank();
			if (r != null) {
				long timeRequired = TimeUnit.HOURS.toMillis((long)r.getHoursRequired()) - u.getTimePlayed(true);
				player.sendMessage(StringUtil.parseColours(command.getPrefix() + " &aand will rank up to &d" + r.getName() + " &ain " + TimeUtil.getReadableTime(timeRequired, TimeUnit.MILLISECONDS, false)));
			}
			return true;
		} else if (args.length == 1) {
			boolean hasPermission = true;
			if (sender instanceof Player) {
				Player player = (Player)sender;
				if (!player.hasPermission("AutoRank.Check.Others"))
					hasPermission = false;
			}
			if (hasPermission) {
				String playerName = args[0];
				User u = userManager.getOnlineUser(playerName);
				if (u == null)
					u = userManager.getOfflineUser(playerName);
				if (u.exists()) {
					sender.sendMessage(StringUtil.parseColours(command.getPrefix() + " &a" + u.getName() + " has played for: &d" + u.getFormattedTimePlayed()));
				} else {
					sender.sendMessage(StringUtil.parseColours(command.getPrefix() + " &cError: " + u.getName() + " is not a valid user."));
				}
				return true;
			}
			return CommandUtil.success(sender, command.getPrefix() + " &cError you do not have the required permissions to check others' rank status.");
		} else {
			return CommandUtil.success(sender, command.getPrefix() + " &cUsage: /AR Check <optional>playerName");
		}
	}

	@Override
	public String permissionRequired() {
		return "AutoRank.Check";
	}

}
