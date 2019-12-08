package com.zodiacmc.ZodiacManager.Scheduling;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;

public abstract class ScheduledTask implements Runnable {

	protected Thread thread;
	protected StackTraceElement[] creationStackTrace;
	protected long delay = 0, interval = 0;
	protected Runnable runnable;
	protected Consumer<ScheduledTask> task;
	protected boolean isCancelled = false, started = false, exceptionThrown = false;
	protected int taskID;
	protected String name;
	private static int ID = 0;

	public ScheduledTask() {
		this.creationStackTrace = Thread.currentThread().getStackTrace();
		this.thread = new Thread(this);
		// ScheduledTaskManager.getInstance().addScheduler(this);
	}

	public static class Builder extends ScheduledTask {

		public Builder(String s) {
			this.taskID = ++ID;
			this.name = s;
			ConsoleUtil.sendMessage("Scheduled Task \"" + this.name + "\" With the ID of " + this.taskID + " created");
		}

		public static ScheduledTask.Builder create(String name) {
			return new Builder(name);
		}

		public ScheduledTask.Builder delay(int millis) throws ScheduledTaskException {
			if (started && !exceptionThrown)
				throw new ScheduledTaskException(this, "Scheduler has already started! Create a new Builder instance!");
			if (isCancelled)
				return this;
			ConsoleUtil.sendMessage("Scheduled Task Delay Set To: " + millis + " milliseconds.");
			this.delay = millis;
			return this;
		}

		public ScheduledTask.Builder delay(int delay, TimeUnit unit) throws ScheduledTaskException {
			if (started && !exceptionThrown)
				throw new ScheduledTaskException(this, "Scheduler has already started! Create a new Builder instance!");
			if (isCancelled)
				return this;
			ConsoleUtil.sendMessage("Scheduled Task Delay Set To: " + delay + " " + unit.name().toLowerCase() + ".");
			this.delay = unit.toMillis(delay);
			return this;
		}

		public ScheduledTask.Builder repeat(int interval) throws ScheduledTaskException {
			if (started && !exceptionThrown)
				throw new ScheduledTaskException(this, "Scheduler has already started! Create a new Builder instance!");
			if (isCancelled)
				return this;
			this.interval = interval;
			ConsoleUtil.sendMessage("Scheduled Task Interval Set To: " + interval);
			return this;
		}

		public ScheduledTask.Builder repeat(int interval, TimeUnit unit) throws ScheduledTaskException {
			if (started && !exceptionThrown)
				throw new ScheduledTaskException(this, "Scheduler has already started! Create a new Builder instance!");
			if (isCancelled)
				return this;
			this.interval = unit.toMillis(interval);
			ConsoleUtil
					.sendMessage("Scheduled Task Interval Set To: " + interval + " " + unit.name().toLowerCase() + ".");
			return this;
		}

		public ScheduledTask.Builder execute(Consumer<ScheduledTask> task) throws ScheduledTaskException {
			if (started && !exceptionThrown)
				throw new ScheduledTaskException(this, "Scheduler has already started! Create a new Builder instance!");
			if (isCancelled)
				return this;
			ConsoleUtil.sendMessage("Scheduled Task Consumer Setup");
			this.task = task;
			return this;
		}

		public ScheduledTask.Builder start() throws ScheduledTaskException {
			if (started && !exceptionThrown)
				throw new ScheduledTaskException(this, "Scheduler has already started! Create a new Builder instance!");
			if (isCancelled)
				return this;
			ConsoleUtil.sendMessage("Scheduled Task Running....");
			ScheduledTaskManager.getInstance().addScheduler(this);
			thread.start();
			return this;
		}

	}

	@Override
	public void run() {
		try {
			started = true;
			String s = interval == 0 && delay == 0 ? "Interval & Delay is null for Scheduler with creation stack trace:" : runnable == null && task == null ? "Runnable & Task is null for Scheduler with creation stack trace:" : "";
			if (s != "")
				new ScheduledTaskException(this, s);
			if (delay != 0)
				Thread.sleep(delay);
			if (interval != 0) {
				while (!isCancelled)
					executeTask();
				ScheduledTaskManager.getInstance().removeScheduler(this);
				thread.join();
				return;
			}
			if (!isCancelled)
				executeTask();
			ScheduledTaskManager.getInstance().removeScheduler(this);
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void executeTask() {
		if (runnable != null) {
			runnable.run();
		} else {
			task.accept(this);
		}
		if (interval != 0) {
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public long getDelay(TimeUnit unit) {
		return TimeUnit.MILLISECONDS.convert(delay, unit);
	}

	public long getInterval(TimeUnit unit) {
		return TimeUnit.MILLISECONDS.convert(delay, unit);
	}

	public long getDelay() {
		return delay;
	}

	public long getInterval() {
		return interval;
	}

	public Consumer<ScheduledTask> getConsumer() {
		return task;
	}

	public Runnable getRunnable() {
		return runnable;
	}

	public void cancel() {
		this.isCancelled = true;
	}

	public String getName() {
		return name;
	}

	public void parseException() {
		cancel();
		exceptionThrown = true;
	}

	public StackTraceElement[] getStackTrace() {
		return creationStackTrace;
	}

}
