package com.forgeessentials.core.modules;

import com.forgeessentials.util.FeLog;
import cpw.mods.fml.common.discovery.ASMDataTable;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.util.EnumChatFormatting;

import java.util.HashMap;
import java.util.HashSet;

public class ModuleCenter
{
    private static final HashSet<String>                  LOADEDMODULES = new HashSet<String>();
    private static final HashMap<String, ModuleContainer> MODULESMAP    = new HashMap<String, ModuleContainer>();

    public static void init(FMLPreInitializationEvent event)
    {
        FeLog.info("Discovering all modules...");
        String description = event.getModMetadata().description;
        description += "\n" + EnumChatFormatting.UNDERLINE + "Modules:" + EnumChatFormatting.RESET + "\n";

        for (ASMDataTable.ASMData data : event.getAsmData().getAll(IModule.LoadMe.class.getName()))
        {
            try
            {
                ModuleContainer container = new ModuleContainer(data);
                description += "\n" + (container.load ? EnumChatFormatting.DARK_GREEN : EnumChatFormatting.DARK_RED) + container.name + EnumChatFormatting.RESET;
                if (container.load) LOADEDMODULES.add(container.name);
                else FeLog.fine("Not loading " + container.name);

                MODULESMAP.put(container.name, container);
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
        return MODULESMAP.get(name);
    }

    public static boolean isModulePresent(String name)
    {
        return MODULESMAP.containsKey(name);
    }

    public static boolean isModuleLoaded(String name)
    {
        return LOADEDMODULES.contains(name);
    }
}
