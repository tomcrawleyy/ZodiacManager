package com.zodiacmc.ZodiacManager.Malls.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.CommandUtil;

public class IgnoreProtection extends SubCommand {
	
	private static List<User> ignoredUsers = new ArrayList<User>();
	
	public IgnoreProtection() {
		super("IgnoreProtection", true, true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		User u = UserManager.getInstance().getOnlineUser(sender.getName());
		if (args.length == 1) {
			if (!args[0].equalsIgnoreCase("on") && !args[0].equalsIgnoreCase("off")) {
				return CommandUtil.success(sender, command.getPrefix() + " &cUsage: /AutoMalls IgnoreProtection <Optional<On|Off>>");
			}
			if (args[0].equalsIgnoreCase("on")) {
				if (ignoredUsers.contains(u)) {
					return CommandUtil.success(sender, command.getPrefix() + " &aYou are already ignoring mall protection!");
				}
				ignoredUsers.add(u);
				return CommandUtil.success(sender, command.getPrefix() + " &aYou are now ignoring mall protection!");
			}
			if (!ignoredUsers.contains(u)) {
				return CommandUtil.success(sender, command.getPrefix() + " &aYou aren't ignoring mall protection!");
			}
			return CommandUtil.success(sender, command.getPrefix() + " &aYou are no longer ignoring mall protection!");
		}
		if (ignoredUsers.contains(u)) {
			ignoredUsers.remove(u);
			return CommandUtil.success(sender, command.getPrefix() + " &aYou are no longer ignoring mall protection!");
		}
		ignoredUsers.add(u);
		return CommandUtil.success(sender, command.getPrefix() + " &aYou are now ignoring mall protection!");
	}
	
	public static List<User> getUsersIgnoringProtection(){
		return ignoredUsers;
	}
	
	public String permissionRequired() {
		return "AutoMalls.IgnoreProtection";
	}

}
