package com.forgeessentials.core.modules.events;

import com.forgeessentials.core.modules.ModuleContainer;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;

public interface FEModuleEvent
{
    ModuleContainer getContainer();

    /**
     * This method returns whatever FML returns in its State events.
     * Though this is subject to change, it should be assumed that this is the physical side, and not the effective side.
     * @return The current side.
     * @see cpw.mods.fml.common.event.FMLStateEvent
     */
    Side getSide();

    /**
     * This method creates a logger for this module. If this method is not called, a logger is not allocated.
     * Any logger created by this method should be a child of the ForgeEssentials logger instance.
     * @return an SLF4J Logger instance for this module.
     * @see Logger
     */
    Logger getLogger();
}
