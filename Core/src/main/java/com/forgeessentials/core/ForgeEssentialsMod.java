package com.forgeessentials.core;

import com.forgeessentials.core.commands.CmdWrapper;
import com.forgeessentials.core.commands.FECommanBase;
import com.forgeessentials.core.commands.ReloadCommand;
import com.forgeessentials.core.modules.FMLevents.FMLEventHandler;
import com.forgeessentials.core.modules.ModuleLoader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.*;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;

@Mod(modid = ForgeEssentials.MODID, name = ForgeEssentials.NAME)
public class ForgeEssentialsMod
{
    @Mod.EventHandler
    public void handleFMLEvent(FMLPreInitializationEvent event)
    {
        ForgeEssentials.preInit(event);

        CoreConfig.INSTANCE.init(new File(ForgeEssentials.SETTINGS_DIR, "ForgeEssentialsCore.cfg"));

        ModuleLoader.init(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLInitializationEvent event)
    {
        FMLEventHandler.passEvent(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLPostInitializationEvent event)
    {
        FMLEventHandler.passEvent(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLServerAboutToStartEvent event)
    {
        FMLEventHandler.passEvent(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLServerStartingEvent event)
    {
        ModuleLoader.enable(event.getServer());
        FMLEventHandler.passEvent(event);

        event.registerServerCommand(new ReloadCommand());
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLServerStartedEvent event)
    {
        doCommandCheck();
        FMLEventHandler.passEvent(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLServerStoppingEvent event)
    {
        ModuleLoader.disable();
        FMLEventHandler.passEvent(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLServerStoppedEvent event)
    {
        FMLEventHandler.passEvent(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLInterModComms.IMCEvent event)
    {
        FMLEventHandler.passEvent(event);
    }

    /**
     * Loops over all commands to make sure they have permissions associated with them.
     */
    private void doCommandCheck()
    {
        CommandHandler commandHandler = (CommandHandler) MinecraftServer.getServer().getCommandManager();
        for (Field field : commandHandler.getClass().getFields()) field.setAccessible(true);

        HashSet<Object> oldSet = new HashSet<Object>(commandHandler.commandSet);
        commandHandler.commandSet.clear();
        commandHandler.commandMap.clear();
        for (Object command : oldSet)
        {
            if (command instanceof FECommanBase) commandHandler.registerCommand((ICommand) command);
            else commandHandler.registerCommand(new CmdWrapper((ICommand) command));
        }
    }
}
