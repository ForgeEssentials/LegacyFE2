package com.forgeessentials.core.modules.internal;

import com.forgeessentials.core.modules.IFEModule;
import com.forgeessentials.core.modules.ModuleContainer;
import com.forgeessentials.core.modules.ModuleLoader;
import com.forgeessentials.core.modules.ModuleState;
import cpw.mods.fml.common.discovery.ASMDataTable;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;

import java.util.HashMap;

import static com.forgeessentials.core.ForgeEssentials.LOGGER;

public class ModuleLoaderImpl implements ModuleLoader
{
    private final HashMap<String, ModuleContainerImpl> modules = new HashMap<String, ModuleContainerImpl>();

    /**
     * Lods the modules.
     */
    public void init(FMLPreInitializationEvent event)
    {
        LOGGER.info("Discovering all modules...");
        String description = event.getModMetadata().description;
        description += "\n" + EnumChatFormatting.UNDERLINE + "Modules:" + EnumChatFormatting.RESET + "\n";

        for (ASMDataTable.ASMData data : event.getAsmData().getAll(IFEModule.LoadMe.class.getName()))
        {
            ModuleContainerImpl container = new ModuleContainerImpl(data);
            description += "\n" + (container.shouldBeLoaded() ? EnumChatFormatting.DARK_GREEN : EnumChatFormatting.DARK_RED) + container.getModuleName() + EnumChatFormatting.RESET;

            modules.put(container.getModuleName(), container);

            if (container.shouldBeLoaded())
            {
                container.doConfig(false);
            }
            else
            {
                LOGGER.debug("Skipping config loading for {}", container.getModuleName());
            }
        }

        event.getModMetadata().description = description;
    }

    /**
     * enables all possible modules.
     */
    public void enable(FMLServerStartingEvent event)
    {
        for (ModuleContainerImpl container : modules.values())
        {
            if (container.shouldBeLoaded())
            {
                container.enableModule(event);
            }
            else
            {
                LOGGER.debug("Skipping Enable for {}", container.getModuleName());
            }
        }
    }

    /**
     * Disables all possible modules.
     */
    public void disable()
    {
        for (ModuleContainerImpl container : modules.values())
        {
            if (container.shouldBeLoaded())
            {
                container.disableModule();
            }
            else
            {
                LOGGER.debug("Skipping Disable for {}", container.getModuleName());
            }
        }
    }

    @Override
    public void reloadAllModules()
    {
        for (ModuleContainerImpl container : modules.values())
        {
            if (container.shouldBeLoaded())
            {
                container.doConfig(true);
            }
            else
            {
                LOGGER.debug("Skipping config reload for {}", container.getModuleName());
            }
        }
    }

    @Override
    public ModuleContainerImpl getModule(String name)
    {
        return modules.get(name);
    }

    @Override
    public boolean doesModuleExist(String moduleName)
    {
        return modules.containsKey(moduleName);
    }

    public boolean isModuleEnabled(String name)
    {
        ModuleContainer module = modules.get(name);
        return module == null ? false : module.getModuleState() == ModuleState.ENABLED;
    }

    @Override
    public void enableModule(String moduleName, MinecraftServer server)
    {
        ModuleContainerImpl module = modules.get(moduleName);
        if (module == null)
        {
            return;
        }

        if (module.shouldBeLoaded() && module.getModuleState() != ModuleState.ENABLED)
        {
            module.enableModule(server);
        }
    }

    @Override
    public void disableModule(String moduleName)
    {
        ModuleContainerImpl module = modules.get(moduleName);
        if (module == null)
        {
            return;
        }

        if (module.shouldBeLoaded() && module.getModuleState() == ModuleState.DISABLED)
        {
            module.disableModule();
        }
    }

    @Override
    public void reloadModule(String moduleName)
    {
        ModuleContainerImpl module = modules.get(moduleName);
        if (module == null)
        {
            return;
        }

        if (module.shouldBeLoaded())
        {
            module.doConfig(true);
        }
    }
}
