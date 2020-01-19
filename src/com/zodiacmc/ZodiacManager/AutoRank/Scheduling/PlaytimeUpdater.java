package com.zodiacmc.ZodiacManager.AutoRank.Scheduling;

import java.util.concurrent.TimeUnit;

import com.zodiacmc.ZodiacManager.Plugins.AutoRank;
import com.zodiacmc.ZodiacManager.Plugins.IPlugin;
import com.zodiacmc.ZodiacManager.Scheduling.IScheduler;
import com.zodiacmc.ZodiacManager.Scheduling.ScheduledTask;
import com.zodiacmc.ZodiacManager.Scheduling.ScheduledTaskException;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;

public class PlaytimeUpdater implements IScheduler {
	
	private static PlaytimeUpdater instance;
	private ScheduledTask task;
	
	private PlaytimeUpdater() {}
	
	public static PlaytimeUpdater getInstance() {
		if (instance == null)
			instance = new PlaytimeUpdater();
		return instance;
	}
	
	public void start(IPlugin plugin) {
		try {
			task = ScheduledTask.Builder.create("User Updater", plugin).repeat(5, TimeUnit.MINUTES).delay(1, TimeUnit.SECONDS)
					.execute(task -> {
						String prefix = AutoRank.getInstance().getBaseCommand().getPrefix();
						ConsoleUtil.sendMessage(prefix + " Saving Users");
						for (User user : UserManager.getInstance().getOnlineUsers()) {
							user.getTimePlayed(true);
						}
					}).start();
		} catch(ScheduledTaskException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		task.cancel();
	}

}
