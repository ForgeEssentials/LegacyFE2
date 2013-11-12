package com.forgeessentials.permissions.api;

import net.minecraft.dispenser.ILocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public final class PermissionsManager
{
    private PermissionsManager()
    {
        // no touch
    }

    private static PermBuilderFactory FACTORY;

    public static boolean checkPerm(EntityPlayer player, String node)
    {
        return FACTORY.builder(player.username, node).setUserContext(player).setTargetContext(player).check();
    }

    public static boolean checkPerm(EntityPlayer player, String node, ILocation targetContext)
    {
        return FACTORY.builder(player.username, node).setUserContext(player).setTargetContext(targetContext).check();
    }

    public static boolean checkPerm(EntityPlayer player, String node, Entity targetContext)
    {
        return FACTORY.builder(player.username, node).setUserContext(player).setTargetContext(targetContext).check();
    }


    public static PermBuilder gertPerm(String username, String node)
    {
        return FACTORY.builder(username, node);
    }
}
