package com.forgeessentials.core;

import net.minecraftforge.common.Configuration;

import java.io.File;

public class CoreConfig
{
    private static final String ROOTCAT = "Core";
    public static final CoreConfig INSTANCE = new CoreConfig();
    private Configuration configuration;

    public boolean useOneConfig = false;

    public void init(File file)
    {
        configuration = new Configuration(file);

        useOneConfig = configuration.get(ROOTCAT, "useOneConfig", useOneConfig, "Use this config for all modules.").getBoolean(useOneConfig);

        configuration.save();
    }

    public Configuration getConfiguration()
    {
        return configuration;
    }
}
