package com.zodiacmc.ZodiacManager.Malls.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Economy.EconomyManager;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;
import com.zodiacmc.ZodiacManager.Malls.Enums.MallPermissionType;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;

import net.milkbowl.vault.economy.EconomyResponse;

public class RenewLease extends SubCommand {

	public RenewLease() {
		super("RenewLease", true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		if (args.length == 0)
			return this.usage("AutoMalls RenewLease <Days>");
		int days;
		try {
			days = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			return this.usage("AutoMalls RenewLease <Days>");
		}
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
			if (localShop.getCuboid().isInCuboid(p.getLocation())) {
				shop = localShop;
				break;
			}
		}
		if (shop == null)
			return this.error("You must be standing inside of a shop to perform this command!");
		if (shop.getOwner() != u) {
			if ((!shop.getTrustedUsers().containsKey(u)
					|| !shop.getTrusteesPermissions(u).contains(MallPermissionType.RENEW))
					&& !IgnoreProtection.getUsersIgnoringProtection().contains(u)) {
				return this.error("You do not have the required permissions to renew this shops lease!");
			}
		}
		EconomyResponse r = EconomyManager.getInstance().withdraw(u, (double)days * shop.getPrice());
		if (!r.transactionSuccess())
			return this.error("You do not have the required funds to perform this command!");
		shop.addDays(days);
		shop.saveConfig();
		return this.success("You have successfully added " + days + " days to the shop lease.");
	}

}
