package com.zodiacmc.ZodiacManager.Malls.Enums;

public enum MallType {
	DONOR("Donor"), DEFAULT("Default");
	
	private String readableName;
	private MallType(String readable) {
		this.readableName = readable;
	}
	public String getReadableName() {
		return readableName;
	}
}


