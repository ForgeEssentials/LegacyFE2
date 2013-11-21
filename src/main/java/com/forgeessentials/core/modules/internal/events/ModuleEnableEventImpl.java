package com.forgeessentials.core.modules.internal.events;

import com.forgeessentials.core.modules.ModuleContainer;
import com.forgeessentials.core.modules.events.ModuleEnableEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.server.MinecraftServer;

public class ModuleEnableEventImpl extends AbstractModuleEvent implements ModuleEnableEvent
{
    private final MinecraftServer server;

    public ModuleEnableEventImpl(ModuleContainer container, FMLServerStartingEvent event)
    {
        super(container, event);
        server = event.getServer();
    }

    public ModuleEnableEventImpl(ModuleContainer container, MinecraftServer server)
    {
        super(container);
        this.server = server;
    }

    @Override
    public void registerCommand()
    {
        // TODO
    }

    @Override
    public MinecraftServer getServer()
    {
        return server;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void sendToMod(String modid, FMLInterModComms.IMCMessage message)
    {
        // TODO
    }

    @Override
    public void sendToModule(String moduleName, FMLInterModComms.IMCMessage message)
    {
        // TODO
    }
}
