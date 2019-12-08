package com.zodiacmc.ZodiacManager.Models;

public class WorldItem {
	
	private int id;
	private byte data;
	
	public WorldItem(int id, byte data) {
		this.id = id;
		this.data = data;
	}
	
	public WorldItem(String serialized) {
		String[] data = serialized.split(":");
		this.id = Integer.parseInt(data[0]);
		this.data = Byte.parseByte(data[1]);
	}
	
	public int getId() {
		return id;
	}
	
	public byte getData() {
		return data;
	}
	
	public String serialize() {
		return id + ":" + data;
	}

}
