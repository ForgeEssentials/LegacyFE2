package com.forgeessentials.permissions.api.context;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerContext extends EntityContext
{
    private final String username;

    public PlayerContext(EntityPlayer entity)
    {
        super(entity);
        username = entity.username;
    }

    public String getUsername()
    {
        return username;
    }
}
