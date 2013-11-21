package net.minecraftforge.permissions.api.context;

import net.minecraft.entity.Entity;

public class EntityContext implements ILocationContext, IRotationContext
{
    private final double x, y, z;
    private final int dim, entityId;
    private final float pitch, yaw;

    public EntityContext(Entity entity)
    {
        dim = entity.worldObj.provider.dimensionId;
        entityId = entity.entityId;
        x = entity.posX;
        y = entity.posY;
        z = entity.posZ;
        pitch = entity.rotationPitch;
        yaw = entity.rotationYaw;
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
    public int getDimensionId()
    {
        return dim;
    }

    public int getEntityId()
    {
        return entityId;
    }

    @Override
    public float getPitch()
    {
        return pitch;
    }

    @Override
    public float getYaw()
    {
        return yaw;
    }
}
