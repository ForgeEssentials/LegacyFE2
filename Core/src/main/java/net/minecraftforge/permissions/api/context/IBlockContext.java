package net.minecraftforge.permissions.api.context;

public interface IBlockContext extends IBlockLocationContext
{
    int getBlockId();

    int getBlockMetadata();

    boolean hasTileEntity();
}
