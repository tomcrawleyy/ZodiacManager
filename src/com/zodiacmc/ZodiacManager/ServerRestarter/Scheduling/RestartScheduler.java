package com.zodiacmc.ZodiacManager.ServerRestarter.Scheduling;

import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;

import com.zodiacmc.ZodiacManager.Configurations.ConfigType;
import com.zodiacmc.ZodiacManager.Configurations.FileManager;
import com.zodiacmc.ZodiacManager.Scheduling.ScheduledTask;
import com.zodiacmc.ZodiacManager.Scheduling.ScheduledTaskException;
import com.zodiacmc.ZodiacManager.ServerRestarter.Configurations.RestartConfig;
import com.zodiacmc.ZodiacManager.Utilities.BroadcastUtil;
import com.zodiacmc.ZodiacManager.Utilities.TimeUtil;

public class RestartScheduler {
	
	private static RestartScheduler instance;
	private ScheduledTask task;
	private long timeUntilRestart;
	private FileManager fm = FileManager.getInstance();
	private RestartConfig config;
	
	private RestartScheduler() {
		config = (RestartConfig)fm.getConfig(ConfigType.RESTART);
		timeUntilRestart = TimeUnit.HOURS.toSeconds(config.getConfig().getInt("RestartIntervalInHours"));
	}
	
	public static RestartScheduler getInstance() {
		if (instance == null)
			instance = new RestartScheduler();
		return instance;
	}
	
	public String getTimeLeft() {
		return TimeUtil.getReadableTime(timeUntilRestart, TimeUnit.SECONDS, false);
	}
	
	public void start() {
		try {
			task = ScheduledTask.Builder
					.create("Server Restarter")
					.repeat(1, TimeUnit.SECONDS)
					.delay(1, TimeUnit.SECONDS)
					.execute(task -> {
						timeUntilRestart -= 1;
						//DebugUtil.debugMessage("Time until restart: " + getTimeLeft());
						if (timeUntilRestart == TimeUnit.HOURS.toSeconds(2)) {
							BroadcastUtil.globalBroadcast("&7(&dZodiac&fRestarter&7) &aServer restarting in 2 hours!");
						} else if (timeUntilRestart == TimeUnit.HOURS.toSeconds(1)) {
							BroadcastUtil.globalBroadcast("&7(&dZodiac&fRestarter&7) &aServer restarting in 1 hours!");
						} else if (timeUntilRestart == TimeUnit.MINUTES.toSeconds(30)) {
							BroadcastUtil.globalBroadcast("&7(&dZodiac&fRestarter&7) &aServer restarting in 30 minutes!");
						} else if (timeUntilRestart == TimeUnit.MINUTES.toSeconds(15)) {
							BroadcastUtil.globalBroadcast("&7(&dZodiac&fRestarter&7) &aServer restarting in 15 minutes!");
						} else if (timeUntilRestart == TimeUnit.MINUTES.toSeconds(10)) {
							BroadcastUtil.globalBroadcast("&7(&dZodiac&fRestarter&7) &aServer restarting in 10 minutes!");
						} else if (timeUntilRestart == TimeUnit.MINUTES.toSeconds(5)) {
							BroadcastUtil.globalBroadcast("&7(&dZodiac&fRestarter&7) &aServer restarting in 5 minutes!");
						} else if (timeUntilRestart == TimeUnit.MINUTES.toSeconds(1)) {
							BroadcastUtil.globalBroadcast("&7(&dZodiac&fRestarter&7) &aServer restarting in 1 minute!");
						} else if (timeUntilRestart == 30) {
							BroadcastUtil.globalBroadcast("&7(&dZodiac&fRestarter&7) &aServer restarting in 30 seconds!");
						} else if (timeUntilRestart == 15) {
							BroadcastUtil.globalBroadcast("&7(&dZodiac&fRestarter&7) &aServer restarting in 15 seconds!");
						} else if (timeUntilRestart == 10) {
							BroadcastUtil.globalBroadcast("&7(&dZodiac&fRestarter&7) &aServer restarting in 10 seconds!");
						} else if (timeUntilRestart == 5) {
							BroadcastUtil.globalBroadcast("&7(&dZodiac&fRestarter&7) &aServer restarting in 5 seconds!");
						} else if (timeUntilRestart == 4) {
							BroadcastUtil.globalBroadcast("&7(&dZodiac&fRestarter&7) &aServer restarting in 4 seconds!");
						} else if (timeUntilRestart == 3) {
							BroadcastUtil.globalBroadcast("&7(&dZodiac&fRestarter&7) &aServer restarting in 3 seconds!");
						} else if (timeUntilRestart == 2) {
							BroadcastUtil.globalBroadcast("&7(&dZodiac&fRestarter&7) &aServer restarting in 2 seconds!");
						} else if (timeUntilRestart == 1) {
							BroadcastUtil.globalBroadcast("&7(&dZodiac&fRestarter&7) &aServer restarting in 1 seconds!");
						} else if (timeUntilRestart == 0) {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
						}
					}).start();
		} catch (ScheduledTaskException e) {
			e.printStackTrace();
		}
	}
	
	public void forceRestart() {
		timeUntilRestart = 11;
	}
	
	public void stop() {
		task.cancel();
	}

}
