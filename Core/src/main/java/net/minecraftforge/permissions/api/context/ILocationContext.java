package net.minecraftforge.permissions.api.context;

public interface ILocationContext extends IContext
{
    double getX();

    double getY();

    double getZ();

    int getDimension();
}
