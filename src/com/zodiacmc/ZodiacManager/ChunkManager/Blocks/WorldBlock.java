package com.zodiacmc.ZodiacManager.ChunkManager.Blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;

import com.zodiacmc.ZodiacManager.ChunkManager.Configurations.WorldBlockConfig;
import com.zodiacmc.ZodiacManager.Scheduling.ScheduledTask;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Utilities.LocationUtil;

public class WorldBlock {

	private Location location;
	private WorldBlockType type;
	private static Map<WorldBlockType, List<WorldBlock>> loadedInstances = new HashMap<WorldBlockType, List<WorldBlock>>();
	private static Map<User, List<ScheduledTask>> scheduledRemovals = new HashMap<User, List<ScheduledTask>>();
	private static List<WorldBlock> scheduledBlocks = new ArrayList<WorldBlock>();
	private WorldBlockConfig config;
	private User user;

	public WorldBlock(WorldBlockType type, Location location, User user) {
		this.type = type;
		this.location = location;
		this.user = user;
		this.user.getWorldBlocks(type).add(this);
		this.config = WorldBlockConfig.getInstance(type);
		if (config.destroyOnLogout()) {
			if (user.isOnline()) {
				location.getBlock().setTypeId(type.getID());
				if (type.getData() != -1)
					location.getBlock().setData((byte) type.getData());
				getLoadedInstances(type).add(this);
			}
		} else {
			getLoadedInstances(type).add(this);
		}
	}

	public static List<WorldBlock> getLoadedInstances(WorldBlockType type) {
		if (loadedInstances.get(type) == null)
			loadedInstances.put(type, new ArrayList<WorldBlock>());
		return loadedInstances.get(type);
	}

	public void destroy(boolean setType) {
		WorldBlock.getLoadedInstances(type).remove(this);
		if (setType)
			this.location.getBlock().setType(Material.AIR);
		if (WorldBlock.getScheduledBlocks().contains(this)) {
			WorldBlock.getScheduledBlocks().remove(this);
		}

	}

	public void login() {
		this.location.getBlock().setTypeId(type.getID());
		if (type.getData() != -1)
			this.location.getBlock().setData((byte) type.getData());
		getLoadedInstances(type).add(this);
	}

	public String serialize() {
		return user.getName() + "," + LocationUtil.toString(location);
	}

	public User getPlacedBy() {
		return user;
	}

	public static List<WorldBlock> getScheduledBlocks() {
		return scheduledBlocks;
	}

	public static Map<User, List<ScheduledTask>> getScheduledRemovals() {
		return scheduledRemovals;
	}

	public WorldBlockType getType() {
		return type;
	}

	public Location getLocation() {
		return location;
	}

}
