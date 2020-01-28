package com.zodiacmc.ZodiacManager.ChunkManager.Scheduling;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlock;
import com.zodiacmc.ZodiacManager.ChunkManager.Blocks.WorldBlockType;
import com.zodiacmc.ZodiacManager.ChunkManager.Configurations.WorldBlockConfig;
import com.zodiacmc.ZodiacManager.Plugins.ChunkManager;
import com.zodiacmc.ZodiacManager.Plugins.IPlugin;
import com.zodiacmc.ZodiacManager.Scheduling.IScheduler;
import com.zodiacmc.ZodiacManager.Scheduling.ScheduledTask;
import com.zodiacmc.ZodiacManager.Scheduling.ScheduledTaskException;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;
import com.zodiacmc.ZodiacManager.Utilities.LocationUtil;

public class BlockUpdater implements IScheduler {
	
	private static BlockUpdater instance;
	private ScheduledTask task;
	
	private BlockUpdater() {}
	
	public static BlockUpdater getInstance() {
		if (instance == null)
			instance = new BlockUpdater();
		return instance;
	}
	
	public void start(IPlugin plugin) {
		try {
			task = ScheduledTask.Builder.create("Block Updater", plugin).repeat(1, TimeUnit.MINUTES).delay(1, TimeUnit.SECONDS)
					.execute(task -> {
						String prefix = ChunkManager.getInstance().getBaseCommand().getPrefix();
						ConsoleUtil.sendMessage(prefix + " Saving Blocks");
						for (WorldBlockType type : WorldBlockType.values()) {
							WorldBlockConfig config = WorldBlockConfig.getInstance(type);
							List<WorldBlock> instances = config.getInstances();
							if (!instances.isEmpty()) {
								List<String> localInstances = new ArrayList<String>();
								for (WorldBlock localBlock : instances) {
									localInstances.add(localBlock.getPlacedBy().getName() + ";" + LocationUtil.toString(localBlock.getLocation()));
								}
								config.getConfig().set("instances", localInstances);
							} else {
								config.getConfig().set("instances", new ArrayList<String>());
							}
							config.saveConfig();
						}
					}).start();
		}catch (ScheduledTaskException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		task.cancel();
	}

}
