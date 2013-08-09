package com.forgeessentials.core;

import java.io.File;
import java.util.Arrays;

import com.forgeessentials.core.bootstrap.FEClassLoader;
import com.forgeessentials.core.modulelauncher.ModuleLauncher;
import com.forgeessentials.core.util.SystemChecker;
import com.forgeessentials.core.util.SystemConfig;
import com.forgeessentials.util.OutputHandler;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

/**
 * Main FE class, only triggers module launching. Standalone loading code. DO
 * NOT DO ANY CORE MODULE STUFFS HERE! All other misc features are in CoreModule
 * 
 * @author luacs1998
 * 
 */

public class ForgeEssentials extends DummyModContainer {

	public static ForgeEssentials instance = new ForgeEssentials();

	private ModuleLauncher mdlaunch;
	private OutputHandler out;
	private SystemConfig conf;

	public static File FEDIR = FEClassLoader.FEDIR;

	public ForgeEssentials() {
		super(new ModMetadata());
		/* ModMetadata is the same as mcmod.info */
		ModMetadata myMeta = super.getMetadata();
		myMeta.authorList = Arrays.asList(new String[] { "AbrarSyed",
				"Dries007", "luacs1998" });
		myMeta.description = "";
		myMeta.modId = "ForgeEssentials";
		myMeta.version = "2.0pre1";
		myMeta.name = "Forge Essentials";
		myMeta.url = "";
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}

	@Subscribe
	public void preInit(FMLPreInitializationEvent e) {
		SystemChecker.run();
		out = new OutputHandler(e.getModLog());
		mdlaunch = new ModuleLauncher();
		mdlaunch.preLoad(e);
		conf = new SystemConfig();
	}

	@Subscribe
	public void init(FMLInitializationEvent e) {
		mdlaunch.load(e);
	}

	@Subscribe
	public void postInit(FMLPostInitializationEvent e) {
		mdlaunch.postLoad(e);
	}

	@Subscribe
	public void serverStarting(FMLServerStartingEvent e) {
		mdlaunch.serverStarting(e);
	}

	@Subscribe
	public void serverStarted(FMLServerStartedEvent e) {
		mdlaunch.serverStarted(e);
	}

	@Subscribe
	public void serverStopping(FMLServerStoppingEvent e) {
		mdlaunch.serverStopping(e);
	}

	@Subscribe
	public void serverStopped(FMLServerStoppedEvent e) {
		mdlaunch.serverStopped(e);
	}

}
