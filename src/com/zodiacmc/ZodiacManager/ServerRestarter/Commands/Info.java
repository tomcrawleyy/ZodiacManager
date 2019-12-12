package com.zodiacmc.ZodiacManager.ServerRestarter.Commands;

import org.bukkit.command.CommandSender;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.ServerRestarter.Scheduling.RestartScheduler;

public class Info extends SubCommand {

	public Info() {
		super("Info", false, false);
	}

	@Override
	public boolean processCommand(CommandSender sender, String[] args) {
		if (args.length > 0)
			return this.usage("ServerRestarter Info");
		return this.resolve("There is &f[&d" + RestartScheduler.getInstance().getTimeLeft() + "&f] &auntil the next scheduled restart.");
	}

	@Override
	public String permissionRequired() {
		return null;
	}

}
