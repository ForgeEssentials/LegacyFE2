package com.forgeessentials.core.misc;

import java.io.File;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import com.forgeessentials.core.ForgeEssentials;
import com.forgeessentials.core.modulelauncher.BaseConfig;
import com.forgeessentials.util.OutputHandler;

public class CoreMiscConfig extends BaseConfig {
	
	private Configuration config;
	
	public CoreMiscConfig(File file) {
		super(file);
		config = new Configuration(file);
		load();
	}

	@Override
	public void save() {
		Property prop = config.get("Misc", "modlistLocation", "modlist.txt");
		prop.comment = "Specify the file where the modlist will be written to. This path is relative to the ForgeEssentials folder.";
		prop.set(CoreModule.modlistLocation);

		prop = config.get("Misc", "MajoritySleep", true);
		prop.comment = "If more than 50% of players sleep, make it day.";
		prop.set(MajoritySleepHandler.MajoritySleep);
		
		prop = config.get("Misc", "UseMOTD", true);
		prop.comment = "Send players the MOTD when they log in";
		prop.set(MOTDHandler.showMOTD);

	}

	@Override
	public void load() {

		config.addCustomCategoryComment("Misc", "Configure miscellaneous features of ForgeEssentials Core.");

		Property prop = config.get("Misc", "modlistLocation", "modlist.txt");
		prop.comment = "Specify the file where the modlist will be written to. This path is relative to the ForgeEssentials folder.";
		CoreModule.modlistLocation = prop.getString();

		prop = config.get("Misc", "MajoritySleep", true);
		prop.comment = "If more than 50% of players sleep, make it day.";
		MajoritySleepHandler.MajoritySleep = prop.getBoolean(true);
		
		prop = config.get("Misc", "UseMOTD", true);
		prop.comment = "Send players the MOTD when they log in";
		MOTDHandler.showMOTD = prop.getBoolean(true);

		config.save();
	}

}
