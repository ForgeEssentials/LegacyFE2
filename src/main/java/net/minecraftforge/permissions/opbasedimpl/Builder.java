package net.minecraftforge.permissions.opbasedimpl;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraftforge.permissions.api.PermBuilder;
import net.minecraftforge.permissions.api.context.IContext;

public class Builder implements PermBuilder
{
    IContext user, target;
    String username, node;

    @Override
    public boolean check()
    {
        if (OpPermFactory.deniedPerms.contains(node))
            return false;
        else if (OpPermFactory.allowedPerms.contains(node))
            return true;
        else if (OpPermFactory.opPerms.contains(node))
            return isOp(username);
        else
            throw new UnregisterredPermissionException(node);
    }

    private static boolean isOp(String username)
    {
        MinecraftServer server = FMLCommonHandler.instance().getSidedDelegate().getServer();

        // SP and LAN
        if (server.isSinglePlayer())
        {
            if (server instanceof IntegratedServer)
                return server.getServerOwner().equalsIgnoreCase(username);
            else
                return server.getConfigurationManager().getOps().contains(username);
        }

        // SMP
        return server.getConfigurationManager().getOps().contains(username);
    }

    @Override
    public PermBuilder setUserName(String name)
    {
        username = name;
        return this;
    }

    @Override
    public PermBuilder setPermNode(String node)
    {
        this.node = node;
        return this;
    }

    @Override
    public PermBuilder setTargetContext(IContext context)
    {
        this.target = context;
        return this;
    }

    @Override
    public PermBuilder setUserContext(IContext context)
    {
        this.user = context;
        return this;
    }

    private static class UnregisterredPermissionException extends RuntimeException
    {
        public final String node;
        public UnregisterredPermissionException(String node)
        {
            super("Unregisterred Permission encountered! "+node);
            this.node = node;
        }
    }
}
