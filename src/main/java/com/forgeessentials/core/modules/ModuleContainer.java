package com.forgeessentials.core.modules;

public interface ModuleContainer
{

    /**
     * @return The modules ID.
     */
    String getModuleName();

    /**
     * @return The modules version as dictated by { @link com.forgeessentials.core.modules.IFeModule.getVersion() }
     */
    String getModuleVersion();

    /**
     * This will return null until the modules state is { @link com.forgeessentials.core.modules.ModuleState.INSTANTIATED}
     *
     * @return an instance of the module.
     */
    IFEModule getModuleInstance();

    /**
     * @return the class object of this module.
     */
    Class<? extends IFEModule> getModuleClass();

    boolean loadsOnClient();

    boolean loadsOnServer();

    /**
     * This is evaluated by the checking if the current run is a Server or a Client, and whether the module should be loaded on.
     *
     * @return Whether or not this module will be loaded.
     */
    boolean shouldBeLoaded();

    ModuleState getModuleState();
}
