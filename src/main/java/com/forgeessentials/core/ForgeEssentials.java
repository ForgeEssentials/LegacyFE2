package com.forgeessentials.core;

import java.io.File;

import com.forgeessentials.core.bootstrap.FETweaker;
import com.forgeessentials.core.modulelauncher.ModuleLauncher;
import com.forgeessentials.util.OutputHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.NetworkMod;

/**
 * Main FE class, only triggers module launching.
 * Standalone loading code. DO NOT DO ANY CORE MODULE STUFFS HERE!
 * All other misc features are in CoreModule
 * @author luacs1998
 *
 */

@Mod(modid = "ForgeEssentials", name = "Forge Essentials", version = "2.0pre1")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class ForgeEssentials {
	
	@Instance("ForgeEssentials")
	public static ForgeEssentials instance;
	
	private ModuleLauncher mdlaunch;
	private OutputHandler out;
	
	public static File FEDIR = FETweaker.getFEDir();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e){
		out = new OutputHandler(e.getModLog());
		mdlaunch = new ModuleLauncher();
		mdlaunch.preLoad(e);
	}
	@EventHandler
	public void init(FMLInitializationEvent e){
		mdlaunch.load(e);
	}
	@EventHandler
	public void postInit(FMLPostInitializationEvent e){
		mdlaunch.postLoad(e);
	}
	@EventHandler
	public void serverStarting(FMLServerStartingEvent e){
		mdlaunch.serverStarting(e);
	}
	@EventHandler
	public void serverStarted(FMLServerStartedEvent e){
		mdlaunch.serverStarted(e);
	}
	@EventHandler
	public void serverStopping(FMLServerStoppingEvent e){
		mdlaunch.serverStopping(e);
	}
	@EventHandler
	public void serverStopped(FMLServerStoppedEvent e){
		mdlaunch.serverStopped(e);
	}

}
