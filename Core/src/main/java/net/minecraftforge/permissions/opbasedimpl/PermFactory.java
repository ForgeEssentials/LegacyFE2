package net.minecraftforge.permissions.opbasedimpl;

import net.minecraft.dispenser.ILocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.permissions.api.PermBuilder;
import net.minecraftforge.permissions.api.PermBuilderFactory;
import net.minecraftforge.permissions.api.RegisteredPermValue;
import net.minecraftforge.permissions.api.context.*;
import net.minecraftforge.permissions.opbasedimpl.context.Point;
import net.minecraftforge.permissions.opbasedimpl.context.TileEntityContext;
import net.minecraftforge.permissions.opbasedimpl.context.WorldContext;

import java.util.Map;

public class PermFactory implements PermBuilderFactory
{
    public static final IContext GLOBAL = new IContext() {};

    @Override
    public PermBuilder builder()
    {
        return new Builder();
    }

    @Override
    public PermBuilder builder(String username, String permNode)
    {
        return new Builder().setUserName(username).setPermNode(permNode);
    }

    @Override
    public IContext getDefaultContext(EntityPlayer player)
    {
        return new PlayerContext(player);
    }

    @Override
    public IContext getDefaultContext(TileEntity te)
    {
        return new TileEntityContext(te);
    }

    @Override
    public IContext getDefaultContext(ILocation loc)
    {
        return new Point(loc);
    }

    @Override
    public IContext getDefaultContext(Entity entity)
    {
        return new EntityContext(entity);
    }

    @Override
    public IContext getDefaultContext(World world)
    {
        return new WorldContext(world);
    }

    @Override
    public IContext getGlobalContext()
    {
        return GLOBAL;
    }

    @Override
    public IContext getDefaultContext(Object entity)
    {
        if (entity instanceof EntityLiving)
            return new EntityLivingContext((EntityLiving) entity);
        else
            return GLOBAL;
    }

    @Override
    public void registerPermissions(Map<String, RegisteredPermValue> perms)
    {
        // TODO: stuff
    }
}
