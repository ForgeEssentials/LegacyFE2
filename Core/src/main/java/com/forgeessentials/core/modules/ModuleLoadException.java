package com.forgeessentials.core.modules;

/**
 * <p>
 *     This Exception is designed to be thrown during the execution of any method in a class implementing IFEModule.
 *     Throwing the exception will cause the module to quietly crash, and will stop any subsequent methods from bieng called on the class.
 *     The only exception is the Disable() method which will bve called in order to ensure that no garbage remains.
 * </>
 */
public class ModuleLoadException extends RuntimeException
{
    public ModuleLoadException(String reason)
    {
        super(reason);
    }

    public ModuleLoadException(Throwable error)
    {
        super(error);
    }

    public ModuleLoadException(String reason, Throwable error)
    {
        super(reason, error);
    }
}
