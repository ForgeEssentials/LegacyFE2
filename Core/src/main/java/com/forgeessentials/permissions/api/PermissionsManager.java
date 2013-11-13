package com.forgeessentials.permissions.api;

import com.forgeessentials.permissions.api.context.IContext;
import net.minecraft.dispenser.ILocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public final class PermissionsManager
{
    private PermissionsManager()
    {
        // no touch
    }

    private static PermBuilderFactory FACTORY;

    public static boolean checkPerm(EntityPlayer player, String node)
    {
        IContext context = FACTORY.getDefaultContext(player);
        return FACTORY.builder(player.username, node)
                      .setUserContext(context)
                      .setTargetContext(context)
                      .check();
    }

    public static boolean checkPerm(EntityPlayer player, String node, Entity targetContext)
    {
        return FACTORY.builder(player.username, node)
                      .setUserContext(FACTORY.getDefaultContext(player))
                      .setTargetContext(FACTORY.getDefaultContext(targetContext))
                      .check();
    }

    public static boolean checkPerm(EntityPlayer player, String node, ILocation targetContext)
    {
        return FACTORY.builder(player.username, node)
                      .setUserContext(FACTORY.getDefaultContext(player))
                      .setTargetContext(FACTORY.getDefaultContext(targetContext))
                      .check();
    }

    public static PermBuilder getPerm(EntityPlayer player, String node)
    {
        IContext context = FACTORY.getDefaultContext(player);
        return FACTORY.builder(player.username, node)
                      .setUserContext(context)
                      .setTargetContext(context);
    }

    public static PermBuilder getPerm(EntityPlayer player, String node, Entity targetContext)
    {
        return FACTORY.builder(player.username, node)
                      .setUserContext(FACTORY.getDefaultContext(player))
                      .setTargetContext(FACTORY.getDefaultContext(targetContext));
    }

    public static PermBuilder getPerm(EntityPlayer player, String node, ILocation targetContext)
    {
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

    public static PermBuilderFactory setPermFactory()
    {
        return FACTORY;
    }
}
