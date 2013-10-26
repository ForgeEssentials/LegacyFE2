package com.forgeessentials.core.commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this or the IFECommand interface. The interface takes priority.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FECommand
{
    /**
     * @return perm in format: modid.command.commandname
     */
    String permission();
}
