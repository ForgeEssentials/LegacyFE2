package com.forgeessentials.core.commands;

import com.forgeessentials.core.ForgeEssentials;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CmdWrapper implements IFECommand
{
    public static final List<String> VANILLALIST      = Arrays.asList("time", "gamemode", "difficulty", "defaultgamemode", "kill", "toggledownfall", "weather", "xp", "tp", "give", "effect", "enchant", "me", "seed", "help", "debug", "tell", "say", "spawnpoint", "gamerule", "clear", "testfor", "spreadplayers", "playsound", "scoreboard", "op", "deop", "stop", "save-all", "save-on", "save-off", "ban-ip", "pardon-ip", "ban", "pardon", "banlist", "kick", "list", "whitelist", "setidletimeout", "publish");
    public static final String       BASETAG_FE      = "fe";
    public static final String       BASETAG_MC      = "mc";
    public static final String       BASETAG_UNKNOWN = "unknown";

    private ICommand command;
    private String   basetag;

    public CmdWrapper(ICommand command)
    {
        this.command = command;

        if (VANILLALIST.contains(command.getCommandName()))
            this.basetag = BASETAG_MC;
        else
            this.basetag = BASETAG_UNKNOWN;
    }

    public CmdWrapper(ICommand command, String basetag)
    {
        this.command = command;
        this.basetag = basetag;
    }

    @Override
    public String getCommandName()
    {
        return command.getCommandName();
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender)
    {
        return command.getCommandUsage(icommandsender);
    }

    @Override
    public List getCommandAliases()
    {
        return command.getCommandAliases();
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        command.processCommand(icommandsender, astring);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
    {
        ForgeEssentials.LOGGER.info("Command permission: " + getCommandPermission());
        // TODO: implement permision checking!
        return command.canCommandSenderUseCommand(icommandsender);
    }

    @Override
    public List addTabCompletionOptions(ICommandSender icommandsender, String[] astring)
    {
        return command.addTabCompletionOptions(icommandsender, astring);
    }

    @Override
    public boolean isUsernameIndex(String[] astring, int i)
    {
        return command.isUsernameIndex(astring, i);
    }

    @Override
    public String getCommandPermission()
    {
        if (command instanceof IFECommand) return ((IFECommand) command).getCommandPermission();
        if (command.getClass().getAnnotation(FECommand.class) != null) command.getClass().getAnnotation(FECommand.class).permission();

        return basetag + ".command." + getCommandName();
    }

    @Override
    public int compareTo(Object o)
    {
        return command.compareTo(o);
    }
}
