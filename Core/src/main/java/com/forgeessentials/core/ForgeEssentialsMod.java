package com.forgeessentials.core;

import com.forgeessentials.core.modules.FMLevents.FMLEventHandler;
import com.forgeessentials.core.modules.ModuleLoader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.*;

import java.io.File;

@Mod(modid = ForgeEssentials.MODID, name = ForgeEssentials.NAME)
public class ForgeEssentialsMod
{
    @Mod.EventHandler
    public void handleFMLEvent(FMLPreInitializationEvent event)
    {
        ForgeEssentials.preInit(event);

        CoreConfig.INSTANCE.init(new File(ForgeEssentials.SETTINGS_DIR, "ForgeEssentialsCore.cfg"));

        ModuleLoader.init(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLInitializationEvent event)
    {
        FMLEventHandler.passEvent(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLPostInitializationEvent event)
    {
        FMLEventHandler.passEvent(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLServerAboutToStartEvent event)
    {
        FMLEventHandler.passEvent(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLServerStartingEvent event)
    {
        FMLEventHandler.passEvent(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLServerStartedEvent event)
    {
        FMLEventHandler.passEvent(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLServerStoppingEvent event)
    {
        FMLEventHandler.passEvent(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLServerStoppedEvent event)
    {
        FMLEventHandler.passEvent(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLInterModComms.IMCEvent event)
    {
        FMLEventHandler.passEvent(event);
    }
}
