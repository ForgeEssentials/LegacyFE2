package net.minecraftforge.permissions.api.context.impls;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.permissions.api.context.INameContext;

public class PlayerContext extends EntityContext implements INameContext
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
