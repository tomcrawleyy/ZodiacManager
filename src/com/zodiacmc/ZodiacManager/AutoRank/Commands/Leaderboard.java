package com.zodiacmc.ZodiacManager.AutoRank.Commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.zodiacmc.ZodiacManager.ZodiacManager;
import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Leaderboards.HashMapLeaderboard;
import com.zodiacmc.ZodiacManager.Plugins.AutoRank;
import com.zodiacmc.ZodiacManager.Utilities.StringUtil;
import com.zodiacmc.ZodiacManager.Utilities.TimeUtil;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;


/**
 * Leaderboard subcommand for the AutoRank module of this plugin.
 * @author Brian Reich
 * 
 * @see SubCommand
 * @see AutoRank
 */
public class Leaderboard extends SubCommand {
    private HashMapLeaderboard<String, Long> leaderboard;

    /**
     * Construct an instance of the Leaderboard command.
     */
    public Leaderboard() {
        this(true);
    }

    /**
     * Constructs an instance of the Leaderboard command.
     * @param autoLoad If <code>true</code>, automatically loads from the Players directory, otherwise initializes with an empty leaderboard.
     */
    public Leaderboard(boolean autoLoad) {
        super("Leaderboard", false);
        this.leaderboard = new HashMapLeaderboard<>();

        if (autoLoad) {
            reload();
        }
    }

    @Override
    public boolean processCommand(CommandSender sender, String[] args) {

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("range")) {
                if (args.length == 3) {
                    // Make sure that the user entered valid numbers.
                    int min = 0, max = 0;
                    try { 
                        min = Integer.parseInt(args[1]); 
                        max = Integer.parseInt(args[2]);
                    } catch (NumberFormatException e) {
                        return super.usage("Leaderboard range <start:int> <end:int>.");
                    }

                    // Check if the range is invalid.
                    // Constraints:
                    //    - max > min  
                    //    - min >= 1
                    //    - max <= leaderboard.size()
                    //    - max - min <= 25
                    if (max < min || min < 1 || max > leaderboard.size() || max - min > 25) {
                        // Invalid range.
                        return super.error("Invalid range!");
                    }

                    sender.sendMessage(StringUtil.parseColors(getLeaderboardRangeFormatted(min - 1, max + 1)));

                    return true;
                } else {
                    return super.usage("Leaderboard range <start:int> <end:int>.");
                }
            } else if (args[0].equalsIgnoreCase("page")) {
                if (args.length == 1) {
                        return super.usage("Leaderboard page <num:int>");
                } else if (args.length == 2) {
                    // Make sure the page number given is a valid number. 
                    int page = -1;
                    try {
                        page = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                        return super.usage("Leaderboard page <num:int>");
                    }

                    if (!(page > 0)) {
                        return super.error("Page number must be a positive integer.");
                    }

                    sender.sendMessage(StringUtil.parseColors(getLeaderboardRangeFormatted(page * 10, (page * 10) + 10)));
                    return true;
                } else {
                    // Too many arguments.
                    super.error("Too many arguments!");
                }
            } else {
                // Invalid subcommand.
                return super.error("Invalid subcommand.");
            }
        } else {
            sender.sendMessage(StringUtil.parseColors(getLeaderboardRangeFormatted(0, 10)));

            return true;
        }

        return super.usage("AutoRank Leaderboard");
    }

    /**
     * Get a string with colors and stuff holding leaderboard values to send to player.
     * The returned <code>String</code> will still need to be parsed using StringUtil.parseColours().
     * @param minPos the minimum position in the leaderboard.
     * @param maxPos the maximum position in the leaderboard.
     * @return The <code>String</code> with colors and stuff (unformatted/unparsed).
     */
    String getLeaderboardRangeFormatted(int minPos, int maxPos) {
        StringBuilder s = new StringBuilder();
        s.append("&a-----" + getBaseCommand().getPrefix() + "&a-----&r\n");

        leaderboard.sort((a, b) -> {
            return Long.compare(b.getValue(), a.getValue());
        });

        if (minPos > leaderboard.size())
            return "&cInvalid page/range.";

        if (maxPos > leaderboard.size())
            maxPos = leaderboard.size();
        

        List<Map.Entry<String, Long>> list = new LinkedList<>(leaderboard.entrySet());
        ListIterator<Map.Entry<String, Long>> it = list.listIterator(minPos);
        Map.Entry<String, Long> curEntry = it.next();
        for (int i = minPos; i < maxPos; i++) {
            s.append("&7" + (i + 1) + ". &d" + curEntry.getKey() + ": &a" + TimeUtil.getReadableTime(curEntry.getValue(), TimeUnit.MILLISECONDS, true) + ((i < maxPos - 1)? "\n" : ""));
            
            if (it.hasNext()) 
                curEntry = it.next();
            else
                break;
        }


        return s.toString();
    }

    /**
     * Load users into the leaderboard from the Players directory
     * @return <code>true</code> if successful, otherwise returns <code>false</code>
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

            leaderboard.sort((a, b) -> {
                return Long.compare(b.getValue(), a.getValue());
            });
            return true;
        } catch (IOException e) {
            ZodiacManager.getInstance()
                    .getServer()
                    .getConsoleSender()
                    .sendMessage(
                        StringUtil.parseColours("&cAn error occured when trying to read the files from the Players directory."));
            
            e.printStackTrace();
        }

        return false;
    }   
}