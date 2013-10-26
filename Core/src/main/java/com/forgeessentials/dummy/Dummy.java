package com.forgeessentials.dummy;

import static com.forgeessentials.core.ForgeEssentials.LOGGER;

import com.forgeessentials.core.ForgeEssentials;
import com.forgeessentials.core.modules.FMLevents.IPreInit;
import com.forgeessentials.core.modules.IFEModule;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.Configuration;

@IFEModule.LoadMe(name = "Dummy")
public class Dummy implements IFEModule
{

    @Override
    public String getVersion()
    {
        return "0.1";
    }

    @Override
    public void doConfig(Configuration configuration)
    {
        ForgeEssentials.LOGGER.info("Dummy doConfig");
    }

    @Override
    public void enable(MinecraftServer server)
    {
        ForgeEssentials.LOGGER.info("Dummy enable");
    }

    @Override
    public void disable()
    {
        ForgeEssentials.LOGGER.info("Dummy disable");
    }

    @Override
    public void reload()
    {
        ForgeEssentials.LOGGER.info("Dummy reload");
    }
}
