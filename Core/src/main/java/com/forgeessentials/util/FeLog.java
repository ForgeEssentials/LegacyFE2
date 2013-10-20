package com.forgeessentials.util;

import com.forgeessentials.core.ForgeEssentials;
import cpw.mods.fml.common.FMLLog;

import java.util.logging.Logger;

public class FeLog
{
    private static final FeLog INSTANCE = new FeLog();
    private final Logger logger;

    private FeLog()
    {
        logger = Logger.getLogger(ForgeEssentials.MODID);
        logger.setParent(FMLLog.getLogger());
    }

    public static FeLog instance()
    {
        return INSTANCE;
    }

    /**
     * @return The underlying Logger instance
     */
    public static FeLog getLogger()
    {
        return INSTANCE;
    }

    public static void info(Object o)
    {
        INSTANCE.logger.info(o.toString());
    }

    public static void fine(Object o)
    {
        INSTANCE.logger.fine(o.toString());
    }

    public static void severe(Object o)
    {
        INSTANCE.logger.severe(o.toString());
    }

    public static void warning(Object o)
    {
        INSTANCE.logger.warning(o.toString());
    }
}
