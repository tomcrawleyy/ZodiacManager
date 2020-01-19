package com.zodiacmc.ZodiacManager.MinimumPrices.Commands;

import java.util.regex.Pattern;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.MinimumPrices.Configurations.MinimumPriceConfig;
import com.zodiacmc.ZodiacManager.Models.WorldItem;

public class SetPrice extends SubCommand {

	public SetPrice() {
		super("SetPrice", false);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		MinimumPriceConfig instance = MinimumPriceConfig.getInstance();

		WorldItem worldItem = null;
		int price, donorPrice;
		if (args.length == 0)
			return (!(sender instanceof Player)) ? this.usage("MinimumPrices SetPrice <ItemID:ItemData> <Price> <DonorPrice>")
					: this.usage("MinimumPrices SetPrice <Optional<ItemID:ItemData>> <Price> <DonorPrice>");
		if (args.length != 3) {
			if (!(sender instanceof Player))
				return this.usage("MinimumPrices SetPrice <ItemID:ItemData> <Price> <DonorPrice>");
			Player player = (Player) sender;
			ItemStack item = player.getItemInHand();
			worldItem = new WorldItem(item.getTypeId(), item.getData().getData());
			try {
				price = Integer.parseInt(args[0]);
				donorPrice = Integer.parseInt(args[1]);
			} catch (NumberFormatException e) {
				return this.error("Price must be an integer.");
			}
		} else {
			Pattern p = Pattern.compile("\\d+:\\d{1,3}");
			if (!p.matcher(args[0]).matches()) {
				return this.usage("MinimumPrices SetPrice <ItemID:ItemData> <Price> <DonorPrice>");
			}
			String[] data = args[0].split(":");
			worldItem = new WorldItem(Integer.parseInt(data[0]), Byte.parseByte(data[1]));
			try {
				price = Integer.parseInt(args[1]);
				donorPrice = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				return this.error("Price must be an integer.");
			}
		}
		int count = 0;
		for (WorldItem localItem : instance.getMinimumPrices().keySet()) {
			if (worldItem.getId() == localItem.getId()) {
				if (worldItem.getData() == -1) {
					if (localItem.getData() != -1) {
						instance.removeMinimumPrice(localItem);
						count++;
					}
				} else if (localItem.getData() == worldItem.getData()) {
					instance.removeMinimumPrice(localItem);
				}
			}
		}
		instance.addMinimumPrice(worldItem, price, donorPrice);
		instance.updateConfig();
		if (worldItem.getData() == -1) {
			if (count < 1) {
				return this.success("The minimum price for everything with the ID of " + worldItem.getId()
						+ " has been set to: " + price + " and set to: " + donorPrice + " for donators.");
			} else {
				return this.success("The minimum price for everything with the ID of " + worldItem.getId()
						+ " has been set to: " + price + " and set to: " + donorPrice + " for donators and has overridden the prices for " + count
						+ " items starting with the ID of " + worldItem.getId());
			}
		}
		return this.success("The minimum price for " + worldItem.getId() + ":" + worldItem.getData()
				+ " has been set to: " + price + " and set to: " + donorPrice + " for donators.");
	}

}
