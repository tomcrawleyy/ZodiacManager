package com.zodiacmc.ZodiacManager.Malls.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidFactoryManager;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;

public class RedefineShop extends SubCommand {

	public RedefineShop() {
		super("RedefineShop", true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		User u = UserManager.getInstance().getOnlineUser(sender.getName());
		CuboidFactoryManager cuboidFactoryManager = CuboidFactoryManager.getInstance();
		Player p = (Player) sender;
		Shop shop = null;
		Mall mall = null;
		if (cuboidFactoryManager.isInSetupMode(u))
			return this.error("You are already in setup mode! Please finish any other cuboid creations before this or type /AutoMalls ExitSetupMode");
		for (Mall localMall : Mall.getMalls()) {
			if (localMall.getCuboid().isInCuboid(p.getLocation())) {
				mall = localMall;
				break;
			}
		}
		if (mall == null)
			return this.error("You must be standing in a mall to perform this command!");
		for (Shop localShop : mall.getShops()) {
			if (localShop.getCuboid().isInCuboid(p.getLocation())) {
				shop = localShop;
				break;
			}
		}
		if (shop == null) 
			return this.error("You must be standing inside a shop to specify which one!");
		
		return this.success("");
	}

}
