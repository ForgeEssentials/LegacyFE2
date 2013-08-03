package com.forgeessentials.core.misc;

import com.forgeessentials.core.modulelauncher.FEComponent;
import com.forgeessentials.core.modulelauncher.FEComponent.FEModuleEventHandler;
import com.forgeessentials.util.OutputHandler;

import cpw.mods.fml.common.event.FMLInitializationEvent;

@FEComponent(name = "Core", isCore = false, serverOnly = false, version = "2.0pre1")
public class CoreModule {
	
	@FEModuleEventHandler
	public void load(FMLInitializationEvent e){
		OutputHandler.felog.info("Test!");
	}

}
