package com.forgeessentials.core.modules;

import cpw.mods.fml.common.event.FMLEvent;
import net.minecraftforge.common.Configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
     * It is reccomended that the
     */
    public abstract String getVersion();

    /**
     * Use this method to get all FML state events.
     * @param event Can be any of the FML events.
     */
    public void fmlEvent(FMLEvent event);

    /**
     * Do your configuration here.
     * Use your ID as a root category.
     * @param configuration The config you should use.
     */
    public void doConfig(Configuration configuration);
}
