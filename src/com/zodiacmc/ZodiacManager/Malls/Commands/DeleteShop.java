package com.zodiacmc.ZodiacManager.Malls.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Malls.Models.Mall;
import com.zodiacmc.ZodiacManager.Malls.Models.Shop;
import com.zodiacmc.ZodiacManager.Utilities.CommandUtil;

public class DeleteShop extends SubCommand {
	
	public DeleteShop() {
		super("DeleteShop", true, true);
	}
	
	public boolean processCommand(CommandSender sender, String[] args) {
		Player p = (Player)sender;
		if (args.length != 0)
			return CommandUtil.success(sender, command.getPrefix() + " &cUsage: /AutoMalls DeleteShop");
		Mall mall = null;
		for (Mall localMall : Mall.getMalls()) {
			if (localMall.getCuboid().isInCuboid(p.getLocation())) {
				mall = localMall;
				break;
			}
		}
		if (mall == null)
			return CommandUtil.success(sender, command.getPrefix() + " &cError: You must be standing in a shop to perform this command.");
		Shop shop = null;
		for (Shop localShop : mall.getShops()) {
			if (localShop.getCuboid().isInCuboid(p.getLocation())) {
				shop = localShop;
				break;
			}
		}
		if (shop == null)
			return CommandUtil.success(sender, command.getPrefix() + " &cError: You must be standing in a shop to perform this command.");
		int id = shop.getId();
		mall.getShops().remove(shop);
		for (Shop localShop : mall.getShops()) {
			if (localShop.getId() > id) {
				localShop.setId(shop.getId() -1);
			}
		}
		//TODO save mall config
		return CommandUtil.success(sender, command.getPrefix() + " &aThe shop you are standing in has now been removed from the mall.");
	}
	
	public String permissionRequired() {
		return "AutoMalls.DeleteShop";
	}

}
