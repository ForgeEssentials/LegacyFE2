package net.minecraftforge.permissions.opbasedimpl.context;

import net.minecraft.dispenser.ILocation;
import net.minecraftforge.permissions.api.context.ILocationContext;

public class Point implements ILocationContext
{
    private final double x, y, z;
    private final int dim;

    public Point(double x, double y, double z, int dim)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dim = dim;
    }

    public Point(ILocation loc)
    {
        x = loc.getX();
        y = loc.getY();
        z = loc.getZ();
        this.dim = loc.getWorld().provider.dimensionId;
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
