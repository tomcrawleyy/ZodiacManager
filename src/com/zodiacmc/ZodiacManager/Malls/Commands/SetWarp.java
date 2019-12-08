package com.zodiacmc.ZodiacManager.Malls.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Malls.Enums.MallPermissionType;
import com.zodiacmc.ZodiacManager.Malls.Models.Mall;
import com.zodiacmc.ZodiacManager.Malls.Models.Shop;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.CommandUtil;

public class SetWarp extends SubCommand {

	public SetWarp() {
		super("SetWarp", true, true);
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
			return CommandUtil.success(sender,
					command.getPrefix() + "&cError: You must be inside a mall to perform this command!");
		Shop shop = null;
		for (Shop localShop : mall.getShops()) {
			if (localShop.getCuboid().isInCuboid(player.getLocation())) {
				shop = localShop;
				break;
			}
		}
		if (shop == null)
			return CommandUtil.success(sender,
					command.getPrefix() + " &cError: You must be standing inside a shop to perform this command!");
		if (shop.getOwner() != user) {
			if (!shop.getTrustedUsers().containsKey(user)
					| shop.getTrusteesPermissions(user).contains(MallPermissionType.SETWARP))
				return CommandUtil.success(sender,
						command.getPrefix() + " &cError: You do not have the required permissions to set "
								+ shop.getOwner().getName() + "'s shop warp.");
		}
		shop.setWarp(player.getLocation());
		return CommandUtil.success(sender, command.getPrefix() + " &aShop warp has been successfully updated!");
	}

	public String permissionRequired() {
		return "AutoMalls.SetWarp";
	}

}
