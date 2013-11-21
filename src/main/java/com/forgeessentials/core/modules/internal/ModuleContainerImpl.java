package com.forgeessentials.core.modules.internal;

import com.forgeessentials.core.ForgeEssentials;
import com.forgeessentials.core.modules.IFEModule;
import com.forgeessentials.core.modules.ModuleContainer;
import com.forgeessentials.core.modules.ModuleLoadException;
import com.forgeessentials.core.modules.ModuleState;
import com.forgeessentials.core.modules.events.ModuleDisableEvent;
import com.forgeessentials.core.modules.events.ModuleEnableEvent;
import com.forgeessentials.core.modules.internal.events.ModuleDisableEventImpl;
import com.forgeessentials.core.modules.internal.events.ModuleEnableEventImpl;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.discovery.ASMDataTable;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.server.MinecraftServer;

import java.io.File;

import static com.forgeessentials.core.ForgeEssentials.LOGGER;

public class ModuleContainerImpl implements ModuleContainer
{

    private final String                     name;
    private       String                     version;
    private final IFEModule                  module;
    private final Class<? extends IFEModule> clazz;
    private final IFEModule.LoadMe           annotation;
    private final boolean                    loadOnClient;
    private final boolean                    loadOnServer;
    private       boolean                    doesLoad;
    private ModuleState state = ModuleState.UNLOADED;

    protected ModuleContainerImpl(ASMDataTable.ASMData asmData)
    {
        try
        {
            LOGGER.info("Module class:  " + asmData.getClassName());

            // the classCast exception is caught later
            clazz = (Class<? extends IFEModule>) Class.forName(asmData.getClassName());
            changeState(ModuleState.CLASSLOADED);

            annotation = clazz.getAnnotation(IFEModule.LoadMe.class);
            name = annotation.name();
            loadOnClient = annotation.loadOnClient();
            loadOnServer = annotation.loadOnServer();

            doesLoad = (FMLCommonHandler.instance().getSide().isClient() && loadOnClient) || (FMLCommonHandler.instance().getSide().isServer() && loadOnServer);

            if (doesLoad)
            {
                module = (IFEModule) clazz.newInstance();
                version = module.getVersion();
                changeState(ModuleState.INSTANTIATED);
                LOGGER.info("Module instantiated:  {}", asmData.getClassName());
            }
            else
            {
                module = null;
            }
        }
        // all the catch statements
        // the reason these are not logged, is because they should NEVER happen outside the dev environment
        catch (ClassNotFoundException | NoClassDefFoundError e)
        {
            throw new RuntimeException(asmData.getClassName() + " does not exist!");
        }
        catch (ClassCastException e)
        {
            throw new RuntimeException(asmData.getClassName() + " must implement IFEModule!");
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            throw new RuntimeException(asmData.getClassName() + " must have an empty public constructor!");
        }
    }

    void doConfig(boolean reload)
    {
        try
        {
            module.doConfig(new File(ForgeEssentials.SETTINGS_DIR, name + ".cfg"), reload);
            changeState(ModuleState.CONFIGURED);
        }
        catch (ModuleLoadException e)
        {
            doesLoad = false;
            LOGGER.warn("{} is crashing quietly in state {}!", name, state);
            LOGGER.debug(name + " is crashing quietly!", e);
        }
    }

    void enableModule(FMLServerStartingEvent fmlEvent)
    {
        enableModule(new ModuleEnableEventImpl(this, fmlEvent));
    }

    void enableModule(MinecraftServer server)
    {
        enableModule(new ModuleEnableEventImpl(this, server));
    }

    private void enableModule(ModuleEnableEvent event)
    {
        try
        {
            module.enable(event);
            changeState(ModuleState.ENABLED);

            // TODO: command stuff?
            // TODO: message stuff?
        }
        catch (ModuleLoadException e)
        {
            doesLoad = false;
            LOGGER.warn("{} is crashing quietly in state {}!", name, state);
            LOGGER.debug(name + " is crashing quietly!", e);
        }
    }

    void disableModule()
    {
        try
        {
            ModuleDisableEvent event = new ModuleDisableEventImpl(this);
            module.disable(event);
            changeState(ModuleState.DISABLED);
        }
        catch (ModuleLoadException e)
        {
            doesLoad = false;
            LOGGER.warn("{} is crashing quietly in state {}!", name, state);
            LOGGER.debug(name + " is crashing quietly!", e);
        }
    }

    @Override
    public ModuleState getModuleState()
    {
        return state;
    }

    @Override
    public Class<? extends IFEModule> getModuleClass()
    {
        return clazz;
    }

    @Override
    public boolean shouldBeLoaded()
    {
        return doesLoad;
    }

    @Override
    public boolean loadsOnClient()
    {
        return loadOnClient;
    }

    @Override
    public boolean loadsOnServer()
    {
        return loadOnServer;
    }

    @Override
    public IFEModule getModuleInstance()
    {
        return module;
    }

    @Override
    public String getModuleName()
    {
        return name;
    }

    @Override
    public String getModuleVersion()
    {
        if (state.ordinal() >= ModuleState.INSTANTIATED.ordinal())
        {
            return version;
        }
        else
        {
            // this should never be thrown unless its in a dev ENV either.
            throw new IllegalStateException("Module " + name + " has not been instantiated! And you cannot get its version till it is!");
        }
    }

    /**
     * Only changes the state of the container when it makes sense to...
     */
    private void changeState(ModuleState newState)
    {
        if (state.compareTo(newState) < 0 || (state == ModuleState.DISABLED && newState == ModuleState.ENABLED))
        {
            state = newState;
        }
    }

}
