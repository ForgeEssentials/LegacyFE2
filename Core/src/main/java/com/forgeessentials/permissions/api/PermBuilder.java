package com.forgeessentials.permissions.api;

import com.forgeessentials.permissions.api.context.IContext;
import net.minecraft.dispenser.ILocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public interface PermBuilder
{
    boolean check();

    PermBuilder setUserName(String name);

    PermBuilder setPermNode(String node);

    PermBuilder setTargetContext(IContext context);

    PermBuilder setUserContext(IContext context);
}