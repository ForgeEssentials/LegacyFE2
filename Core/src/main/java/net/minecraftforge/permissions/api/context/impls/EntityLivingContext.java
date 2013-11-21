package net.minecraftforge.permissions.api.context.impls;

import net.minecraft.entity.EntityLiving;
import net.minecraftforge.permissions.api.context.IHealthContext;

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
