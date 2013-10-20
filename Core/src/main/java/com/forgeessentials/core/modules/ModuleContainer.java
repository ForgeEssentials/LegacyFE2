package com.forgeessentials.core.modules;

import com.forgeessentials.util.FeLog;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.discovery.ASMDataTable;

import java.lang.annotation.Annotation;

public class ModuleContainer
{
    public final String                     name;
    public final IFEModule                  module;
    public final Class<? extends IFEModule> clazz;
    public final IFEModule.LoadMe           annotation;
    public final boolean                    loadOnClient;
    public final boolean                    loadOnServer;
    public final boolean                    loaded;

    public ModuleState state = ModuleState.UNLOADED;

    public ModuleContainer(ASMDataTable.ASMData asmData)
    {
        try
        {
            FeLog.info("Module class:  " + asmData.getClassName());

            // the classCast exception is caught later
            clazz = (Class<? extends IFEModule>) Class.forName(asmData.getClassName());

            annotation = clazz.getAnnotation(IFEModule.LoadMe.class);
            name = annotation.name();
            loadOnClient = annotation.loadOnClient();
            loadOnServer = annotation.loadOnServer();

            loaded = (FMLCommonHandler.instance().getSide().isClient() && loadOnClient) || (FMLCommonHandler.instance().getSide().isServer() && loadOnServer);

            if (loaded)
            {
                module = (IFEModule) clazz.newInstance();
                state = ModuleState.LOADED;
            }
            else
            {
                module = null;
            }
        }
        // all the catch statements
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException(asmData.getClassName() + " does not exist!");
        }
        catch (ClassCastException e)
        {
            throw new RuntimeException(asmData.getClassName() + " must implement IFEModule!");
        }
        catch (NoClassDefFoundError e)
        {
            throw new RuntimeException(asmData.getClassName() + " does not exist!");
        }
        catch (InstantiationException e)
        {
            throw new RuntimeException(asmData.getClassName() + " must have an empty public constructor!");
        }
        catch (IllegalAccessException e)
        {
            throw new RuntimeException(asmData.getClassName() + " must have an empty public constructor!");
        }
    }
}
