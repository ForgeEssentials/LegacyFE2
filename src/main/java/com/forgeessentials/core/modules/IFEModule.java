package com.forgeessentials.core.modules;

import com.forgeessentials.core.modules.events.ModuleDisableEvent;
import com.forgeessentials.core.modules.events.ModuleEnableEvent;
import cpw.mods.fml.common.event.FMLEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.Configuration;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

/**
 * An interface to mark ForgeEssentials modules.
 * Any module that does not implement this interface will crash.
 * Any class that implements this interface, but is not annotated with the LoadMe annotation will not be loaded as a module.
 */
public interface IFEModule
{
    /**
     * A marker annotation that marks the class to be loaded my the FE Module Loader.
     * This annotation contains some properties that every module should have hardcoded.
     * other module properties are instead implemented as getters in the IModule interface.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface LoadMe
    {
        /**
         * The id of the module.
         * This should be unique, and not have spaces.
         */
        String name();

        /**
         * Defaults to true.
         */
        boolean loadOnClient() default true;

        /**
         * Defaults to true.
         */
        boolean loadOnServer() default true;
    }

    /**
     * This method should return the version of the Module.
     * Acepted formats are: "#.#.#.#", "#.#.#", and "#.#"
     * The returned string MAY NOT include arbitrary letters, symbols, or spaces.
     * his method will only be called once, immediately after the class is instantiated.
     */
    public abstract String getVersion();

    /**
     * Load any configuration here.
     * This is executed before enable method when a minecraft starts, and it is executed later on when a module is reloaded.
     *
     * @param file The config file you should use.
     * @param reloading TRUE if this method is being executed for reload purposes. FALSE if this is the initial loading of the module.
     */
    public void doConfig(File file, boolean reloading);

    /**
     * Use this to initialize the module.
     * This will never be called twice in a row. The disable method will always be called between consecutive calls to this method. Called on server start.
     *
     * @param event
     * @see cpw.mods.fml.common.event.FMLServerStartingEvent
     */
    public void enable(ModuleEnableEvent event);

    /**
     * Called when the server is stopping.
     * This will never be called twice in a row. The enable method will always be called between consecutive calls to this method. Called on server stop.
     *
     * @param event
     * @see cpw.mods.fml.common.event.FMLServerStoppingEvent
     */
    public void disable(ModuleDisableEvent event);

    /**
     *
     * @param container
     * @param messages
     */
    public void receiveInterModMessages(ModuleContainer container, List<FMLInterModComms.IMCMessage> messages);
}
