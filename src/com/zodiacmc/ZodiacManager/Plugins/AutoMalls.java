package com.zodiacmc.ZodiacManager.Plugins;

import org.bukkit.Bukkit;

import com.zodiacmc.ZodiacManager.ZodiacManager;
import com.zodiacmc.ZodiacManager.Commands.BaseCommand;
import com.zodiacmc.ZodiacManager.Configurations.FileManager;
import com.zodiacmc.ZodiacManager.Listeners.PlayerJoinListener;
import com.zodiacmc.ZodiacManager.Malls.Commands.AbandonShop;
import com.zodiacmc.ZodiacManager.Malls.Commands.AddShop;
import com.zodiacmc.ZodiacManager.Malls.Commands.CheckPermissions;
import com.zodiacmc.ZodiacManager.Malls.Commands.CheckTime;
import com.zodiacmc.ZodiacManager.Malls.Commands.DeleteShops;
import com.zodiacmc.ZodiacManager.Malls.Commands.ExitSetupMode;
import com.zodiacmc.ZodiacManager.Malls.Commands.Home;
import com.zodiacmc.ZodiacManager.Malls.Commands.IgnoreProtection;
import com.zodiacmc.ZodiacManager.Malls.Commands.RedefineMall;
import com.zodiacmc.ZodiacManager.Malls.Commands.RedefineShop;
import com.zodiacmc.ZodiacManager.Malls.Commands.RenewLease;
import com.zodiacmc.ZodiacManager.Malls.Commands.RentShop;
import com.zodiacmc.ZodiacManager.Malls.Commands.SetDailyPrice;
import com.zodiacmc.ZodiacManager.Malls.Commands.SetMall;
import com.zodiacmc.ZodiacManager.Malls.Commands.SetMallWarp;
import com.zodiacmc.ZodiacManager.Malls.Commands.SetTimeLeft;
import com.zodiacmc.ZodiacManager.Malls.Commands.SetWarp;
import com.zodiacmc.ZodiacManager.Malls.Commands.Trust;
import com.zodiacmc.ZodiacManager.Malls.Commands.Untrust;
import com.zodiacmc.ZodiacManager.Malls.Commands.Warp;
import com.zodiacmc.ZodiacManager.Malls.Commands.WarpMall;
import com.zodiacmc.ZodiacManager.Malls.Configurations.MallConfig;
import com.zodiacmc.ZodiacManager.Malls.Scheduling.ShopUpdater;
import com.zodiacmc.ZodiacManager.Utilities.ConsoleUtil;

public class AutoMalls implements IPlugin{
	
	private BaseCommand baseCommand;
	private static AutoMalls instance;
	private static FileManager fm = FileManager.getInstance();
	
	public static AutoMalls getInstance() {
		if (instance == null)
			instance = new AutoMalls();
		return instance;
	}
	
	private AutoMalls() {
		ConsoleUtil.loadupMessage("&d+=====================================+");
		ConsoleUtil.loadupMessage("&d|     &aZodiac&c\\<({~v3.0~})>/&aManager     &d|");
		ConsoleUtil.loadupMessage("&d|               Auto &fMalls            &d|");
		ConsoleUtil.loadupMessage("&d+=====================================+");
		fm.loadFile(MallConfig.getInstance());
		Bukkit.getPluginManager().registerEvents(com.zodiacmc.ZodiacManager.Cuboids.CuboidFactoryManager.getInstance(), ZodiacManager.getInstance());
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.Malls.Events.BlockBreak(), ZodiacManager.getInstance());
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.Malls.Events.BlockPlace(), ZodiacManager.getInstance());
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.Malls.Events.CreatureSpawn(), ZodiacManager.getInstance());
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.Malls.Events.PlayerMove(), ZodiacManager.getInstance());
		Bukkit.getPluginManager().registerEvents(new com.zodiacmc.ZodiacManager.Malls.Events.PlayerTeleport(), ZodiacManager.getInstance());
		PlayerJoinListener.getInstance().addListener(new com.zodiacmc.ZodiacManager.Malls.Events.PlayerJoin());
		baseCommand = new BaseCommand("AutoMalls", "&7(&dAuto&fMalls&7)");
		baseCommand.instantiateCommand(new AbandonShop());
		baseCommand.instantiateCommand(new AddShop());
		baseCommand.instantiateCommand(new CheckPermissions());
		baseCommand.instantiateCommand(new CheckTime());
		baseCommand.instantiateCommand(new DeleteShops());
		baseCommand.instantiateCommand(new ExitSetupMode());
		baseCommand.instantiateCommand(new Home());
		baseCommand.instantiateCommand(new IgnoreProtection());
		baseCommand.instantiateCommand(new RedefineMall());
		baseCommand.instantiateCommand(new RedefineShop());
		baseCommand.instantiateCommand(new RenewLease());
		baseCommand.instantiateCommand(new RentShop());
		baseCommand.instantiateCommand(new SetDailyPrice());
		baseCommand.instantiateCommand(new SetMall());
		baseCommand.instantiateCommand(new SetMallWarp());
		baseCommand.instantiateCommand(new SetTimeLeft());
		baseCommand.instantiateCommand(new SetWarp());
		baseCommand.instantiateCommand(new Trust());
		baseCommand.instantiateCommand(new Untrust());
		baseCommand.instantiateCommand(new Warp());
		baseCommand.instantiateCommand(new WarpMall());
		ShopUpdater.getInstance().start(this);
	}
	
	public BaseCommand getBaseCommand() {
		return baseCommand;
	}

}
