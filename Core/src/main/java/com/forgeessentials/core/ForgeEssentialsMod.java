package com.forgeessentials.core;

import com.forgeessentials.core.modules.ModuleLoader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.*;
import net.minecraftforge.common.Configuration;

import java.io.File;

@Mod(modid = ForgeEssentials.MODID, name = ForgeEssentials.NAME)
public class ForgeEssentialsMod
{
    @Mod.EventHandler
    public void handleFMLEvent(FMLPreInitializationEvent event)
    {
        ForgeEssentials.preInit(event);

        CoreConfig.INSTANCE.init(new File(ForgeEssentials.settingsFolder, "ForgeEssentialsCore.cfg"));

        ModuleLoader.init(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLInitializationEvent event)
    {
        ModuleLoader.passEvent(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLPostInitializationEvent event)
    {
        ModuleLoader.passEvent(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLServerAboutToStartEvent event)
    {
        ModuleLoader.passEvent(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLServerStartingEvent event)
    {
        ModuleLoader.passEvent(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLServerStartedEvent event)
    {
        ModuleLoader.passEvent(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLServerStoppingEvent event)
    {
        ModuleLoader.passEvent(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLServerStoppedEvent event)
    {
        ModuleLoader.passEvent(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLInterModComms.IMCEvent event)
    {
        ModuleLoader.passEvent(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLFingerprintViolationEvent event)
    {
        ModuleLoader.passEvent(event);
    }
}
