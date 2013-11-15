package net.minecraftforge.permissions.opbasedimpl;

import net.minecraft.dispenser.ILocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.permissions.api.PermBuilder;
import net.minecraftforge.permissions.api.PermBuilderFactory;
import net.minecraftforge.permissions.api.RegisteredPermValue;
import net.minecraftforge.permissions.api.context.IContext;
import net.minecraftforge.permissions.api.context.PlayerContext;

import java.util.Map;

public class PermFactory implements PermBuilderFactory
{


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
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IContext getDefaultContext(ILocation loc)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IContext getDefaultContext(Entity entity)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IContext getDefaultContext(World world)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IContext getGlobalContext()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public IContext getDefaultContext(Object entity)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void registerPermissions(Map<String, RegisteredPermValue> perms)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
