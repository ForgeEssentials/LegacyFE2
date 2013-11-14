package net.minecraftforge.permissions.api.context;

public interface IBlockContext extends ILocationContext
{
    int getBlockId();

    int getBlockMetadata();

    int getBlockX();

    int getBlockY();

    int getBlockZ();
}
