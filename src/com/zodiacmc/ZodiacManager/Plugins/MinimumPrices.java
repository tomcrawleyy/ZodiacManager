package com.zodiacmc.ZodiacManager.Plugins;

import org.bukkit.Bukkit;

import com.zodiacmc.ZodiacManager.ZodiacManager;
import com.zodiacmc.ZodiacManager.Commands.BaseCommand;
import com.zodiacmc.ZodiacManager.Configurations.FileManager;
import com.zodiacmc.ZodiacManager.MinimumPrices.Configurations.MinimumPriceConfig;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;

public class MinimumPrices implements IPlugin {

	private BaseCommand baseCommand;
	private static MinimumPrices instance;
	private static FileManager fm = FileManager.getInstance();

	public static MinimumPrices getInstance() {
		if (instance == null)
			instance = new MinimumPrices();
		return instance;
	}

	private MinimumPrices() {
		ConsoleUtil.loadupMessage("&d+=====================================+");
		ConsoleUtil.loadupMessage("&d|     &aZodiac&c\\<({~v3.0~})>/&aManager     &d|");
		ConsoleUtil.loadupMessage("&d|            Minimum &fPrices           &d|");
		ConsoleUtil.loadupMessage("&d+=====================================+");
		fm.loadFile(MinimumPriceConfig.getInstance());
		baseCommand = new BaseCommand("MinimumPrices", "&7(&dMinimumPrices&7)");
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.MinimumPrices.Events.BlockBreak(), ZodiacManager.getInstance());
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.MinimumPrices.Events.BlockPlace(), ZodiacManager.getInstance());
	}

	@Override
	public BaseCommand getBaseCommand() {
		return baseCommand;
	}

}
