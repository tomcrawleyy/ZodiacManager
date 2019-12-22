package com.zodiacmc.ZodiacManager.Plugins;

import com.zodiacmc.ZodiacManager.Commands.BaseCommand;
import com.zodiacmc.ZodiacManager.Malls.Commands.AbandonShop;
import com.zodiacmc.ZodiacManager.Malls.Commands.AddShop;
import com.zodiacmc.ZodiacManager.Malls.Commands.CheckTime;
import com.zodiacmc.ZodiacManager.Malls.Commands.DeleteMall;
import com.zodiacmc.ZodiacManager.Malls.Commands.DeleteShop;
import com.zodiacmc.ZodiacManager.Malls.Commands.ExitSetupMode;
import com.zodiacmc.ZodiacManager.Malls.Commands.Home;
import com.zodiacmc.ZodiacManager.Malls.Commands.IgnoreProtection;
import com.zodiacmc.ZodiacManager.Malls.Commands.RedefineMall;
import com.zodiacmc.ZodiacManager.Malls.Commands.RedefineShop;
import com.zodiacmc.ZodiacManager.Malls.Commands.RenewLease;
import com.zodiacmc.ZodiacManager.Malls.Commands.RentShop;
import com.zodiacmc.ZodiacManager.Malls.Commands.SetDailyPrice;
import com.zodiacmc.ZodiacManager.Malls.Commands.SetMall;
import com.zodiacmc.ZodiacManager.Malls.Commands.SetWarp;
import com.zodiacmc.ZodiacManager.Malls.Commands.Trust;
import com.zodiacmc.ZodiacManager.Malls.Commands.Untrust;
import com.zodiacmc.ZodiacManager.Malls.Commands.Warp;

public class AutoMalls implements IPlugin{
	
	private BaseCommand baseCommand;
	private static AutoMalls instance;
	
	public static AutoMalls getInstance() {
		if (instance == null)
			instance = new AutoMalls();
		return instance;
	}
	
	private AutoMalls() {
		baseCommand = new BaseCommand("AutoMalls", "&7(&dZodiac&fMalls&7)");
		baseCommand.instantiateCommand(new AbandonShop());
		baseCommand.instantiateCommand(new AddShop());
		baseCommand.instantiateCommand(new CheckTime());
		baseCommand.instantiateCommand(new DeleteMall());
		baseCommand.instantiateCommand(new DeleteShop());
		baseCommand.instantiateCommand(new ExitSetupMode());
		baseCommand.instantiateCommand(new Home());
		baseCommand.instantiateCommand(new IgnoreProtection());
		baseCommand.instantiateCommand(new RedefineMall());
		baseCommand.instantiateCommand(new RedefineShop());
		baseCommand.instantiateCommand(new RenewLease());
		baseCommand.instantiateCommand(new RentShop());
		baseCommand.instantiateCommand(new SetDailyPrice());
		baseCommand.instantiateCommand(new SetMall());
		baseCommand.instantiateCommand(new SetWarp());
		baseCommand.instantiateCommand(new Trust());
		baseCommand.instantiateCommand(new Untrust());
		baseCommand.instantiateCommand(new Warp());
		
	}
	
	public BaseCommand getBaseCommand() {
		return baseCommand;
	}

}
