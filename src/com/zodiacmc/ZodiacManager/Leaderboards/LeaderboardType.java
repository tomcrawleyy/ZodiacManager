package com.zodiacmc.ZodiacManager.Leaderboards;

/**
 * LeaderboardType.java
 * <p>
 * Different types of leaderboards that can exist can be specified in this file.
 * </p>
 * 
 * @author Brian Reich
 * @version 1.0
 */
public enum LeaderboardType {
    /* ---------- Enumerations ---------- */
    AUTO_RANK("oldplaytimes.yml");



    /* ---------- LeaderboardType class ---------- */
    private String name;


    LeaderboardType() {
        
    }

    LeaderboardType(String name) {
        this.name = name;
    }

    public String getName() {
        return ((name == null)? this.name() : this.name);
    }
}
