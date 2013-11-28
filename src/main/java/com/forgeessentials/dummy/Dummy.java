package com.forgeessentials.dummy;

import com.forgeessentials.core.ForgeEssentials;
import com.forgeessentials.core.modules.IFEModule;
import com.forgeessentials.core.modules.ModuleContainer;
import com.forgeessentials.core.modules.events.ModuleDisableEvent;
import com.forgeessentials.core.modules.events.ModuleEnableEvent;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInterModComms;

import java.io.File;
import java.util.List;

import net.minecraft.server.MinecraftServer;

@IFEModule.LoadMe(name = "Dummy")
public class Dummy implements IFEModule
{

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
