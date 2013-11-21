package net.minecraftforge.permissions.api.context.impls;

import net.minecraft.world.World;
import net.minecraftforge.permissions.api.context.IDimensionContext;

public class WorldContext implements IDimensionContext
{
    private final int id;

    public WorldContext(World world)
    {
        id = world.provider.dimensionId;
    }

    @Override
    public int getDimensionId()
    {
        return id;
    }
}
