package com.forgeessentials.core.commands;

import net.minecraft.command.ICommand;

/**
 * Use this or the FeCommand annotation. This takes priority.
 */
public interface IFECommand extends ICommand
{
    public String getCommandPermission();
}
