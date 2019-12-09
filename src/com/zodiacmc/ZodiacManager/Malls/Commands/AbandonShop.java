package com.zodiacmc.ZodiacManager.Malls.Commands;

import org.bukkit.command.CommandSender;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;

public class AbandonShop extends SubCommand {
	
	public AbandonShop() {
		super("AbandonShop", true, true);
	}
	
	public boolean processCommand(CommandSender sender, String[] args) {
		return this.resolve("");
	}
	
	public String permissionRequired() {
		return "AutoMalls.AbandonShop";
	}

}
