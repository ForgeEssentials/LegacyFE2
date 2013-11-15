package net.minecraftforge.permissions.opbasedimpl.context;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.permissions.api.context.IBlockContext;

public class TileEntityContext implements IBlockContext
{
    private int x, y, z, id, meta, dim;

    public TileEntityContext(TileEntity entity)
    {
        x = entity.xCoord;
        y = entity.yCoord;
        z = entity.zCoord;
        id = entity.blockType.blockID;
        meta = entity.blockMetadata;
        dim = entity.worldObj.provider.dimensionId;
    }

    @Override
    public int getBlockId()
    {
        return id;
    }

    @Override
    public int getBlockMetadata()
    {
        return meta;
    }

    @Override
    public boolean hasTileEntity()
    {
        return true;
    }

    @Override
    public int getBlockX()
    {
        return x;
    }

    @Override
    public int getBlockY()
    {
        return y;
    }

    @Override
    public int getBlockZ()
    {
        return z;
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
}
