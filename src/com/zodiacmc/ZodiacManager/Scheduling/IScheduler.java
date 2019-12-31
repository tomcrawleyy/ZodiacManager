package com.zodiacmc.ZodiacManager.Scheduling;

import com.zodiacmc.ZodiacManager.Plugins.IPlugin;

public interface IScheduler {
	
	public void start(IPlugin plugin);
	public void stop();

}
