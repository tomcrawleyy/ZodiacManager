package com.zodiacmc.ZodiacManager.ChunkManager.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlockType;
import com.zodiacmc.ZodiacManager.ChunkManager.Configurations.WorldBlockConfig;
import com.zodiacmc.ZodiacManager.Commands.SubCommand;

public class SetRemoveAfterLogout extends SubCommand {

	public SetRemoveAfterLogout() {
		super("SetRemoveAfterLogout", false);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		if (((!(sender instanceof Player)) && (args.length != 2)) | (args.length < 1))
			return this.usage("ChunkManager SetRemoveAfterLogout <BlockType> <Boolean>");
		WorldBlockType blockType = null;
		String booleanString = "";
		if (args.length == 1) {
			Player player = (Player) sender;
			ItemStack item = player.getItemInHand();
			for (WorldBlockType type : WorldBlockType.values()) {
				if (item.getTypeId() == type.getID() && item.getData().getData() == (byte) type.getData()) {
					blockType = type;
					break;
				}
			}
			if (blockType == null)
				return this.error("The block " + item.getTypeId() + ":"
						+ item.getData().getData() + " has not been configured in this plugin.");
			booleanString = args[0];
		} else {
			for (WorldBlockType type : WorldBlockType.values()) {
				if (type.name().equalsIgnoreCase(args[0]) || type.getCapitalization().equalsIgnoreCase(args[0])) {
					blockType = type;
					break;
				}
			}
			if (blockType == null)
				return this.error("The block " + args[0]
						+ " has not been configured in this plugin.");
			booleanString = args[1];
		}
		Boolean localBoolean = null;
		if (booleanString.equalsIgnoreCase("false")) {
			localBoolean = false;
		} else if (booleanString.equalsIgnoreCase("true")) {
			localBoolean = true;
		}
		if (localBoolean == null)
			return this.usage("ChunkManager SetRemoveAfterLogout <BlockType> <Boolean>");
		WorldBlockConfig config = WorldBlockConfig.getInstance(blockType);
		config.setDestroyOnLogout(localBoolean);
		if (localBoolean)
			return this.success("BlockType: " + blockType.getReadable()
					+ " will now reappear/remove upon its owner logging in/out respectively.");
		return this.success("BlockType: " + blockType.getReadable()
				+ " will not reappear/remove upon its owner logging in/out.");
	}

}
