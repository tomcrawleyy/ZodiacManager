package com.zodiacmc.ZodiacManager.Scheduling;

import java.util.ArrayList;
import java.util.List;

public class ScheduledTaskManager {
	
	private List<ScheduledTask> scheduledTasks = new ArrayList<ScheduledTask>();
	private List<ScheduledTask> repeatingTasks = new ArrayList<ScheduledTask>();
	private static ScheduledTaskManager scheduleManager;
	
	private ScheduledTaskManager() { }
	
	public void addScheduler(ScheduledTask task) {
		if (task.getInterval() > 0) {
			repeatingTasks.add(task);
		}
		scheduledTasks.add(task);
	}
	
	public void removeScheduler(ScheduledTask task) {
		scheduledTasks.remove(task);
		repeatingTasks.remove(task);
	}
	
	public List<ScheduledTask> getScheduledTasks() {
		return scheduledTasks;
	}
	
	public List<ScheduledTask> getRepeatingTasks() {
		return repeatingTasks;
	}
	
	public static ScheduledTaskManager getInstance() {
		if (scheduleManager == null)
			scheduleManager = new ScheduledTaskManager();
		return scheduleManager;
	}

}
