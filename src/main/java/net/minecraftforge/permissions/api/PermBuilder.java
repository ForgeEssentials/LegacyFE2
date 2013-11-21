package net.minecraftforge.permissions.api;

import net.minecraftforge.permissions.api.context.IContext;

public interface PermBuilder
{
    boolean check();

    PermBuilder setUserName(String name);

    PermBuilder setPermNode(String node);

    PermBuilder setTargetContext(IContext context);

    PermBuilder setUserContext(IContext context);
}