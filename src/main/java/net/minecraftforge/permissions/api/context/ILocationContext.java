package net.minecraftforge.permissions.api.context;

public interface ILocationContext extends IDimensionContext
{
    double getX();

    double getY();

    double getZ();
}
