package com.forgeessentials.core;

import com.forgeessentials.core.modules.ModuleCenter;
import com.forgeessentials.util.Data;
import com.forgeessentials.util.FeLog;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.Configuration;

import java.io.File;

@Mod(modid = Data.MODID, name = Data.NAME)
public class ForgeEssentials
{
    private Configuration configuration;

    @Mod.EventHandler
    public void handleFMLEvent(FMLPreInitializationEvent event)
    {
        Data.init(event);

        configuration = new Configuration(new File(Data.settingsFolder, "Core.cfg"));
        configuration.save();

        ModuleCenter.init(event);
    }
}
