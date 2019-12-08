package com.zodiacmc.ZodiacManager.AutoRank.Scheduling;

import java.util.concurrent.TimeUnit;

import com.zodiacmc.ZodiacManager.Plugins.AutoRank;
import com.zodiacmc.ZodiacManager.Scheduling.ScheduledTask;
import com.zodiacmc.ZodiacManager.Scheduling.ScheduledTaskException;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;

public class PlaytimeUpdater {
	
	private static PlaytimeUpdater instance;
	private ScheduledTask task;
	
	private PlaytimeUpdater() {}
	
	public static PlaytimeUpdater getInstance() {
		if (instance == null)
			instance = new PlaytimeUpdater();
		return instance;
	}
	
	public void start() {
		try {
			task = ScheduledTask.Builder.create("User Updater").repeat(1, TimeUnit.MINUTES).delay(1, TimeUnit.SECONDS)
					.execute(task -> {
						String prefix = AutoRank.getInstance().getBaseCommand().getPrefix();
						ConsoleUtil.sendMessage(prefix + " Saving Users....");
						for (User user : UserManager.getInstance().getOnlineUsers()) {
							long oldTimePlayed = user.getTimePlayed(false);
							ConsoleUtil.sendMessage(prefix + " Saving User: " + user.getName() + ", Old Playtime: " + oldTimePlayed + ", New Playtime: " + user.getTimePlayed(true));
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
