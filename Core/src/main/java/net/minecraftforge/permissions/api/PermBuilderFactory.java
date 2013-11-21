package net.minecraftforge.permissions.api;

import com.google.gson.JsonObject;
import net.minecraft.world.World;
import net.minecraftforge.permissions.api.context.IContext;
import net.minecraft.dispenser.ILocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import java.util.List;
import java.util.Map;

public interface PermBuilderFactory<T extends PermBuilder>
{
    /**
     * This method should return a fresh unadulterated PermBuilder instance with no default values.
     * @return a new instance of your PermBuilder.
     */
    public T builder();

    /**
     * This method should return a PermBuilder instance with the username and PermNode set.
     * @return a new instance of your PermBuilder.
     */
    public T builder(String username, String permNode);

    /**
     * @return The default IContext instance of this object for this Implementation.
     */
    public IContext getDefaultContext(EntityPlayer player);

    /**
     * @return The default IContext instance of this object for this Implementation.
     */
    public IContext getDefaultContext(TileEntity te);

    /**
     * @return The default IContext instance of this object for this Implementation.
     */
    public IContext getDefaultContext(ILocation loc);

    /**
     * @return The default IContext instance of this object for this Implementation.
     */
    public IContext getDefaultContext(Entity entity);

    /**
     * @return The default IContext instance of this object for this Implementation.
     */
    public IContext getDefaultContext(World world);

    /**
     * @return A IContext signifying the Server as a whole.
     */
    public IContext getGlobalContext();

    /**
     * At the very least, this method should return an anonymous instance of IContext.
     * This method should NEVER return null.
     * @return The default IContext instance of this object for this Implementation.
     */
    public IContext getDefaultContext(Object whoKnows);

    /**
     * This is where permissions are registered with their default value.
     * @param perms
     */
    public void registerPermissions(List<PermReg> perms);

    public static class PermReg
    {
        public final String              key;
        public final RegisteredPermValue role;
        public final JsonObject          data;

        public PermReg(String key, RegisteredPermValue value, JsonObject obj)
        {
            this.key = key;
            this.role = value;
            this.data = obj;
        }
    }
}
