package com.forgeessentials.core;

import com.forgeessentials.core.modules.ModuleLoader;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;

public class ForgeEssentials
{
    public static final String MODID  = "ForgeEssentials";
    public static final String NAME   = "ForgeEssentials";
    public static final Logger LOGGER = getLogger();
    public static File FEDIR;
    public static File settingsFolder;

    static void preInit(FMLPreInitializationEvent event)
    {
        ForgeEssentials.FEDIR = new File(event.getModConfigurationDirectory().getParentFile(), ForgeEssentials.NAME);
        ForgeEssentials.FEDIR.mkdirs();

        ForgeEssentials.settingsFolder = new File(ForgeEssentials.FEDIR, "settings");
        ForgeEssentials.settingsFolder.mkdir();

        CoreConfig.INSTANCE.init(new File(ForgeEssentials.settingsFolder, "ForgeEssentialsCore.cfg"));

        ModuleLoader.init(event);
    }

    private static Logger getLogger()
    {
        // setup JUL logger
        java.util.logging.Logger tempLogger = java.util.logging.Logger.getLogger(ForgeEssentials.MODID);
        tempLogger.setParent(FMLLog.getLogger());
        tempLogger.setUseParentHandlers(true);

        try
        {
            tempLogger.addHandler(new FileHandler(new File(FEDIR, "fe.log%g").getPath()));
        }
        catch (IOException e)
        {
            LOGGER.warn("Log file has failed!", e);
        }

        return LoggerFactory.getLogger(MODID);
    }
}
