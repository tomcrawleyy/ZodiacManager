package com.zodiacmc.ZodiacManager.Malls.Enums;

public enum MallPermissionType {
	BUILD("Build", "Build"), RENEW("Renew", "Renew Shop's Rent"), REFILL("Refill", "Refill Chests"),
	SETWARP("SetWarp", "Set Warp's Location"), TRUSTOTHERS("TrustOthers", "Trust Others in Shop"),
	TRUSTOTHERSRENEW("TrustOthersRenew", "Trust Others to Renew Rent"),
	TRUSTOTHERSREFILL("TrustOthersRefill", "Trust Others to Refill Chests"),
	TRUSTOTHERSSETWARP("TrustOthersSetWarp", "Trust Others to Set Warp's Location"),
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
