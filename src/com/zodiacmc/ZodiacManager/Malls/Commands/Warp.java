package com.zodiacmc.ZodiacManager.Malls.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;
import com.zodiacmc.ZodiacManager.Malls.Enums.MallType;

public class Warp extends SubCommand {

	public Warp() {
		super("Warp", true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length == 0 | args.length > 2) {
			return this.usage("AutoMalls Warp <PlayerName> <Optional<Donor|Default>>");
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
				return this.usage("AutoMalls Warp <PlayerName> <Optional<Donor|Default>>");
			}
			Shop shop = null;
			for (Shop localShop : mall.getShops()) {
				if (localShop.getOwner().getName().equalsIgnoreCase(args[0])) {
					shop = localShop;
					break;
				}
			}
			if (shop == null)
				return this.error("This player does not own a shop in the " + mall.getType().getReadableName() + " mall.");
			player.teleport(shop.getWarp());
			return this.resolve("You have been successfully teleported to "
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
			return this.error("This player does not own a shop.");
		if (ownedShops.size() > 1) {
			if (player.hasPermission("AutoMalls.Entry.Donor"))
				return this.error("User has a shop in multiple malls which you can access, please specify using /AutoMalls Warp <PlayerName> <Donor|Default>");
			for (Shop shop : ownedShops) {
				if (shop.getMall().getType() == MallType.DEFAULT) {
					player.teleport(shop.getWarp());
					return this.resolve("You have been successfully teleported to "
									+ shop.getOwner().getName() + "'s shop in the "
									+ shop.getMall().getType().getReadableName() + " mall.");
				}
			}
		}
		Shop shop = ownedShops.get(0);
		if (shop.getMall().getType() == MallType.DONOR) {
			if (!player.hasPermission("AutoMalls.Entry.Donor"))
				return this.error("This player's only shop is in a mall you dont have access to.");
		}
		player.teleport(shop.getWarp());
		return this.resolve("You have been successfully teleported to " + shop.getOwner().getName()
						+ "'s shop in the " + shop.getMall().getType().getReadableName() + " mall.");
	}

}
