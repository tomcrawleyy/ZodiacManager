package com.zodiacmc.ZodiacManager.Malls.Scheduling;

import java.util.concurrent.TimeUnit;

import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Shop;
import com.zodiacmc.ZodiacManager.Plugins.AutoMalls;
import com.zodiacmc.ZodiacManager.Scheduling.ScheduledTask;
import com.zodiacmc.ZodiacManager.Scheduling.ScheduledTaskException;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;

public class ShopUpdater {
	
	private static ShopUpdater instance;
	private ScheduledTask task;
	private ShopUpdater() {}
	public static ShopUpdater getInstance() {
		if (instance == null)
			instance = new ShopUpdater();
		return instance;
	}
	
	public void start() {

		try {
			task = ScheduledTask.Builder.create("Shop Updater").repeat(5, TimeUnit.MINUTES).delay(1, TimeUnit.SECONDS)
					.execute(task -> {
						String prefix = AutoMalls.getInstance().getBaseCommand().getPrefix();
						ConsoleUtil.sendMessage(prefix + " Updating Shops....");
						for (Mall mall : Mall.getMalls()) {
							for (Shop shop : mall.getShops()) {
								if (shop.getOwner() == null)
									continue;
								shop.setTimeLeft(shop.getTimeLeft() - TimeUnit.MINUTES.toMillis(5), TimeUnit.MILLISECONDS);
								if (shop.getTimeLeft() <= 0) {
									shop.reset();
									shop.saveConfig();
								}
							}
						}
					}).start();
		} catch(ScheduledTaskException e) {
			e.printStackTrace();
		}
	}
	
	public void stop()
	{
		task.cancel();
	}
}
