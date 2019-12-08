package com.zodiacmc.ZodiacManager.AutoRank.Commands;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.CommandUtil;
import com.zodiacmc.ZodiacManager.Utilities.TimeUtil;

public class SetPlaytime extends SubCommand {

	public SetPlaytime() {
		super("SetPlaytime", false, true);
	}

	@Override
	public boolean processCommand(CommandSender sender, String[] args) {
		if (args.length != 3)
			return CommandUtil.success(sender, command.getPrefix()
					+ " &cUsage: /AR SetPlaytime PlayerName Time TimeUnit(Hours, Days, Minutes, Seconds)");
		String playerString = args[0];
		int time;
		try {
			time = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			return CommandUtil.success(sender, command.getPrefix()
					+ " &cUsage: /AR SetPlaytime PlayerName Time TimeUnit(Hours, Days, Minutes, Seconds)");
		}
		TimeUnit unit = null;
		for (TimeUnit checkUnit : TimeUnit.values()) {
			if (checkUnit.name().equalsIgnoreCase(args[2]))
				unit = checkUnit;
		}
		if (unit == null)
			return CommandUtil.success(sender, command.getPrefix()
					+ " &cUsage: /AR SetPlaytime PlayerName Time TimeUnit(Hours, Days, Minutes, Seconds)");
		if (sender.getName().equalsIgnoreCase(playerString)) {
			if (!sender.hasPermission("AutoRank.SetPlaytime.Self")) {
				return CommandUtil.success(sender,
						command.getPrefix() + " &cError: You do not have permission to edit your own platime!");
			}
		}
		Player player = Bukkit.getPlayer(playerString);;
		if (player == null)
			return CommandUtil.success(sender, command.getPrefix() + " &cError: That player is not online!");
		User user = UserManager.getInstance().getOnlineUser(player);
		user.setTimePlayed(unit.toMillis(time));
		return CommandUtil.success(sender,  command.getPrefix() + " &a" + user.getName() + "'s Playtime has successfully been set to " + TimeUtil.getReadableTime(user.getTimePlayed(true), TimeUnit.MILLISECONDS, false));
	}

	@Override
	public String permissionRequired() {
		return "AutoRank.SetPlaytime";
	}

}
