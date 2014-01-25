package net.minecraftforge.permissions.api;

import java.util.List;

import net.minecraft.dispenser.ILocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.FakePlayer;
import net.minecraftforge.permissions.api.PermBuilderFactory.PermReg;
import net.minecraftforge.permissions.api.context.IContext;
import net.minecraftforge.permissions.opbasedimpl.OpPermFactory;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.ModContainer;

public final class PermissionsManager
{
    private PermissionsManager()
    {
        // no touch
    }

    private static       boolean            wasSet  = false;
    private static final PermBuilderFactory DEFAULT = new OpPermFactory(); // for now, move to forge's init when we go over
    private static PermBuilderFactory FACTORY;

    public static boolean checkPerm(EntityPlayer player, String node)
    {
        if (player instanceof FakePlayer)
            throw new IllegalArgumentException("You cannot check permissions with a fake player. Use PermManager.getPerm(username, node)");

        IContext context = FACTORY.getDefaultContext(player);
        return FACTORY.builder(player.username, node)
                      .setUserContext(context)
                      .setTargetContext(context)
                      .check();
    }

    public static boolean checkPerm(EntityPlayer player, String node, Entity targetContext)
    {
        if (player instanceof FakePlayer)
            throw new IllegalArgumentException("You cannot check permissions with a fake player. Use PermManager.getPerm(username, node)");

        return FACTORY.builder(player.username, node)
                      .setUserContext(FACTORY.getDefaultContext(player))
                      .setTargetContext(FACTORY.getDefaultContext(targetContext))
                      .check();
    }

    public static boolean checkPerm(EntityPlayer player, String node, ILocation targetContext)
    {
        if (player instanceof FakePlayer)
            throw new IllegalArgumentException("You cannot check permissions with a fake player. Use PermManager.getPerm(username, node)");

        return FACTORY.builder(player.username, node)
                      .setUserContext(FACTORY.getDefaultContext(player))
                      .setTargetContext(FACTORY.getDefaultContext(targetContext))
                      .check();
    }

    public static PermBuilder getPerm(EntityPlayer player, String node)
    {
        if (player instanceof FakePlayer)
            throw new IllegalArgumentException("You cannot check permissions with a fake player. Use PermManager.getPerm(username, node)");

        IContext context = FACTORY.getDefaultContext(player);
        return FACTORY.builder(player.username, node)
                      .setUserContext(context)
                      .setTargetContext(context);
    }

    public static PermBuilder getPerm(EntityPlayer player, String node, Entity targetContext)
    {
        if (player instanceof FakePlayer)
            throw new IllegalArgumentException("You cannot check permissions with a fake player. Use PermManager.getPerm(username, node)");

        return FACTORY.builder(player.username, node)
                      .setUserContext(FACTORY.getDefaultContext(player))
                      .setTargetContext(FACTORY.getDefaultContext(targetContext));
    }

    public static PermBuilder getPerm(EntityPlayer player, String node, ILocation targetContext)
    {
        if (player instanceof FakePlayer)
            throw new IllegalArgumentException("You cannot check permissions with a fake player. Use PermManager.getPerm(username, node)");

        return FACTORY.builder(player.username, node)
                      .setUserContext(FACTORY.getDefaultContext(player))
                      .setTargetContext(FACTORY.getDefaultContext(targetContext));
    }

    public static PermBuilder getPerm(String username, String node, TileEntity userContext)
    {
        return FACTORY.builder(username, node).setUserContext(FACTORY.getDefaultContext(userContext));
    }

    public static PermBuilder getPerm(String username, String node)
    {
        return FACTORY.builder(username, node);
    }

    public static PermBuilderFactory getPermFactory()
    {
        return FACTORY;
    }

    /**
     * Register a new permissions handler. Do not use unless you know what you're doing.
     * @param factory Your permissions handler class, implementing {@link net.minecraftforge.permissions.api.PermBuilderFactory}
     * @param mod Your mod container
     * @throws IllegalStateException if there is already a permissions handler set.
     */
    public static void setPermFactory(PermBuilderFactory factory, ModContainer mod) throws IllegalStateException
    {
        if (factory == null)
        {
            FACTORY = DEFAULT;
            wasSet = false;
        }
        else if (wasSet)
        {
            throw new IllegalStateException(String.format("Mod %s tried to register a permission system when one has already been set!", mod.getModId()));
        }
        else
        {
        	FMLLog.info("Registering permission handler %s from mod %s", factory.toString(), mod.getModId());
        	FACTORY = factory;
            wasSet = true;
        }
    }
    
    /**
     * Register permissions for checking with the permission handler
     * @param perms A list of permissions, packed into a {@link net.minecraftforge.permissions.api.PermBuilderFactory.PermReg}
     */
    public static void registerPermissions(List<PermReg> perms){
    	FACTORY.registerPermissions(perms);
    	
    }
}
