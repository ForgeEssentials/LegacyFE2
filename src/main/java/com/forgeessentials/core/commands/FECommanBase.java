package com.forgeessentials.core.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.permissions.api.PermissionsManager;

import com.forgeessentials.core.ForgeEssentials;

/**
 * Use this class for internal FE commands.
 */
public abstract class FECommanBase extends CommandBase implements IFECommand
{
    // checks command if it's a player, else just allow
	@Override
    public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
        ForgeEssentials.LOGGER.info("Command permission: " + getCommandPermission());
        if (sender instanceof EntityPlayer)
        	return PermissionsManager.checkPerm((EntityPlayer)sender, getCommandPermission());
        else return true;
    }

    public int compareTo(Object obj)
    {
        return 0;
    }
}
