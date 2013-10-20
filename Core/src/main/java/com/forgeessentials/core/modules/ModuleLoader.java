package com.forgeessentials.core.modules;

import com.forgeessentials.util.FeLog;
import cpw.mods.fml.common.discovery.ASMDataTable;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.util.EnumChatFormatting;

import java.util.HashMap;
import java.util.HashSet;

public class ModuleLoader
{
    private static final HashSet<String>                  LOADED_MODULES = new HashSet<String>();
    private static final HashMap<String, ModuleContainer> MODULES_MAP    = new HashMap<String, ModuleContainer>();

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
                description += "\n" + (container.load ? EnumChatFormatting.DARK_GREEN : EnumChatFormatting.DARK_RED) + container.name + EnumChatFormatting.RESET;
                if (container.load) LOADED_MODULES.add(container.name);
                else FeLog.fine("Not loading " + container.name);

                MODULES_MAP.put(container.name, container);
            }
            catch (Exception e)
            {
                FeLog.severe("An error occurred while trying to load a module from class " + data.getClassName());
                e.printStackTrace();
            }
        }

        event.getModMetadata().description = description;
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
}
