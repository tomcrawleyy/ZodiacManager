package com.zodiacmc.ZodiacManager.MinimumPrices.Configurations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
	private Map<WorldItem, Integer> donorPrices;
	
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
	
	public void addMinimumPrice(WorldItem item, Integer value, Integer donorValue) {
		minimumPrices.put(item, value);
		donorPrices.put(item, donorValue);
	}
	
	public void removeMinimumPrice(WorldItem item) {
		minimumPrices.remove(item);
		donorPrices.remove(item);
	}
	
	public Map<WorldItem, Integer> getMinimumPrices(){
		return minimumPrices;
	}
	
	public Map<WorldItem, Integer> getDonorPrices() {
		return donorPrices;
	}
	
	public void updateConfig() {
		List<String> serializedPrices = new ArrayList<String>();
		List<String> serializedDonorPrices = new ArrayList<String>();
		for (WorldItem item : minimumPrices.keySet()) {
			serializedDonorPrices.add(item.serialize() + "," + donorPrices.get(item));
			serializedPrices.add(item.serialize() + "," + minimumPrices.get(item));
		}
		config.set("donor-prices", serializedDonorPrices);
		config.set("minimum-prices", serializedPrices);
		saveConfig();
	}

	@Override
	public void loadConfig(Plugin p) {
		file = new File(p.getDataFolder() + "/MinimumPrices/", "minimum-prices.yml");
		config = YamlConfiguration.loadConfiguration(file);
		minimumPrices = new HashMap<WorldItem, Integer>();
		donorPrices = new HashMap<WorldItem, Integer>();
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
			for (String minPrice : config.getStringList("donor-prices")) {
				String[] data = minPrice.split(",");
				WorldItem item = new WorldItem(data[0]);
				donorPrices.put(item, Integer.parseInt(data[1]));
				ConsoleUtil.sendMessage("&f(&dMinimum&fPrices) Donator Minimum price for " + data[0] + " has been set to " + Integer.parseInt(data[1]));
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
