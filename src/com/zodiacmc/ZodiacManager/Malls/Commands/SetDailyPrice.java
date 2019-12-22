package com.zodiacmc.ZodiacManager.Malls.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;

public class SetDailyPrice extends SubCommand {
	
	public SetDailyPrice() {
		super("SetDailyPrice", true);
	}
	
	public boolean processCommand(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		if (args.length != 1)
			return this.usage("AutoMalls SetDailyPrice <Price>");
		int amount;
		try {
			amount = Integer.parseInt(args[0]);
		} catch(NumberFormatException e) {
			return this.usage("AutoMalls SetDailyPrice <Price>");
		}
		Mall mall = null;
		for (Mall localMall : Mall.getMalls()) {
			if (localMall.getCuboid().isInCuboid(p.getLocation())) {
				mall = localMall;
				break;
			}
		}
		if (mall == null)
			return this.error("You must be standing inside of a shop to perform this command!");
		Shop shop = null;
		for (Shop localShop : mall.getShops()) {
			if (localShop.getCuboid().isInCuboid(p.getLocation())) {
				shop = localShop;
				break;
			}
		}
		if (shop == null)
			return this.error("You must be standing inside of a shop to perform this command!");
		shop.setPrice(amount);
		return this.success("Shop price has been successfully updated to: " + amount);
	}

}
