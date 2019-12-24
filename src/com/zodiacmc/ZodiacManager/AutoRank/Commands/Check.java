package com.zodiacmc.ZodiacManager.AutoRank.Commands;

import java.util.concurrent.TimeUnit;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.AutoRank.Ranking.Rank;
import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;
import com.zodiacmc.ZodiacManager.Utilities.StringUtil;
import com.zodiacmc.ZodiacManager.Utilities.TimeUtil;

public class Check extends SubCommand {
	
	public Check() {
		super("Check", false);
	}

	@Override
	public boolean processCommand(CommandSender sender, String[] args) {
		UserManager userManager = UserManager.getInstance();
		if(args.length == 0) {
			if (!(sender instanceof Player)) 
				return this.usage("AutoRank Check <PlayerName>");
			Player player = (Player) sender;
			User u = userManager.getOnlineUser(player);
			player.sendMessage(StringUtil.parseColours(command.getPrefix() + " &aYou have played for: &d" + u.getFormattedTimePlayed() + " &aand are Rank &d" + u.getRank().getName()));
			Rank r = u.getRank().getNextRank();
			if (r != null) {
				long timeRequired = TimeUnit.HOURS.toMillis((long)r.getHoursRequired()) - u.getTimePlayed(true);
				ConsoleUtil.sendMessage(timeRequired + "");
				return this.resolve("and will rank up to &d" + r.getName() + " &ain " + TimeUtil.getReadableTime(timeRequired, TimeUnit.MILLISECONDS, false));
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
					return this.resolve(u.getName() + " has played for: &d" + u.getFormattedTimePlayed());
				} else {
					return this.error(u.getName() + " is not a valid user.");
				}
			}
			return this.error("you do not have the required permissions to check others' rank status.");
		} else {
			return this.usage("AR Check <optional>playerName");
		}
	}

}
