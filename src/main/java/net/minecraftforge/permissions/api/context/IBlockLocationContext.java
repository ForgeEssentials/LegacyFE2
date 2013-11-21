package net.minecraftforge.permissions.api.context;

public interface IBlockLocationContext extends ILocationContext
{
    int getBlockX();

    int getBlockY();

    int getBlockZ();
}
