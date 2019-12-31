package com.zodiacmc.ZodiacManager.MinimumPrices.Configurations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.zodiacmc.ZodiacManager.Configurations.ConfigType;
import com.zodiacmc.ZodiacManager.Configurations.IConfiguration;
import com.zodiacmc.ZodiacManager.Models.WorldItem;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;

public class MinimumPriceConfig implements IConfiguration {
	
	private FileConfiguration config;
	private File file;
	private static MinimumPriceConfig instance;
	private Map<WorldItem, Integer> minimumPrices;
	
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
	
	public void addMinimumPrice(WorldItem item, Integer value) {
		minimumPrices.put(item, value);
	}
	
	public void removeMinimumPrice(WorldItem item) {
		minimumPrices.remove(item);
	}
	
	public Map<WorldItem, Integer> getMinimumPrices(){
		return minimumPrices;
	}
	
	public void updateMinimumPrices() {
		List<String> serializedPrices = new ArrayList<String>();
		for (WorldItem item : minimumPrices.keySet()) {
			serializedPrices.add(item.serialize() + "," + minimumPrices.get(item));
		}
		config.set("minimum-prices", serializedPrices);
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
			for (String minPrice : config.getStringList("minimum-prices")) {
				String[] data = minPrice.split(",");
				WorldItem item = new WorldItem(data[0]);
				minimumPrices.put(item, Integer.parseInt(data[1]));
				ConsoleUtil.sendMessage("&f(&dMinimum&fPrices) Minimum price for " + data[0] + " has been set to " + Integer.parseInt(data[1]));
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
