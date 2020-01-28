package com.zodiacmc.ZodiacManager.Malls.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zodiacmc.ZodiacManager.AutoRank.Ranking.RankType;
import com.zodiacmc.ZodiacManager.Commands.SubCommand;
import com.zodiacmc.ZodiacManager.Malls.Cuboids.Mall;
import com.zodiacmc.ZodiacManager.Malls.Enums.MallType;
import com.zodiacmc.ZodiacManager.Users.User;
import com.zodiacmc.ZodiacManager.Users.UserManager;

public class WarpMall extends SubCommand {

	public WarpMall() {
		super("WarpMall", true);
	}

	public boolean processCommand(CommandSender sender, String[] args) {
		if (args.length == 0 || (!args[0].equalsIgnoreCase("Donor") && !args[0].equalsIgnoreCase("Default")))
			return this.usage("AutoMalls WarpMall <Default|Donor>");
		Player p = (Player) sender;
		User u = UserManager.getInstance().getOnlineUser(p);
		Mall m = null;
		for (Mall mall : Mall.getMalls()) {
			if (args[0].equalsIgnoreCase(mall.getType().getReadableName())) {
				if (mall.getType() == MallType.DONOR) {
					if (u.getRank().getRankType() == RankType.DEFAULT) {
						return this.error("You do not have the required permissions to enter this mall!");
					}
				}
				m = mall;
				break;
			}
		}
		if (m.getWarp() == null)
			return this.error("This mall does not have a warp set! Please contact a member of staff!");
		p.teleport(m.getWarp());
		return this.success("You have been successfully teleported to the mall!");
	}

}
