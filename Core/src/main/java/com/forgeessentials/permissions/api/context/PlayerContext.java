package com.forgeessentials.permissions.api.context;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerContext extends EntityContext implements INamed
{
    private final String username;

    public PlayerContext(EntityPlayer entity)
    {
        super(entity);
        username = entity.username;
    }

    @Override
    public String getName()
    {
        return username;
    }
}
