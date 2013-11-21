package com.forgeessentials.core.modules;

import com.forgeessentials.core.modules.internal.ModuleLoaderImpl;
import net.minecraft.server.MinecraftServer;

public interface ModuleLoader
{
    /**
     * reloads the configs of all possible modules.
     */
    void reloadAllModules();

    /**
     * Returns null if the specified module does not exist.
     * @return NULL if the specified module does not exist.
     */
    ModuleContainer getModule(String moduleName);

    boolean doesModuleExist(String moduleName);

    boolean isModuleEnabled(String moduleName);

    /**
     * Enables the specified module if it exists and can be enabled.
     * @param moduleName
     * @param server
     */
    void enableModule(String moduleName, MinecraftServer server);

    /**
     * Disables the specified module if it exists and is currently enabled.
     * @param moduleName
     */
    void disableModule(String moduleName);

    /**
     * Reloads the configs for the specified module if it exists.
     * This happens regardless if the module is enabled or not.
     * @param moduleName
     */
    void reloadModule(String moduleName);
}
