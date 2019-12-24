package com.zodiacmc.ZodiacManager.ServerRestarter.Commands;


import org.bukkit.command.CommandSender;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.ServerRestarter.Scheduling.RestartScheduler;

public class Restart extends SubCommand {
	
	public Restart() {
		super("Restart", false);
	}

	@Override
	public boolean processCommand(CommandSender sender, String[] args) {
		RestartScheduler.getInstance().forceRestart();
		return this.success("The server will now restart!");
	}

	@Override
	public String permissionRequired() {
		return "ServerRestarter.Restart";
	}

}
