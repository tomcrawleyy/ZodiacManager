package com.zodiacmc.ZodiacManager.ChunkManager.Configurations;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.zodiacmc.ZodiacManager.AutoRank.Ranking.Rank;
import com.zodiacmc.ZodiacManager.AutoRank.Ranking.RankManager;
import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlock;
import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlockType;
import com.zodiacmc.ZodiacManager.Configurations.ConfigType;
import com.zodiacmc.ZodiacManager.Configurations.IConfiguration;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;
import com.zodiacmc.ZodiacManager.Utilities.LocationUtil;

public class WorldBlockConfig implements IConfiguration {

	private FileConfiguration config;
	private File file;
	private Boolean destroyOnLogout;
	private static Map<WorldBlockType, WorldBlockConfig> configInstances = new HashMap<WorldBlockType, WorldBlockConfig>();
	private List<WorldBlock> instances;
	private WorldBlockType worldBlockType;

	private WorldBlockConfig(WorldBlockType type) {
		worldBlockType = type;
		instances = new ArrayList<WorldBlock>();
	}

	public static WorldBlockConfig getInstance(WorldBlockType type) {
		if (!configInstances.containsKey(type))
			configInstances.put(type, new WorldBlockConfig(type));
		return configInstances.get(type);
	}
	
	public List<WorldBlock> getInstances(){
		return instances;
	}

	@Override
	public FileConfiguration getConfig() {
		return config;
	}

	@Override
	public File getFile() {
		return file;
	}
	
	public WorldBlock getPlacedAt(Location loc) {
		for (WorldBlock block : instances) {
			Location location = block.getLocation();
			if (loc.getBlockX() == location.getBlockX() && loc.getBlockY() == location.getBlockY()
					&& loc.getBlockZ() == location.getBlockZ()) {
				return block;
			}
		}
		return null;
	}

	@Override
	public void loadConfig(Plugin p) {
		file = new File(p.getDataFolder() + "/ChunkLoader/", worldBlockType.name().toLowerCase() + ".yml");
		config = YamlConfiguration.loadConfiguration(file);
		if (!file.exists()) {
			config.set("destroyOnLogout", true);
			for (Rank rank : RankManager.getInstance().getRanks()) {
				config.set("destroyAfter." + rank.getName(), 0);
				config.set("limiter." + rank.getName(), -1);
			}
			config.set("instances", new ArrayList<String>());
			saveConfig();
		}
		for (String instance : config.getStringList("instances")) {
			String instanceData[] = instance.split(";");
			Location loc = LocationUtil.fromString(instanceData[1]);
			User u = UserManager.getInstance().getUserOnlineOrOffline(instanceData[0]);
			WorldBlock block = new WorldBlock(worldBlockType, loc, u);
			instances.add(block);
		}
	}

	public void addInstance(WorldBlock block) {
		if (block.getType() != worldBlockType) {
			ConsoleUtil.sendMessage("&cError: " + block.getType().name() + " cannot be added to the " + worldBlockType.name() + " configuration!");
			return;
		}
		instances.add(block);
		List<String> localInstances = new ArrayList<String>();
		for (WorldBlock localBlock : instances) {
			localInstances.add(localBlock.getPlacedBy().getName() + ";" + LocationUtil.toString(localBlock.getLocation()));
		}
		config.set("instances", localInstances);
		saveConfig();
	}
	
	public void clearInstances() {
		instances.clear();
		config.set("instances", new ArrayList<String>());
		saveConfig();
	}
	
	public void removeInstance(WorldBlock block) {
		if (block.getType() != worldBlockType) {
			ConsoleUtil.sendMessage("&cError: " + block.getType().name() + " cannot be removed from the " + worldBlockType.name() + "configuration");
			return;
		}
		instances.remove(block);
		block.getPlacedBy().getWorldBlocks(worldBlockType).remove(block);
		block.getPlacedBy().saveData();
	}

	@Override
	public ConfigType getType() {
		return ConfigType.WORLDBLOCK;
	}

	@Override
	public void saveConfig() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean destroyOnLogout() {
		if (destroyOnLogout == null)
			destroyOnLogout = config.getBoolean("destroyOnLogout");
		return destroyOnLogout;
	}
	
	public void setDestroyOnLogout(boolean value) {
		destroyOnLogout = value;
		config.set("destroyOnLogout", value);
		saveConfig();
	}

	public long getRemovalDelay(Rank rank, TimeUnit unit) {
		Long value;
		try {
			value = config.getLong("destroyAfter." + rank.getName());
		} catch (Exception e) {
			value = 0L;
		}
		return unit.convert(value, TimeUnit.MILLISECONDS);
	}
	
	public void setRemovalDelay(Rank rank, long value, TimeUnit unit) {
		config.set("destroyAfter." + rank.getName(), TimeUnit.MILLISECONDS.convert(value, unit));
		saveConfig();
	}

	public int getLimit(Rank rank) {
		return config.getInt("limiter." + rank.getName());
	}

	public void setLimit(Rank rank, int newLimit) {
		config.set("limiter." + rank.getName(), newLimit);
		saveConfig();
	}

}
