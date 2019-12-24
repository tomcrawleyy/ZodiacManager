package com.zodiacmc.ZodiacManager.Malls.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Malls.Configurations.MallConfig;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;

public class AbandonShop extends SubCommand {

	public AbandonShop() {
		super("AbandonShop", true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		User user = UserManager.getInstance().getOnlineUser(sender.getName());
		Player player = (Player) sender;
		Mall mall = null;
		Shop shop = null;
		if (args.length > 1)
			return this.usage("AutoMalls AbandonShop <Optional<Default|Donor>>");
		if (args.length == 0) {
			for (Mall localMall : Mall.getMalls()) {
				if (localMall.getCuboid().isInCuboid(player.getLocation())) {
					mall = localMall;
					break;
				}
			}
			if (mall == null)
				return this.error("You must either specify a mall or stand in the shop you wish to abandon.");
			for (Shop localShop : mall.getShops()) {
				if (localShop.getCuboid().isInCuboid(player.getLocation())) {
					if (localShop.getOwner() == user || IgnoreProtection.getUsersIgnoringProtection().contains(user)) {
						shop = localShop;
						break;
					}
				}
			}
		} else {
			if (!args[0].equalsIgnoreCase("Donor") && !args[0].equalsIgnoreCase("Default"))
				return this.usage("AutoMalls AbandonShop <Default|Donor>");
			for (Mall localMall : Mall.getMalls()) {
				if (localMall.getType().getReadableName().equalsIgnoreCase(args[0])) {
					mall = localMall;
					break;
				}
			}
			for (Shop localShop : mall.getShops()) {
				if (localShop.getOwner() == user) {
					shop = localShop;
					break;
				}
			}
		}
		if (shop == null)
			return this.error("You do not own a shop in the " + mall.getType().getReadableName() + " mall!");
		shop.reset();
		MallConfig.getInstance().saveMalls();
		return this.success("Shop has successfully been abandoned!");
	}

}
