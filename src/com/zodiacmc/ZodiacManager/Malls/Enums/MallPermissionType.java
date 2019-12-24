package com.zodiacmc.ZodiacManager.Malls.Enums;

public enum MallPermissionType {
	BUILD("Build", "Build"), 
	RENEW("Renew", "Renew Shop's Rent"), 
	//REFILL("Refill", "Refill Chests"),
	SETWARP("SetWarp", "Set Warp's Location"), 
	MANAGEMENT("Management", "Trust/Untrust Others To Build"),
	RENEWMANAGEMENT("RenewManagement", "Trust/Untrust Others to Renew Rent"),
	//REFILLMANAGEMENT("RefillManagement", "Trust/Untrust Others to Refill Chests"),
	WARPMANAGEMENT("WarpManagement", "Trust Others to Set Warp's Location"),
	ALL("All", "Trust With Everything");

	private String readableName;
	private String description;

	private MallPermissionType(String readableName, String description) {
		this.readableName = readableName;
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public String getReadableName() {
		return this.readableName;
	}
}
