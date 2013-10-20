package com.forgeessentials.dummy;

import static com.forgeessentials.core.ForgeEssentials.LOGGER;

import com.forgeessentials.core.modules.IFEModule;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLEvent;
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
    public void fmlEvent(FMLEvent event)
    {
        LOGGER.warn("Event: {}  Side: {}  EffectiveSide: {}", event.getEventType(), FMLCommonHandler.instance().getSide(), FMLCommonHandler.instance().getEffectiveSide());
    }

    @Override
    public void doConfig(Configuration configuration)
    {

    }
}
