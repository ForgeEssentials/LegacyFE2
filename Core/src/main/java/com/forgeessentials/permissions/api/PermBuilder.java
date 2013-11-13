package com.forgeessentials.permissions.api;

import net.minecraft.dispenser.ILocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public interface PermBuilder
{
    boolean check();

    PermBuilder setUserName(String name);

    PermBuilder setPermNode(String node);

    PermBuilder setTargetContext(ILocation context);

    PermBuilder setTargetContext(Entity entity);

    PermBuilder setUserContext(ILocation context);

    PermBuilder setUserContext(Entity entity);
}