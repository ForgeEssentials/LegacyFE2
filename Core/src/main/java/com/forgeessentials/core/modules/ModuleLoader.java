package com.forgeessentials.core.modules;

import com.forgeessentials.core.CoreConfig;
import com.forgeessentials.core.ForgeEssentials;
import com.forgeessentials.util.FeLog;
import cpw.mods.fml.common.discovery.ASMDataTable;
import cpw.mods.fml.common.event.FMLEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.Configuration;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

public class ModuleLoader
{
    private static final HashSet<String>                  LOADED_MODULES = new HashSet<String>();
    private static final HashMap<String, ModuleContainer> MODULES_MAP    = new HashMap<String, ModuleContainer>();

    /**
     * Gets called to load modules and do config and pass on the FMLPreInitializationEvent.
     * @param event used for its ASM data
     */
    public static void init(FMLPreInitializationEvent event)
    {
        FeLog.info("Discovering all modules...");
        String description = event.getModMetadata().description;
        description += "\n" + EnumChatFormatting.UNDERLINE + "Modules:" + EnumChatFormatting.RESET + "\n";

        for (ASMDataTable.ASMData data : event.getAsmData().getAll(IFEModule.LoadMe.class.getName()))
        {
            try
            {
                ModuleContainer container = new ModuleContainer(data);
                description += "\n" + (container.loaded ? EnumChatFormatting.DARK_GREEN : EnumChatFormatting.DARK_RED) + container.name + EnumChatFormatting.RESET;
                if (container.loaded) LOADED_MODULES.add(container.name);
                else FeLog.warning("Not loading " + container.name);

                MODULES_MAP.put(container.name, container);
            }
            catch (Exception e)
            {
                FeLog.severe("An error occurred while trying to load a module from class " + data.getClassName());
                e.printStackTrace();
            }
        }

        event.getModMetadata().description = description;

        doConfig();

        passEvent(event);
    }

    private static void doConfig()
    {
        for (String module : LOADED_MODULES)
        {
            Configuration configuration = CoreConfig.INSTANCE.useOneConfig ? CoreConfig.INSTANCE.getConfiguration() : new Configuration(new File(ForgeEssentials.settingsFolder, module + ".cfg"));
            MODULES_MAP.get(module).module.doConfig(configuration);
            configuration.save();
        }
    }

    public static ModuleContainer getModule(String name)
    {
        return MODULES_MAP.get(name);
    }

    public static boolean isModulePresent(String name)
    {
        return MODULES_MAP.containsKey(name);
    }

    public static boolean isModuleLoaded(String name)
    {
        return LOADED_MODULES.contains(name);
    }

    /**
     * Pass an FML event to all loaded modules.
     * Don't pass FMLPreInitializationEvent, that has been done internally.
     * @param event FMLEvent to be passed on.
     */
    public static void passEvent(FMLEvent event)
    {
        for (String module : LOADED_MODULES)
        {
            MODULES_MAP.get(module).module.fmlEvent(event);
        }
    }
}
