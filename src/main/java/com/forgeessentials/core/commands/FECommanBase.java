package com.forgeessentials.core.commands;

import com.forgeessentials.core.ForgeEssentials;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

/**
 * Use this class for internal FE commands.
 */
public abstract class FECommanBase extends CommandBase implements IFECommand
{
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender)
    {
        ForgeEssentials.LOGGER.info("Command permission: " + getCommandPermission());
        //Todo: implement permission checking!
        return true;
    }

    public int compareTo(Object obj)
    {
        return 0;
    }
}
