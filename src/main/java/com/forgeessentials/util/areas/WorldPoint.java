package com.forgeessentials.util.areas;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import com.forgeessentials.util.data.api.IReconstructData;
import com.forgeessentials.util.data.api.SaveableObject;
import com.forgeessentials.util.data.api.SaveableObject.Reconstructor;
import com.forgeessentials.util.data.api.SaveableObject.SaveableField;
import com.forgeessentials.util.data.api.SaveableObject.UniqueLoadingKey;

@SaveableObject(SaveInline = true)
public class WorldPoint extends Point
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5462406378573144189L;
	@SaveableField
	public int					dim;

	public WorldPoint(int dimension, int x, int y, int z)
	{
		super(x, y, z);
		dim = dimension;
	}

	public WorldPoint(World world, int x, int y, int z)
	{
		super(x, y, z);
		dim = world.provider.dimensionId;
	}

	public WorldPoint(Entity player)
	{
		super(player);
		dim = player.dimension;
	}

	public int compareTo(WorldPoint p)
	{
		int diff = dim - p.dim;

		if (diff == 0)
		{
			diff = super.compareTo(p);
		}
		return diff;
	}

	public boolean equals(WorldPoint p)
	{
		return dim == p.dim && super.equals(p);
	}

	public WorldPoint copy(WorldPoint p)
	{
		return new WorldPoint(p.dim, p.x, p.y, p.z);
	}

	@Reconstructor()
	public static WorldPoint reconstruct(IReconstructData tag)
	{
		int x = (Integer) tag.getFieldValue("x");
		int y = (Integer) tag.getFieldValue("y");
		int z = (Integer) tag.getFieldValue("z");
		int dim = (Integer) tag.getFieldValue("dim");
		return new WorldPoint(dim, x, y, z);
	}

	@UniqueLoadingKey()
	private String getLoadingField()
	{
		return "WorldPoint" + this;
	}

	@Override
	public String toString()
	{
		return "WorldPoint[" + dim + ", " + x + ", " + y + ", " + z + "]";
	}
}
