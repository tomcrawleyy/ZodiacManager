package com.zodiacmc.ZodiacManager.Cuboids;

public enum CuboidType {
	MALL(true), SHOP(false), REDEFINE(false);
	
	private boolean ignoreY;
	private String cuboidName;
	
	private CuboidType(boolean ignoreY) {
		this.ignoreY = ignoreY;
	}
	
	public void setName(String name) {
		this.cuboidName = name;
	}
	
	public String getName() {
		return cuboidName;
	}
	
	public boolean shouldIgnoreY() {
		return this.ignoreY;
	}
}
