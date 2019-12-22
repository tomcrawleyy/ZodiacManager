package com.zodiacmc.ZodiacManager.Economy;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.zodiacmc.ZodiacManager.ZodiacManager;
import com.zodiacmc.ZodiacManager.Users.User;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class EconomyManager {
	
	private static EconomyManager instance;
	private Economy econ;
	private EconomyManager(Plugin p) {
		if (p.getServer().getPluginManager().getPlugin("Vault") == null) {
		      System.out.println("Vault is Null, Economy Features Disabled.");
		    }
		    RegisteredServiceProvider<Economy> rsp = p.getServer().getServicesManager().getRegistration(Economy.class);
		    if (rsp == null) {
			      System.out.println("Vault is Null, Economy Features Disabled.");
		    }
		    econ = (Economy)rsp.getProvider();
	}
	
	public static EconomyManager getInstance() {
		if (instance == null)
			instance = new EconomyManager(ZodiacManager.getInstance());
		return instance;
	}
	
	public EconomyResponse withdraw(User u, Double amount) {
		return econ.withdrawPlayer(u.getName(), amount);
	}
	
	public EconomyResponse deposit(User u, Double amount) {
		return econ.depositPlayer(u.getName(), amount);
	}
	
	

}
