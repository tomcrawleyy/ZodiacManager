package com.zodiacmc.ZodiacManager.Malls.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Malls.Enums.MallType;
import com.zodiacmc.ZodiacManager.Malls.Models.Mall;
import com.zodiacmc.ZodiacManager.Malls.Models.Shop;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.CommandUtil;

public class Warp extends SubCommand {

	public Warp() {
		super("Warp", true, true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		User user = UserManager.getInstance().getOnlineUser(sender.getName());
		Player player = (Player) sender;
		if (args.length == 0 | args.length > 2) {
			return CommandUtil.success(sender,
					command.getPrefix() + " &cUsage: /AutoMalls Warp <PlayerName> <Optional<Donor|Default>>");
		}
		if (args.length == 2) {
			Mall mall = null;
			for (Mall localMall : Mall.getMalls()) {
				if (localMall.getType().getReadableName().equalsIgnoreCase(args[1])) {
					mall = localMall;
					break;
				}
			}
			if (mall == null) {
				return CommandUtil.success(sender,
						command.getPrefix() + " &cUsage: /AutoMalls Warp <PlayerName> <Optional<Donor|Default>>");
			}
			Shop shop = null;
			for (Shop localShop : mall.getShops()) {
				if (localShop.getOwner().getName().equalsIgnoreCase(args[0])) {
					shop = localShop;
					break;
				}
			}
			if (shop == null) {
				return CommandUtil.success(sender,
						command.getPrefix() + " &cError: This player does not own a shop in the "
								+ mall.getType().getReadableName() + " mall.");
			}
			player.teleport(shop.getWarp());
			return CommandUtil.success(sender, command.getPrefix() + " &aYou have been successfully teleported to "
					+ shop.getOwner().getName() + "'s shop in the " + mall.getType().getReadableName() + " mall.");
		}
		List<Shop> ownedShops = new ArrayList<Shop>();
		for (Mall mall : Mall.getMalls()) {
			for (Shop shop : mall.getShops()) {
				if (shop.getOwner().getName().equalsIgnoreCase(args[0])) {
					ownedShops.add(shop);
					break;
				}
			}
		}
		if (ownedShops.isEmpty())
			return CommandUtil.success(sender, command.getPrefix() + " &cError: This player does not own a shop.");
		if (ownedShops.size() > 1) {
			if (player.hasPermission("AutoMalls.Entry.Donor"))
				return CommandUtil.success(sender, command.getPrefix()
						+ " &aUser has a shop in multiple malls which you can access, please specify using /AutoMalls Warp <PlayerName> <Donor|Default>");
			for (Shop shop : ownedShops) {
				if (shop.getMall().getType() == MallType.DEFAULT) {
					player.teleport(shop.getWarp());
					return CommandUtil.success(sender,
							command.getPrefix() + "&aYou have been successfully teleported to "
									+ shop.getOwner().getName() + "'s shop in the "
									+ shop.getMall().getType().getReadableName() + " mall.");
				}
			}
		}
		Shop shop = ownedShops.get(0);
		if (shop.getMall().getType() == MallType.DONOR) {
			if (!player.hasPermission("AutoMalls.Entry.Donor"))
				return CommandUtil.success(sender, command.getPrefix()
						+ " &cError: This player's only shop is in a mall you dont have access to.");
		}
		player.teleport(shop.getWarp());
		return CommandUtil.success(sender,
				command.getPrefix() + " &aYou have been successfully teleported to " + shop.getOwner().getName()
						+ "'s shop in the " + shop.getMall().getType().getReadableName() + " mall.");
	}

	public String permissionRequired() {
		return "AutoMalls.Warp";
	}

}
