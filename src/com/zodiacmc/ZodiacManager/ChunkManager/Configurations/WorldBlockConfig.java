package com.zodiacmc.ZodiacManager.ChunkManager.Configurations;

import java.io.File;
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
import com.zodiacmc.ZodiacManager.Configurations.FileManager;
import com.zodiacmc.ZodiacManager.Configurations.IConfiguration;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;
import com.zodiacmc.ZodiacManager.Utilities.LocationUtil;

public class WorldBlockConfig implements IConfiguration {

	private FileManager fm = FileManager.getInstance();
	private FileConfiguration config;
	private File file;
	private Boolean destroyOnLogout;
	private Map<Rank, Integer> limits;
	private Map<Rank, Integer> delays;
	private static Map<WorldBlockType, WorldBlockConfig> configInstances = new HashMap<WorldBlockType, WorldBlockConfig>();
	private List<WorldBlock> instances;
	private WorldBlockType worldBlockType;

	private WorldBlockConfig(WorldBlockType type) {
		worldBlockType = type;
		instances = new ArrayList<WorldBlock>();
		limits = new HashMap<Rank, Integer>();
		delays = new HashMap<Rank, Integer>();
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
			destroyOnLogout = true;
			for (Rank rank : RankManager.getInstance().getRanks()) {
				config.set("destroyAfter." + rank.getName(), 0);
				delays.put(rank, 0);
				config.set("limiter." + rank.getName(), -1);
				limits.put(rank, -1);
			}
			config.set("instances", new ArrayList<String>());
			saveConfig();
			return;
		}
		ConsoleUtil.sendMessage("&f(&dChunk&fManager) Loading " + worldBlockType.name().toLowerCase() + ".yml");
		destroyOnLogout = config.getBoolean("destroyOnLogout");
		for (Rank rank : RankManager.getInstance().getRanks()) {
			delays.put(rank, config.getInt("destroyAfter." + rank.getName()));
			limits.put(rank, config.getInt("limiter." + rank.getName()));
		}
		for (String instance : config.getStringList("instances")) {
			String instanceData[] = instance.split(";");
			Location loc = LocationUtil.fromString(instanceData[1]);
			User u = UserManager.getInstance().getUser(instanceData[0]);
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
		ConsoleUtil.sendMessage("addInstance, size: " + instances.size());
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
		if (!instances.isEmpty()) {
			config.set("instances", instances);
		} else {
			config.set("instances", new ArrayList<String>());
		}
		saveConfig();
	}

	@Override
	public ConfigType getType() {
		return worldBlockType.getConfigType();
	}

	@Override
	public void saveConfig() {
		fm.saveConfig(this);
	}

	public boolean destroyOnLogout() {
		return destroyOnLogout;
	}
	
	public void setDestroyOnLogout(boolean value) {
		destroyOnLogout = value;
		config.set("destroyOnLogout", value);
		saveConfig();
	}

	public long getRemovalDelay(Rank rank) {
		return delays.get(rank);
	}
	
	public void setRemovalDelay(Rank rank, Long value, TimeUnit unit) {
		delays.put(rank, value.intValue());
		config.set("destroyAfter." + rank.getName(), TimeUnit.MILLISECONDS.convert(value, unit));
		saveConfig();
	}

	public int getLimit(Rank rank) {
		return limits.get(rank);
	}

	public void setLimit(Rank rank, int newLimit) {
		config.set("limiter." + rank.getName(), newLimit);
		limits.put(rank, newLimit);
		saveConfig();
	}

}
