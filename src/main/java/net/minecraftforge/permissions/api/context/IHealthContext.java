package net.minecraftforge.permissions.api.context;

public interface IHealthContext extends IContext
{
    float getMaxHealth();

    float getCurrentHealth();
}
