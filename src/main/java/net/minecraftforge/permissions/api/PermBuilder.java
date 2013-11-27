package net.minecraftforge.permissions.api;

import net.minecraftforge.permissions.api.context.IContext;

public interface PermBuilder<T extends PermBuilder>
{
    boolean check();

    T setUserName(String name);

    T setPermNode(String node);

    T setTargetContext(IContext context);

    T setUserContext(IContext context);
}