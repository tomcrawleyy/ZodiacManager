package com.zodiacmc.ZodiacManager.Malls.Commands;

import java.util.concurrent.TimeUnit;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Economy.EconomyManager;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;

import net.milkbowl.vault.economy.EconomyResponse;

public class RentShop extends SubCommand {

	public RentShop() {
		super("RentShop", true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		if (args.length > 0)
			return this.usage("AutoMalls RentShop");
		User u = UserManager.getInstance().getOnlineUser(sender.getName());
		Player p = (Player) sender;
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
			if (localShop.getOwner() == u)
				return this.error("You already own a shop in this mall!");
			if (localShop.getCuboid().isInCuboid(p.getLocation())) {
				shop = localShop;
			}
		}
		if (shop.getOwner() != null)
			return this.error("This shop is already owned by another user!");
		EconomyResponse r = EconomyManager.getInstance().withdraw(u, (double) shop.getPrice() * 7);
		if (!r.transactionSuccess())
			return this.error("You do not have the required funds to rent this shop!");
		shop.setOwner(u);
		shop.setTimeLeft(7, TimeUnit.DAYS);
		shop.saveConfig();
		return this.success(
				"You have successfully rented this shop for 7 days! To add more use /AutoMalls RenewLease <DaysAmount>");
	}

}
