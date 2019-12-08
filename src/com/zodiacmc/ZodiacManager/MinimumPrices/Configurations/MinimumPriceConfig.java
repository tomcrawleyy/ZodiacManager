package com.zodiacmc.ZodiacManager.MinimumPrices.Configurations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.zodiacmc.ZodiacManager.Configurations.ConfigType;
import com.zodiacmc.ZodiacManager.Configurations.IConfiguration;
import com.zodiacmc.ZodiacManager.Models.WorldItem;
import com.zodiacmc.ZodiacManager.Plugins.MinimumPrices;

public class MinimumPriceConfig implements IConfiguration {
	
	private FileConfiguration config;
	private File file;
	private static MinimumPriceConfig instance;
	
	private MinimumPriceConfig() {
	}
	
	public static MinimumPriceConfig getInstance() {
		if (instance == null)
			instance = new MinimumPriceConfig();
		return instance;
	}

	@Override
	public FileConfiguration getConfig() {
		return config;
	}

	@Override
	public File getFile() {
		return file;
	}

	@Override
	public void loadConfig(Plugin p) {
		file = new File(p.getDataFolder() + "/MinimumPrices/", "minimum-prices.yml");
		config = YamlConfiguration.loadConfiguration(file);
		if (!file.exists()) {
			List<String> minPrices = new ArrayList<String>();
			config.set("minimum-prices", minPrices);
			saveConfig();
		} else {
			MinimumPrices plugin = MinimumPrices.getInstance();
			for (String minPrice : config.getStringList("minimum-prices")) {
				String[] data = minPrice.split(",");
				WorldItem item = new WorldItem(data[0]);
				plugin.addMinimumPrice(item, Integer.parseInt(data[1]));
			}
		}
	}

	@Override
	public ConfigType getType() {
		return ConfigType.MINIMUMPRICES;
	}

	@Override
	public void saveConfig() {
		try {
			config.save(file);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

}
