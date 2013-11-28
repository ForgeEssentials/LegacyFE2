package com.forgeessentials.core.modules.internal.events;

import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.server.MinecraftServer;

import com.forgeessentials.core.modules.ModuleContainer;
import com.forgeessentials.core.modules.events.ModuleEnableEvent;

import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class ModuleEnableEventImpl extends AbstractModuleEvent implements ModuleEnableEvent
{
    private final MinecraftServer server;
    private final FMLServerStartingEvent event;

    public ModuleEnableEventImpl(ModuleContainer container, FMLServerStartingEvent event)
    {
        super(container, event);
        server = event.getServer();
        this.event = event;
    }

    public ModuleEnableEventImpl(ModuleContainer container, MinecraftServer server)
    {
        super(container);
        this.server = server;
        this.event = null;
    }

    @Override
    public void registerCommand(ICommand command)
    {
    	event.registerServerCommand(command);
    }

    @Override
    public MinecraftServer getServer()
    {
        return server;
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
