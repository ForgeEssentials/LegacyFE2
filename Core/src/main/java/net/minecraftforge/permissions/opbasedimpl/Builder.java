package net.minecraftforge.permissions.opbasedimpl;

import net.minecraftforge.permissions.api.PermBuilder;
import net.minecraftforge.permissions.api.context.IContext;

public class Builder implements PermBuilder
{
    IContext user, target;
    String username, node;

    @Override
    public boolean check()
    {
        // TODO
        return false;
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
}
