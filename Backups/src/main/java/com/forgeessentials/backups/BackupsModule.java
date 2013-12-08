package com.forgeessentials.backups;

import java.io.File;
import java.util.List;

import com.forgeessentials.core.modules.IFEModule;
import com.forgeessentials.core.modules.ModuleContainer;
import com.forgeessentials.core.modules.events.ModuleDisableEvent;
import com.forgeessentials.core.modules.events.ModuleEnableEvent;

import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;

@IFEModule.LoadMe(name = "Backups")
public class BackupsModule implements IFEModule{

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doConfig(File file, boolean reloading) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enable(ModuleEnableEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disable(ModuleDisableEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveInterModMessages(ModuleContainer container,
			List<IMCMessage> messages) {
		// TODO Auto-generated method stub
		
	}

}
