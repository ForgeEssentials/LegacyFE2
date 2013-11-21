package com.forgeessentials.core.modules.internal.events;

import com.forgeessentials.core.ForgeEssentials;
import com.forgeessentials.core.modules.ModuleContainer;
import com.forgeessentials.core.modules.events.FEModuleEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLStateEvent;
import cpw.mods.fml.relauncher.Side;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractModuleEvent implements FEModuleEvent
{
    private final Side            side;
    private final ModuleContainer container;

    public AbstractModuleEvent(ModuleContainer container, FMLStateEvent event)
    {
        side = event.getSide();
        this.container = container;
    }

    public AbstractModuleEvent(ModuleContainer container)
    {
        side = FMLCommonHandler.instance().getSide();
        this.container = container;
    }

    @Override
    public ModuleContainer getContainer()
    {
        return container;
    }

    @Override
    public Side getSide()
    {
        return side;
    }

    @Override
    public Logger getLogger()
    {
        // setup JUL logger
        java.util.logging.Logger tempLogger = java.util.logging.Logger.getLogger(container.getModuleName());
        tempLogger.setParent(java.util.logging.Logger.getLogger(ForgeEssentials.MODID));
        tempLogger.setUseParentHandlers(true);

        // return the new logger
        return LoggerFactory.getLogger(container.getModuleName());
    }
}
