package com.zodiacmc.ZodiacManager.Utilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtil {
	public static String toString(Location loc) {
		return "X:" + loc.getBlockX() + ", Y:" + loc.getBlockY() + ", Z:" + loc.getBlockZ();
	}
	
	public static Location fromString(String loc) {
		String[] data = loc.split(",");
		String[] x = data[0].split(":");
		String[] y = data[1].split(":");
		String[] z = data[2].split(":");
		return new Location(Bukkit.getWorld("world"), Integer.parseInt(x[1]), Integer.parseInt(y[1]), Integer.parseInt(z[1]));
	}
}
