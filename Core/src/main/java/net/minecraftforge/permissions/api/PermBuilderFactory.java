package net.minecraftforge.permissions.api;

import net.minecraftforge.permissions.api.context.IContext;
import net.minecraft.dispenser.ILocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import java.util.Map;

public interface PermBuilderFactory<T extends PermBuilder>
{
    /**
     * This method should return a fresh unadulterated PermBuilder instance with no default values.
     * @return a new instance of your PermBuilder.
     */
    T builder();

    /**
     * This method should return a PermBuilder instance with the username and PermNode set.
     * @return a new instance of your PermBuilder.
     */
    T builder(String username, String permNode);

    /**
     * @return The default IContext instance of this object for this Implementation.
     */
    IContext getDefaultContext(EntityPlayer player);

    /**
     * @return The default IContext instance of this object for this Implementation.
     */
    IContext getDefaultContext(TileEntity te);

    /**
     * @return The default IContext instance of this object for this Implementation.
     */
    IContext getDefaultContext(ILocation te);

    /**
     * @return The default IContext instance of this object for this Implementation.
     */
    IContext getDefaultContext(Entity entity);

    /**
     * At the very least, this method should return an anonymous instance of IContext.
     * This method should NEVER return null.
     * @return The default IContext instance of this object for this Implementation.
     */
    IContext getDefaultContext(Object entity);

    /**
     * This is where permissions are registerred with their default value.
     * @param perms
     */
    void registerPermissions(Map<String, RegisteredPermValue> perms);
}
