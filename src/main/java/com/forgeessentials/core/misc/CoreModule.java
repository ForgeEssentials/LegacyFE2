package com.forgeessentials.core.misc;

import net.minecraftforge.common.MinecraftForge;

import com.forgeessentials.core.modulelauncher.FEComponent;
import com.forgeessentials.core.modulelauncher.FEComponent.FEModuleEventHandler;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@FEComponent(name = "Misc", isCore = false, serverOnly = false, version = "2.0pre1", configClass = CoreMiscConfig.class)
public class CoreModule {

	public static String modlistLocation;

	@FEModuleEventHandler
	public void load(FMLInitializationEvent e) {
		GameRegistry.registerPlayerTracker(new MOTDHandler());
		MinecraftForge.EVENT_BUS.register(new MajoritySleepHandler());
	}

}
