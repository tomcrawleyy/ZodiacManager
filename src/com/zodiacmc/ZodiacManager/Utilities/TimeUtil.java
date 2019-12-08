package com.zodiacmc.ZodiacManager.Utilities;

import java.util.concurrent.TimeUnit;

public class TimeUtil {

	public static String getReadableTime(long time, TimeUnit unit, boolean includeZeroes) {
		long localTime = unit.toMillis(time);
		long days = TimeUnit.MILLISECONDS.toDays(localTime);
		long daysInMillis = TimeUnit.DAYS.toMillis(days);
		localTime -= daysInMillis;
		long hours = TimeUnit.MILLISECONDS.toHours(localTime);
		long hoursInMillis = TimeUnit.HOURS.toMillis(hours);
		localTime -= hoursInMillis;
		long minutes = TimeUnit.MILLISECONDS.toMinutes(localTime);
		long minutesInMillis = TimeUnit.MINUTES.toMillis(minutes);
		localTime -= minutesInMillis;
		long seconds = TimeUnit.MILLISECONDS.toSeconds(localTime);
		String daysString = !includeZeroes ? days != 0 ? days + " Days, " : "" : days + " Days, ";
		String hoursString = !includeZeroes ? hours != 0 ? hours + " Hours, " : "" : hours + " Hours, ";
		String minutesString = !includeZeroes ? minutes != 0 ? minutes + " Minutes, " : "" : minutes + " Minutes, ";
		return daysString + hoursString + minutesString + seconds + " Seconds.";
	}
	
}
