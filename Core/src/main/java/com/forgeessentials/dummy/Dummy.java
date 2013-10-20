package com.forgeessentials.dummy;

import com.forgeessentials.core.modules.IFEModule;
import com.forgeessentials.util.FeLog;
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
        FeLog.severe("Event: " + event.getEventType() + " Side: " + FMLCommonHandler.instance().getSide() + " Effective Side: " + FMLCommonHandler.instance().getEffectiveSide());
    }

    @Override
    public void doConfig(Configuration configuration)
    {

    }
}
