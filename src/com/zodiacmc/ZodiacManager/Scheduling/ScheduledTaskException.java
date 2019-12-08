package com.zodiacmc.ZodiacManager.Scheduling;

public class ScheduledTaskException extends Exception {
	
	private static final long serialVersionUID = 2349560536538078792L;
	
	public ScheduledTaskException(ScheduledTask task, String s) {
		super(" for Task " + task.getName() + ":" + s);
		task.parseException();
		this.setStackTrace(task.getStackTrace());
	}

}
