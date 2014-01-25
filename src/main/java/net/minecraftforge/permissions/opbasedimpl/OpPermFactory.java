package net.minecraftforge.permissions.opbasedimpl;

import net.minecraft.dispenser.ILocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.permissions.api.PermBuilder;
import net.minecraftforge.permissions.api.PermBuilderFactory;
import net.minecraftforge.permissions.api.context.EntityContext;
import net.minecraftforge.permissions.api.context.EntityLivingContext;
import net.minecraftforge.permissions.api.context.IContext;
import net.minecraftforge.permissions.api.context.PlayerContext;
import net.minecraftforge.permissions.opbasedimpl.context.Point;
import net.minecraftforge.permissions.opbasedimpl.context.TileEntityContext;
import net.minecraftforge.permissions.opbasedimpl.context.WorldContext;

import java.util.List;
import java.util.TreeSet;

import cpw.mods.fml.common.FMLLog;

public class OpPermFactory implements PermBuilderFactory<Builder>
{
    static TreeSet<String> opPerms      = new TreeSet<String>();
    static TreeSet<String> deniedPerms  = new TreeSet<String>();
    static TreeSet<String> allowedPerms = new TreeSet<String>();

    public static final IContext GLOBAL = new IContext() {};

    @Override
    public Builder builder()
    {
        return new Builder();
    }

    @Override
    public Builder builder(String username, String permNode)
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
        {
            return new EntityLivingContext((EntityLiving) entity);
        }
        else
        {
            return GLOBAL;
        }
    }

    @Override
    public void registerPermissions(List<PermReg> perms)
    {
        for (PermReg entry : perms)
        {
            // avoid duplicates that are already configured
            if (isRegistered(entry.key))
                continue;
            
            FMLLog.fine("Registering permission node %s with default value %s", entry.key, entry.role);

            switch(entry.role)
            {
                case OP:
                    opPerms.add(entry.key);
                    break;
                case NONOP:
                    allowedPerms.add(entry.key);
                    break;
                case FALSE:
                    deniedPerms.add(entry.key);
                    break;
                case TRUE:
                    allowedPerms.add(entry.key);
                    break;
            }
        }
    }

    private static boolean isRegistered(String node)
    {
        return opPerms.contains(node) || allowedPerms.contains(node) || deniedPerms.contains(node);
    }
}
