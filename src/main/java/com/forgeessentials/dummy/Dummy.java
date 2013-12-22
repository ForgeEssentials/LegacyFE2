package com.forgeessentials.dummy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.permissions.api.PermBuilderFactory.PermReg;
import net.minecraftforge.permissions.api.PermissionsManager;
import net.minecraftforge.permissions.api.RegisteredPermValue;

import com.forgeessentials.core.ForgeEssentials;
import com.forgeessentials.core.modules.IFEModule;
import com.forgeessentials.core.modules.ModuleContainer;
import com.forgeessentials.core.modules.events.ModuleDisableEvent;
import com.forgeessentials.core.modules.events.ModuleEnableEvent;

import cpw.mods.fml.common.event.FMLInterModComms;

@IFEModule.LoadMe(name = "Dummy")
public class Dummy implements IFEModule
{
	private static List<PermReg> perms = new ArrayList<PermReg>();
	static{
		perms.add(new PermReg("fe.dummy.test", RegisteredPermValue.TRUE, null));
		perms.add(new PermReg("fe.dummy.commands.testperm", RegisteredPermValue.TRUE, null));
	}

    @Override
    public String getVersion()
    {
        return "0.1";
    }

    @Override
    public void doConfig(File file, boolean reloading)
    {
        ForgeEssentials.LOGGER.info("Dummy doConfig file: {}  reoload: {}", file, reloading);
    }

    @Override
    public void enable(ModuleEnableEvent event)
    {
        ForgeEssentials.LOGGER.info("Dummy enable");
        event.getLogger().info("Logger Test");
        event.registerCommand(new PermissionsTester());
        // PermissionsManager.getPermFactory().registerPermissions(perms);
    }

    @Override
    public void disable(ModuleDisableEvent event)
    {
        ForgeEssentials.LOGGER.info("Dummy disable");
    }

    @Override
    public void receiveInterModMessages(ModuleContainer container, List<FMLInterModComms.IMCMessage> messages)
    {
        ForgeEssentials.LOGGER.info("Dummy Messages");
    }
}
