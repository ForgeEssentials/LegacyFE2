package com.forgeessentials.core;

import com.forgeessentials.core.modules.ModuleLoader;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class ForgeEssentials
{
    public static final String MODID  = "ForgeEssentials";
    public static final String NAME   = "ForgeEssentials";
    public static final String VERSION = "2.0pre1-SNAPSHOT";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static File FEDIR;
    public static File SETTINGS_DIR;

    static void preInit(FMLPreInitializationEvent event)
    {
        // generatid FEDIR
        ForgeEssentials.FEDIR = new File(event.getModConfigurationDirectory().getParentFile(), ForgeEssentials.NAME);
        ForgeEssentials.FEDIR.mkdirs();

        // generating settingsDir
        ForgeEssentials.SETTINGS_DIR = new File(ForgeEssentials.FEDIR, "settings");
        ForgeEssentials.SETTINGS_DIR.mkdir();

        // logger stuff
        try
        {
            // setup JUL logger
            java.util.logging.Logger tempLogger = java.util.logging.Logger.getLogger(ForgeEssentials.MODID);
            tempLogger.setParent(FMLLog.getLogger());
            tempLogger.setUseParentHandlers(true);

            // add file handler
            FileHandler handler = new FileHandler(FEDIR.getPath() + "/fe.log%g");
            handler.setLevel(Level.FINER);
            SimpleFormatter formatter = new SimpleFormatter();
            handler.setFormatter(new SimpleFormatter());
            tempLogger.addHandler(handler);
        }
        catch (IOException e)
        {
            LOGGER.warn("Log file has failed!", e);
        }
    }

    public static ModuleLoader getModuleLoder()
    {
        return ForgeEssentialsMod.instance.moduleLoader;
    }
}
