package com.forgeessentials.util;

import cpw.mods.fml.common.FMLLog;

import java.util.logging.Level;
import java.util.logging.Logger;

public class FeLog
{
    private static final FeLog INSTANCE = new FeLog();
    private final Logger logger;

    private FeLog()
    {
        logger = Logger.getLogger(Data.MODID);
        logger.setParent(FMLLog.getLogger());
    }

    public static FeLog get()
    {
        return INSTANCE;
    }

    public static void info(Object o)
    {
        INSTANCE.logger.info(o.toString());
    }

    public static void fine(Object o)
    {
        INSTANCE.logger.info(o.toString());
    }

    public static void severe(Object o)
    {
        INSTANCE.logger.severe(o.toString());
    }
}
