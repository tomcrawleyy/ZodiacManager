package com.zodiacmc.ZodiacManager.Malls.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;

public class IgnoreProtection extends SubCommand {
	
	private static List<User> ignoredUsers = new ArrayList<User>();
	
	public IgnoreProtection() {
		super("IgnoreProtection", true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		User u = UserManager.getInstance().getOnlineUser(sender.getName());
		if (args.length == 1) {
			if (!args[0].equalsIgnoreCase("on") && !args[0].equalsIgnoreCase("off")) {
				return this.usage("AutoMalls IgnoreProtection <Optional<On|Off>>");
			}
			if (args[0].equalsIgnoreCase("on")) {
				if (ignoredUsers.contains(u)) {
					return this.error("You are already ignoring mall protection!");
				}
				ignoredUsers.add(u);
				return this.resolve("You are now ignoring mall protection!");
			}
			if (!ignoredUsers.contains(u)) {
				return this.error("You aren't ignoring mall protection!");
			}
			return this.resolve("You are no longer ignoring mall protection!");
		}
		if (ignoredUsers.contains(u)) {
			ignoredUsers.remove(u);
			return this.resolve("You are no longer ignoring mall protection!");
		}
		ignoredUsers.add(u);
		return this.resolve("You are now ignoring mall protection!");
	}
	
	public static List<User> getUsersIgnoringProtection(){
		return ignoredUsers;
	}

}
