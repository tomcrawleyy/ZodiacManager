package com.zodiacmc.ZodiacManager.Malls.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;

public class SetMallWarp extends SubCommand {
	
	public SetMallWarp() {
		super("SetMallWarp", true);
	}
	
	public boolean processCommand(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		Mall mall = null;
		for (Mall localMall : Mall.getMalls()) {
			if (localMall.getCuboid().isInCuboid(p.getLocation())) {
				mall = localMall;
				break;
			}
		}
		if (mall == null)
			return this.error("You must be standing inside of a mall to perform this command!");
		mall.setWarp(p.getLocation());
		return this.success("Mall warp has successfully been set!");
	}

}
