package com.zodiacmc.ZodiacManager.Malls.Commands;

import org.bukkit.command.CommandSender;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;

public class DeleteShops extends SubCommand {
	
	public DeleteShops() {
		super("DeleteShops", false);
	}
	
	public boolean processCommand(CommandSender sender, String[] args) {
		if (args.length == 0 || (!args[0].equalsIgnoreCase("Donor") && !args[0].equalsIgnoreCase("Default")))
			return this.usage("AutoMalls DeleteShops <Default|Donor>");
		Mall m = null;
		for (Mall mall : Mall.getMalls()) {
			if (mall.getType().getReadableName().equalsIgnoreCase(args[0])) {
				m = mall;
				break;
			}
		}
		int count = m.getShops().size();
		m.getShops().clear();
		m.saveConfig();
		return this.success("Successfully removed " + count + " shops from " + m.getType().getReadableName() + " mall!");
	}

}
