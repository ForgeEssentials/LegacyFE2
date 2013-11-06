package com.forgeessentials.core.commands;

import com.forgeessentials.core.ForgeEssentials;
import com.forgeessentials.core.modules.internal.ModuleLoaderImpl;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;

public class ReloadCommand extends FECommanBase
{
    @Override
    public String getCommandPermission()
    {
        return CmdWrapper.BASETAG_FE + ".core." + getCommandName();
    }

    @Override
    public String getCommandName()
    {
        return "fereload";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "Reload the ForgeEssentials modules";
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("Reloading...").setColor(EnumChatFormatting.YELLOW));
        ForgeEssentials.getModuleLoder().reloadAllModules();
        icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText("Done reloading!").setColor(EnumChatFormatting.DARK_GREEN));
    }
}
