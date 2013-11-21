package net.minecraftforge.permissions.api;

import net.minecraftforge.permissions.api.context.IContext;

public interface PermBuilder<T extends PermBuilder>
{
    public boolean check();

    public T setUserName(String name);

    public T setPermNode(String node);

    public T setTargetContext(IContext context);

    public T setUserContext(IContext context);
}