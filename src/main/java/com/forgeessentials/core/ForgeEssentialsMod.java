package com.forgeessentials.core;

import com.forgeessentials.core.commands.CmdWrapper;
import com.forgeessentials.core.commands.FECommandBase;
import com.forgeessentials.core.commands.ReloadCommand;
import com.forgeessentials.core.modules.internal.ModuleLoaderImpl;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraft.server.MinecraftServer;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashSet;

@Mod(modid = ForgeEssentials.MODID, name = ForgeEssentials.NAME, version = ForgeEssentials.VERSION)
public class ForgeEssentialsMod
{
    @Mod.Instance(ForgeEssentials.MODID)
    static ForgeEssentialsMod instance;

    ModuleLoaderImpl moduleLoader;

    @Mod.EventHandler
    public void handleFMLEvent(FMLPreInitializationEvent event)
    {
        ForgeEssentials.preInit(event);

        CoreConfig.INSTANCE.init(new File(ForgeEssentials.SETTINGS_DIR, "ForgeEssentialsCore.cfg"));

        moduleLoader = new ModuleLoaderImpl();
        moduleLoader.init(event);
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLServerStartingEvent event)
    {
        moduleLoader.enable(event);

        event.registerServerCommand(new ReloadCommand());
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLServerStartedEvent event)
    {
        // doCommandCheck(); commented out for now.. this is causing me problems
    }

    @Mod.EventHandler
    public void handleFMLEvent(FMLServerStoppingEvent event)
    {
        moduleLoader.disable();
    }

    /**
     * Loops over all commands to make sure they have permissions associated with them.
     */
    private void doCommandCheck()
    {
        CommandHandler commandHandler = (CommandHandler) MinecraftServer.getServer().getCommandManager();
        for (Field field : commandHandler.getClass().getFields())
        {
            field.setAccessible(true);
        }

        HashSet<Object> oldSet = new HashSet<Object>(commandHandler.commandSet);
        commandHandler.commandSet.clear();
        commandHandler.commandMap.clear();
        for (Object command : oldSet)
        {
            if (command instanceof FECommandBase)
            {
                commandHandler.registerCommand((ICommand) command);
            }
            else
            {
                commandHandler.registerCommand(new CmdWrapper((ICommand) command));
            }
        }
    }
}
