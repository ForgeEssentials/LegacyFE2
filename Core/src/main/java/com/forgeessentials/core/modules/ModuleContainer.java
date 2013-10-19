package com.forgeessentials.core.modules;

import com.forgeessentials.util.FeLog;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.discovery.ASMDataTable;

import java.lang.annotation.Annotation;

public class ModuleContainer
{
    public final String                     name;
    public final IModule                    module;
    public final Class<? extends IModule>   clazz;
    public final IModule.LoadMe             annotation;
    public final boolean                    loadOnClient, loadOnServer;
    public final boolean load;

    public ModuleContainer(ASMDataTable.ASMData asmData) throws Exception
    {
        FeLog.info("Module class:  " + asmData.getClassName());
        Class c = Class.forName(asmData.getClassName());
        if (!IModule.class.isAssignableFrom(c))
            throw new Exception(asmData.getClassName() + " doesn't implement IModule!");

        clazz = c;

        for (Annotation a : clazz.getAnnotations())
        {
            FeLog.info(a);
        }

        annotation = clazz.getAnnotation(IModule.LoadMe.class);
        name = annotation.name();
        loadOnClient = annotation.loadOnClient();
        loadOnServer = annotation.loadOnServer();

        load = (FMLCommonHandler.instance().getSide().isClient() && loadOnClient) || (FMLCommonHandler.instance().getSide().isServer() && loadOnServer);

        module = load ? (IModule) clazz.newInstance() : null;
    }
}
