package com.zodiacmc.ZodiacManager.Cuboids;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import com.zodiacmc.ZodiacManager.Utilities.LocationUtil;

public class Cuboid {
	
	private World world;
	private int lowerX;
	private int upperX;
	private int lowerY;
	private int upperY;
	private int lowerZ;
	private int upperZ;
	private boolean ignoreHeight;
	private CuboidType type;
	
	public Cuboid(Location loc1, Location loc2, boolean ignoreHeight, CuboidType type) {
		this.upperX = Math.max(loc1.getBlockX(), loc2.getBlockX());
		this.lowerX = Math.min(loc1.getBlockX(), loc2.getBlockX());
		this.upperY = ignoreHeight ? 256 : Math.max(loc1.getBlockY(), loc2.getBlockY());
		this.lowerY = ignoreHeight ? 0 : Math.min(loc1.getBlockY(), loc2.getBlockY());
		this.upperZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
		this.lowerZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
		this.world = loc1.getWorld();
		this.ignoreHeight = ignoreHeight;
		this.type = type;
	}
	
	public CuboidType getType() {
		return this.type;
	}
	
	public boolean extendsBuildHeight() {
		return ignoreHeight;
	}
	
	public int getUpperX() {
		return this.upperX;
	}
	
	public int getLowerX() {
		return this.lowerX;
	}
	
	public int getUpperY() {
		return this.upperY;
	}
	
	public int getLowerY() {
		return this.lowerY;
	}
	
	public int getUpperZ() {
		return this.upperZ;
	}
	
	public int getLowerZ() {
		return this.lowerZ;
	}
	
	public void clearCuboid() {
		for (int x = this.lowerX; x <= this.upperX; x++) {
			for (int y = this.lowerY; y <= this.upperY; y++) {
				for (int z = this.lowerZ; z <= this.upperZ; z++) {
					Location loc = new Location(this.world, x, y, z);
					loc.getBlock().setType(Material.AIR);
				}
			}
		}
	}
	
	public Location getCenter() {
		int x1 = this.upperX+1;
		int y1 = this.lowerY+1;
		int z1 = this.upperZ+1;
		return new Location(this.world, this.lowerX + ((x1- this.lowerX) / 2.0D), y1, this.lowerZ + ((z1-this.lowerZ) / 2.0D));
	}
	
	public Location getLowerNE() {
		return new Location(this.world, this.lowerX, this.lowerY, this.lowerZ);
	}
	
	public Location getUpperSW() {
		return new Location(this.world, this.upperX, this.upperY, this.upperZ);
	}
	
	public boolean isInCuboid(Location loc) {
		if (this.world.getName() != loc.getWorld().getName())
			return false;
		if (loc.getBlockX() > this.upperX | loc.getBlockX() < this.lowerX)
			return false;
		if (loc.getBlockY() > this.upperY | loc.getBlockY() < this.lowerY)
			return false;
		if (loc.getBlockZ() > this.upperZ | loc.getBlockZ() < this.lowerZ)
			return false;
		return true;
	}
	
	public String serialize() {
		return LocationUtil.toString(getLowerNE()) + "/" + LocationUtil.toString(getUpperSW()) + "/" + this.ignoreHeight;
	}

}
