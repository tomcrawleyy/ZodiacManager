package com.zodiacmc.ZodiacManager.Malls.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;
import com.zodiacmc.ZodiacManager.Malls.Enums.MallPermissionType;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;

public class SetWarp extends SubCommand {

	public SetWarp() {
		super("SetWarp", true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		User user = UserManager.getInstance().getOnlineUser(sender.getName());
		Player player = (Player) sender;
		Mall mall = null;
		for (Mall localMall : Mall.getMalls()) {
			if (localMall.getCuboid().isInCuboid(player.getLocation())) {
				mall = localMall;
				break;
			}
		}
		if (mall == null)
			return this.error("You must be inside a mall to perform this command!");
		Shop shop = null;
		for (Shop localShop : mall.getShops()) {
			if (localShop.getCuboid().isInCuboid(player.getLocation())) {
				shop = localShop;
				break;
			}
		}
		if (shop == null)
			return this.error("You must be standing inside a shop to perform this command!");
		if (shop.getOwner() != user) {
			if (!shop.getTrustedUsers().containsKey(user)
					| shop.getTrusteesPermissions(user).contains(MallPermissionType.SETWARP))
				return this.error("You do not have the required permissions to set "
								+ shop.getOwner().getName() + "'s shop warp.");
		}
		shop.setWarp(player.getLocation());
		return this.resolve("Shop warp has been successfully updated!");
	}

}
