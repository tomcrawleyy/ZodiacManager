package com.zodiacmc.ZodiacManager.Plugins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zodiacmc.ZodiacManager.Commands.BaseCommand;
import com.zodiacmc.ZodiacManager.MinimumPrices.Configurations.MinimumPriceConfig;
import com.zodiacmc.ZodiacManager.Models.WorldItem;

public class MinimumPrices implements IPlugin {

	private BaseCommand baseCommand;
	private static MinimumPrices instance;
	private Map<WorldItem, Integer> minimumPrices;

	public static MinimumPrices getInstance() {
		if (instance == null)
			instance = new MinimumPrices();
		return instance;
	}

	private MinimumPrices() {
		baseCommand = new BaseCommand("MinimumPrices", "&7(&dMinimumPrices&7)");
		minimumPrices = new HashMap<WorldItem, Integer>();
	}
	
	public Map<WorldItem, Integer> getMinimumPrices() {
		return minimumPrices;
	}

	public void addMinimumPrice(WorldItem item, int price) {
		minimumPrices.put(item, price);
	}
	
	public void removeMinimumPrice(WorldItem item) {
		minimumPrices.remove(item);
	}
	
	public void updateMinimumPrices() {
		MinimumPriceConfig config = MinimumPriceConfig.getInstance();
		List<String> serializedPrices = new ArrayList<String>();
		for (WorldItem item : minimumPrices.keySet()) {
			serializedPrices.add(item.serialize() + "," + minimumPrices.get(item));
		}
		config.getConfig().set("minimum-prices", serializedPrices);
	}

	@Override
	public BaseCommand getBaseCommand() {
		return baseCommand;
	}

}
