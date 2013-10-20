package com.forgeessentials.core;

import com.forgeessentials.core.modules.ModuleLoader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.Configuration;

import java.io.File;

/**
 * The actual @mod class of ForgeEssentials
 */
@Mod(modid = ForgeEssentials.MODID, name = ForgeEssentials.NAME)
public class ForgeEssentialsMod
{
    private Configuration configuration;

    @Mod.EventHandler
    public void handleFMLEvent(FMLPreInitializationEvent event)
    {
        ForgeEssentials.init(event);

        configuration = new Configuration(new File(ForgeEssentials.settingsFolder, "Core.cfg"));
        configuration.save();

        ModuleLoader.init(event);
    }
}
