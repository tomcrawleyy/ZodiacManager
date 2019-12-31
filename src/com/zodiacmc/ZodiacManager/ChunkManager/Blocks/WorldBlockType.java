package com.zodiacmc.ZodiacManager.ChunkManager.Blocks;

import com.zodiacmc.ZodiacManager.Configurations.ConfigType;

public enum WorldBlockType {
	WORLD_ANCHOR(214, 0, "WorldAnchor", "World Anchor", ConfigType.WORLDBLOCK_WORLD_ANCHOR),
	LOW_VOLTAGE_SOLAR_ARRAY(183,0, "LowVoltageSolarArray", "Low Voltage Solar Array", ConfigType.WORLDBLOCK_LOW_VOLTAGE_SOLAR_ARRAY),
	MEDIUM_VOLTAGE_SOLAR_ARRAY(183,1,"MediumVoltageSolarArray", "Medium Voltage Solar Array", ConfigType.WORLDBLOCK_MEDIUM_VOLTAGE_SOLAR_ARRAY),
	HIGH_VOLTAGE_SOLAR_ARRAY(183,2,"HighVoltageSolarArray", "Medium Voltage Solar Array", ConfigType.WORLDBLOCK_HIGH_VOLTAGE_SOLAR_ARRAY),
	DIMENSIONAL_ANCHOR(4095, 0, "DimensionalAnchor", "Dimensional Anchor", ConfigType.WORLDBLOCK_DIMENSIONAL_ANCHOR),
	TELEPORT_TETHER(179, 0, "TeleportTether", "Teleport Tether", ConfigType.WORLDBLOCK_TELEPORT_TETHER),
	TIMER(138, 0, "Timer", "Timer", ConfigType.WORLDBLOCK_TIMER),
	COMPUTER(207, 0, "Computer", "Computer", ConfigType.WORLDBLOCK_COMPUTER);
	
	
	private int id, data;
	private String capitalization, readable;
	private ConfigType type;
	
	WorldBlockType(int id, int data, String capitalization, String readable, ConfigType type) {
		this.id = id;
		this.data = data;
		this.capitalization = capitalization;
		this.readable = readable;
		this.type = type;
	}
	
	public ConfigType getConfigType() {
		return this.type;
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
