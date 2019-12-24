package com.zodiacmc.ZodiacManager.Serializers;

import org.bukkit.Location;

import com.zodiacmc.ZodiacManager.Cuboids.Cuboid;
import com.zodiacmc.ZodiacManager.Cuboids.CuboidType;
import com.zodiacmc.ZodiacManager.Utilities.LocationUtil;

public class CuboidSerializer {

	public static String serialize(Cuboid c) {
		return LocationUtil.toString(c.getLowerNE()) + "/" + LocationUtil.toString(c.getUpperSW()) + "/"
				+ c.extendsBuildHeight();
	}

	public static Cuboid deserialize(String s, CuboidType type) {
		String[] cuboidParts = s.split("/");
		Location loc1 = LocationUtil.fromString(cuboidParts[0]);
		Location loc2 = LocationUtil.fromString(cuboidParts[1]);
		boolean height = cuboidParts[2].equalsIgnoreCase("true");
		return new Cuboid(loc1, loc2, height, type);
	}

}
