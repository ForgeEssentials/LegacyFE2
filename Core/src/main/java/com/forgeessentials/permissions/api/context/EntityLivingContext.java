package com.forgeessentials.permissions.api.context;

import net.minecraft.entity.EntityLiving;

public class EntityLivingContext extends EntityContext implements IHealthContext
{
    private final float max, current;

    public EntityLivingContext(EntityLiving entity)
    {
        super(entity);
        max = entity.getMaxHealth();
        current = entity.getHealth();
    }

    @Override
    public float getMaxHealth()
    {
        return max;
    }

    @Override
    public float getCurrentHealth()
    {
        return current;
    }
}
