package com.zodiacmc.ZodiacManager.ChunkManager.Blocks;

public enum WorldBlockType {
	WORLDANCHOR(214, 0, "WorldAnchor", "World Anchor");
	
	private int id, data;
	private String capitalization, readable;
	
	WorldBlockType(int id, int data, String capitalization, String readable) {
		this.id = id;
		this.data = data;
		this.capitalization = capitalization;
		this.readable = readable;
	}
	
	public int getID() {
		return id;
	}
	
	public int getData() {
		return data; 
	}
	
	public String getCapitalization() {
		return capitalization;
	}
	
	public String getReadable() {
		return readable;
	}
}
