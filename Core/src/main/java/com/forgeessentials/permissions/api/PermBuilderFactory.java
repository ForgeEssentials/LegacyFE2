package com.forgeessentials.permissions.api;

public interface PermBuilderFactory
{
    PermBuilder builder();

    PermBuilder builder(String username, String permNode);
}
