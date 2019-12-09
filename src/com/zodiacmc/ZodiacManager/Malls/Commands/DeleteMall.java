package com.zodiacmc.ZodiacManager.Malls.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Malls.Models.Mall;

public class DeleteMall extends SubCommand {
	
	public DeleteMall() {
		super("DeleteMall", false, true);
	}
	
	public boolean processCommand(CommandSender sender, String[] args) {
		if (args.length > 1) {
			return this.usage("AutoMalls DeleteMall <Optional<Default|Donor>>");
		}
		if (args.length == 0) {
			if (!(sender instanceof Player))
				return this.usage("/AutoMalls DeleteMall <Default|Donor>");
			Player player = (Player) sender;
			Mall mall = null;
			for (Mall localMall : Mall.getMalls()) {
				if (localMall.getCuboid().isInCuboid(player.getLocation())) {
					mall = localMall;
					break;
				}
			}
			if (mall == null)
				return this.error("Either stand in the mall you wish to delete or specify using /AutoMalls DeleteMall <Default|Donor>");
			Mall.getMalls().remove(mall);
			//TODO save mall config
			return this.resolve("Mall has successfully been removed!");
		}
		if (!args[0].equalsIgnoreCase("default") && !args[0].equalsIgnoreCase("donor"))
			return this.usage("AutoMalls DeleteMall <Default|Donor>");
		Mall mall = null;
		for (Mall localMall : Mall.getMalls()) {
			if (localMall.getType().name().equalsIgnoreCase(args[0])) {
				mall = localMall;
				break;
			}
		}
		Mall.getMalls().remove(mall);
		//TODO Save mall config
		return this.resolve("Mall has successfully been removed!");
	}

	public String permissionRequired() {
		return "AutoMalls.DeleteMall";
	}
}
