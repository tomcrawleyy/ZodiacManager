package com.zodiacmc.ZodiacManager.AutoRank.Commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import com.zodiacmc.ZodiacManager.ZodiacManager;
import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Leaderboards.HashMapLeaderboard;
import com.zodiacmc.ZodiacManager.Utilities.StringUtil;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;


/**
 * 
 */
public class Leaderboard extends SubCommand {
    private HashMapLeaderboard<String, Long> leaderboard;

    /**
     * Construct an instance of the Leaderboard command.
     */
    public Leaderboard() {
        super("leaderboard", false);
        this.leaderboard = new HashMapLeaderboard<>();

    }

    @Override
    public boolean processCommand(CommandSender sender, String[] args) {
        if (args.length > 0) {
            Player player = (Player) sender;
            if (args[0].equalsIgnoreCase("range")) {
                if (args.length == 3) {
                    int min = Integer.parseInt(args[1]), max = Integer.parseInt(args[2]);

                } else {
                    return this.usage("leaderboard range <start:int> <end:int>.");
                }
            } else if (isInt(args[0])) {

            } else {
                // Show the first page of the users. Each page is 10 users.

            }
        } else {

        }

        return false;
    }

    /**
     * 
     * @param minPos
     * @param maxPos
     * @return
     */
    String getLeaderboardRange(int minPos, int maxPos) {

        return null;
    }

    /**
     * 
     * @param minPos
     * @param maxPos
     * @return
     */
    String getLeaderboardRangeFormatted(int minPos, int maxPos) {
        StringBuilder s = new StringBuilder();

        leaderboard.sort((a, b) -> {
            return Long.compare(b.getValue(), a.getValue());
        });

        Map.Entry<String, Long>[] es = (Map.Entry<String, Long>[]) leaderboard.entrySet().toArray();
        for (int i = 0; i < es.length; i++) {
            s.append("&8" + (i + 1) + ". &d" + es[i].getKey() + ": &7" + es[i].getValue() + ((i < es.length - 1)? "\n" : ""));
        }

        return s.toString();
    }

    /**
     * 
     * @return
     */
    private boolean reload() {
        // Load from Players directory.
        File playerDir = new File(ZodiacManager.getInstance().getDataFolder() + "/Players");
        try {
            Files.find(Paths.get(playerDir.getPath()), Integer.MAX_VALUE, (path, attr) -> !attr.isDirectory())
                    .forEach(path -> {
                        String username = path.getFileName().toString().replaceAll("(\\.yml)", "");

                        YamlConfiguration config = YamlConfiguration.loadConfiguration(path.toFile());
                        Long timePlayed = config.getLong("timePlayed", -1);
                        if (timePlayed != -1)
                            leaderboard.put(username, timePlayed);
                        
                        // leaderboard.
            });

            return true;
        } catch (IOException e) {
            ZodiacManager.getInstance().getServer().getConsoleSender().sendMessage(StringUtil.parseColours("&cAn error occured when trying to read the files from the Players directory."));
            e.printStackTrace();
        }

        return false;
    }    
    

    private boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}