package com.zodiacmc.ZodiacManager.Malls.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Malls.Models.Mall;
import com.zodiacmc.ZodiacManager.Utilities.CommandUtil;

public class DeleteMall extends SubCommand {
	
	public DeleteMall() {
		super("DeleteMall", false, true);
	}
	
	public boolean processCommand(CommandSender sender, String[] args) {
		if (args.length > 1) {
			return CommandUtil.success(sender, command.getPrefix() + " &cUsage: /AutoMalls DeleteMall <Optional<Default|Donor>>");
		}
		if (args.length == 0) {
			if (!(sender instanceof Player))
				return CommandUtil.success(sender, command.getPrefix() + " &cUsage: /AutoMalls DeleteMall <Default|Donor>");
			Player player = (Player) sender;
			Mall mall = null;
			for (Mall localMall : Mall.getMalls()) {
				if (localMall.getCuboid().isInCuboid(player.getLocation())) {
					mall = localMall;
					break;
				}
			}
			if (mall == null)
				return CommandUtil.success(sender, command.getPrefix() + " &cError: Either stand in the mall you wish to delete or specify using /AutoMalls DeleteMall <Default|Donor>");
			Mall.getMalls().remove(mall);
			//TODO save mall config
		}
		return CommandUtil.success(sender, command.getPrefix());
	}

	public String permissionRequired() {
		return "AutoMalls.DeleteMall";
	}
}
