package com.forgeessentials.permissions.api;

public interface PermBuilderFactory<T extends PermBuilder>
{
    T builder();

    T builder(String username, String permNode);
}
