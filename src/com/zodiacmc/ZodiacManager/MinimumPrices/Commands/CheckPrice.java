package com.zodiacmc.ZodiacManager.MinimumPrices.Commands;

import java.util.Map;
import java.util.regex.Pattern;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Models.WorldItem;
import com.zodiacmc.ZodiacManager.Plugins.MinimumPrices;
import com.zodiacmc.ZodiacManager.Utilities.CommandUtil;

public class CheckPrice extends SubCommand {

	public CheckPrice() {
		super("CheckPrice", false, false);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		Map<WorldItem, Integer> minimumPrices = MinimumPrices.getInstance().getMinimumPrices();
		if (args.length != 1) {
			if (!(sender instanceof Player)) {
				return CommandUtil.success(sender,
						command.getPrefix() + " &cUsage /MinimumPrices CheckPrice <ItemID:ItemData>");
			}
			Player player = (Player) sender;
			ItemStack item = player.getItemInHand();
			WorldItem worldItem = new WorldItem(item.getTypeId(), item.getData().getData());

			for (WorldItem localItem : minimumPrices.keySet()) {
				if (worldItem.getId() == localItem.getId()) {
					if (localItem.getData() == -1 | localItem.getData() == worldItem.getData()) {
						int minimumPrice = minimumPrices.get(localItem);
						return CommandUtil.success(sender, command.getPrefix()
								+ " &aThe minimum price for the item you are holding is " + minimumPrice + " each.");
					}
				}
			}
			return CommandUtil.success(sender,
					command.getPrefix() + " &aThere is no minimum price set for the item you are holding!");
		}
		Pattern p = Pattern.compile("\\d+:\\d{1,3}");
		if (!p.matcher(args[0]).matches())
			return CommandUtil.success(sender,
					command.getPrefix() + " &cUsage: /MinimumPrices CheckPrice <ItemID:ItemData>");

		String[] data = args[0].split(":");
		WorldItem worldItem = new WorldItem(Integer.parseInt(data[0]), Byte.parseByte(data[1]));
		for (WorldItem localItem : minimumPrices.keySet()) {
			if (localItem.getId() == worldItem.getId()) {
				if (localItem.getData() == -1 | localItem.getData() == worldItem.getData()) {
					int minimumPrice = minimumPrices.get(localItem);
					return CommandUtil.success(sender, command.getPrefix() + " &aThe minimum price for "
							+ worldItem.getId() + ":" + worldItem.getData() + " is " + minimumPrice + ".");
				}
			}
		}
		return true;
	}

	public String permissionRequired() {
		return "MinimumPrices.CheckPrice";
	}

}
