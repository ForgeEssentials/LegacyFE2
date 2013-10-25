package com.forgeessentials.core.modules.FMLevents;

import com.forgeessentials.core.modules.IFEModule;
import cpw.mods.fml.common.event.*;

import java.util.HashSet;

public class FMLEventHandler
{
    public static void checkInterfaces(IFEModule module)
    {
        if (module instanceof IPreInit)             IPreInitSet.add(                (IPreInit)              module);
        if (module instanceof IInit)                IInitSet.add(                   (IInit)                 module);
        if (module instanceof IPostInit)            IPostSet.add(                   (IPostInit)             module);
        if (module instanceof IServerAboutToStart)  IServerAboutToStartSet.add(     (IServerAboutToStart)   module);
        if (module instanceof IServerStartingEvent) IServerStartingEventSet.add(    (IServerStartingEvent)  module);
        if (module instanceof IServerStartedEvent)  IServerStartedEventSet.add(     (IServerStartedEvent)   module);
        if (module instanceof IServerStoppingEvent) IServerStoppingEventSet.add(    (IServerStoppingEvent)  module);
        if (module instanceof IServerStoppedEvent)  IServerStoppedEventSet.add(     (IServerStoppedEvent)   module);
        if (module instanceof IIMCEvent)            IIMCEventSet.add(               (IIMCEvent)             module);
    }

    private static HashSet<IPreInit> IPreInitSet = new HashSet<IPreInit>();
    public static void passEvent(FMLPreInitializationEvent event)
    {
        for (IPreInit module : IPreInitSet) module.fmlEvent(event);
    }

    private static HashSet<IInit> IInitSet = new HashSet<IInit>();
    public static void passEvent(FMLInitializationEvent event)
    {
        for (IInit module : IInitSet) module.fmlEvent(event);
    }

    private static HashSet<IPostInit> IPostSet = new HashSet<IPostInit>();
    public static void passEvent(FMLPostInitializationEvent event)
    {
        for (IPostInit module : IPostSet) module.fmlEvent(event);
    }

    private static HashSet<IServerAboutToStart> IServerAboutToStartSet = new HashSet<IServerAboutToStart>();
    public static void passEvent(FMLServerAboutToStartEvent event)
    {
        for (IServerAboutToStart module : IServerAboutToStartSet) module.fmlEvent(event);
    }

    private static HashSet<IServerStartingEvent> IServerStartingEventSet = new HashSet<IServerStartingEvent>();
    public static void passEvent(FMLServerStartingEvent event)
    {
        for (IServerStartingEvent module : IServerStartingEventSet) module.fmlEvent(event);
    }

    private static HashSet<IServerStartedEvent> IServerStartedEventSet = new HashSet<IServerStartedEvent>();
    public static void passEvent(FMLServerStartedEvent event)
    {
        for (IServerStartedEvent module : IServerStartedEventSet) module.fmlEvent(event);
    }

    private static HashSet<IServerStoppingEvent> IServerStoppingEventSet = new HashSet<IServerStoppingEvent>();
    public static void passEvent(FMLServerStoppingEvent event)
    {
        for (IServerStoppingEvent module : IServerStoppingEventSet) module.fmlEvent(event);
    }

    private static HashSet<IServerStoppedEvent> IServerStoppedEventSet = new HashSet<IServerStoppedEvent>();
    public static void passEvent(FMLServerStoppedEvent event)
    {
        for (IServerStoppedEvent module : IServerStoppedEventSet) module.fmlEvent(event);
    }

    private static HashSet<IIMCEvent> IIMCEventSet = new HashSet<IIMCEvent>();
    public static void passEvent(FMLInterModComms.IMCEvent event)
    {
        for (IIMCEvent module : IIMCEventSet) module.fmlEvent(event);
    }
}
