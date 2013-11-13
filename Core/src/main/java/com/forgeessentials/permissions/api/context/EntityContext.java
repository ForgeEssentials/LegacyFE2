package com.forgeessentials.permissions.api.context;

import net.minecraft.entity.Entity;

public class EntityContext implements ILocationContext
{
    private final double x, y, z;
    private final int dim, entityId;

    public EntityContext(Entity entity)
    {
        dim = entity.worldObj.provider.dimensionId;
        entityId = entity.entityId;
        x = entity.posX;
        y = entity.posY;
        z = entity.posZ;
    }

    @Override
    public double getX()
    {
        return x;
    }

    @Override
    public double getY()
    {
        return y;
    }

    @Override
    public double getZ()
    {
        return z;
    }

    @Override
    public int getDimension()
    {
        return dim;
    }

    public int getEntityId()
    {
        return entityId;
    }
}
