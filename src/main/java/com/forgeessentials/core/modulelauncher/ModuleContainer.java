package com.forgeessentials.core.modulelauncher;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;

import com.forgeessentials.core.ForgeEssentials;
import com.forgeessentials.core.modulelauncher.FEComponent.Container;
import com.forgeessentials.core.modulelauncher.FEComponent.DummyConfig;
import com.forgeessentials.core.modulelauncher.FEComponent.FEModuleEventHandler;
import com.forgeessentials.core.modulelauncher.FEComponent.Instance;
import com.forgeessentials.core.modulelauncher.FEComponent.ModuleDir;
import com.forgeessentials.core.modulelauncher.util.CallableMap;
import com.forgeessentials.util.OutputHandler;
import com.google.common.base.Throwables;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.discovery.ASMDataTable.ASMData;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

public class ModuleContainer implements Comparable{
	protected static HashSet<Class>				modClasses	= new HashSet<Class>();

	public Object								module, mod;
	private BaseConfig					configObj;
	private Class<? extends BaseConfig>	configClass;

	// methods..
	private String								preinit, init, postinit, serverinit, serverpostinit, serverstopping, serverstopped, reload;

	// fields
	private String								instance, container, config, moduleDir;

	// other vars..
	public final String							className;
	public final String							name;
	public final boolean						isCore;
	public boolean								isLoadable	= true;
	protected boolean							doesOverride;
	private Class parentMod;
	private boolean serverOnly;

	@SuppressWarnings("unchecked")
	public ModuleContainer(ASMData data)
	{
		// get the class....
		Class c = null;
		className = data.getClassName();

		try
		{
			c = Class.forName(className);
		}
		catch (Throwable e)
		{
			OutputHandler.felog.info("Error trying to load " + data.getClassName() + " as a FEModule!");
			e.printStackTrace();

			isCore = false;
			name = "INVALID-MODULE";
			return;
		}

		// checks original FEModule annotation.
		if (!c.isAnnotationPresent(FEComponent.class))
			throw new IllegalArgumentException(c.getName() + " doesn't have the @FEModule annotation!");
		FEComponent annot = (FEComponent) c.getAnnotation(FEComponent.class);
		if (annot == null)
			throw new IllegalArgumentException(c.getName() + " doesn't have the @FEModule annotation!");
		name = annot.name();
		isCore = annot.isCore();
		configClass = annot.configClass();
		serverOnly = annot.serverOnly();
		
		if (serverOnly && FMLCommonHandler.instance().getEffectiveSide().isClient()){
			isLoadable = false;
			OutputHandler.felog.warning("The module " + name + " has been coded to work on the dedicated server only. It will not be loaded.");
		}

		// check method annotation. they are all optional...
		Class[] params;
		for (Method m : c.getDeclaredMethods())
		{
			if (m.isAnnotationPresent(FEModuleEventHandler.class))
			{
				if (preinit != null)
					throw new RuntimeException("Only one class may be marked as FEModuleEventHandler");
				params = m.getParameterTypes();
				if (params.length != 1)
					throw new RuntimeException(m + " may only have 1 argument!");
				if (params[0].equals(FMLPreInitializationEvent.class))
					preinit = m.getName();
				else if (params[0].equals(FMLInitializationEvent.class))
					init = m.getName();
				else if (params[0].equals(FMLPostInitializationEvent.class))
					postinit = m.getName();
				else if (params[0].equals(FMLServerStartingEvent.class))
					serverinit = m.getName();
				else if (params[0].equals(FMLServerStartedEvent.class))
					serverpostinit = m.getName();
				else if (params[0].equals(FMLServerStoppingEvent.class))
					serverstopping = m.getName();
				else if (params[0].equals(FMLServerStoppedEvent.class))
					serverstopped = m.getName();
				m.setAccessible(true);

				
			}
		}

		// collect field annotations... these are also optional.
		for (Field f : c.getDeclaredFields())
		{
			if (f.isAnnotationPresent(Instance.class))
			{
				if (instance != null)
					throw new RuntimeException("Only one field may be marked as Instance");
				f.setAccessible(true);
				instance = f.getName();
			}
			else if (f.isAnnotationPresent(Container.class))
			{
				if (container != null)
					throw new RuntimeException("Only one field may be marked as Container");
				if (f.getType().equals(ModuleContainer.class))
					throw new RuntimeException("This field must have the type ModuleContainer!");
				f.setAccessible(true);
				container = f.getName();
			}
			else if (f.isAnnotationPresent(ModuleDir.class))
			{
				if (moduleDir != null)
					throw new RuntimeException("Only one field may be marked as ModuleDir");
				if (!File.class.isAssignableFrom(f.getType()))
					throw new RuntimeException("This field must be the type File!");
				f.setAccessible(true);
				moduleDir = f.getName();
			}
		}
	}

	protected void createAndPopulate()
	{
		Field f;
		Class c;
		// instantiate.
		try
		{
			c = Class.forName(className);
			module = c.newInstance();
		}
		catch (Throwable e)
		{
			OutputHandler.felog.warning(name + " could not be instantiated. FE will not load this module.");
			e.printStackTrace();
			isLoadable = false;
			return;
		}
		if(serverOnly){
			isLoadable = false;
			return;
		}

		// now for the fields...
		try
		{
			if (instance != null)
			{
				f = c.getDeclaredField(instance);
				f.setAccessible(true);
				f.set(module, module);
			}

			if (container != null)
			{
				f = c.getDeclaredField(container);
				f.setAccessible(true);
				f.set(module, this);
			}

			if (moduleDir != null)
			{
				File file = new File(ForgeEssentials.FEDIR, name);
				file.mkdirs();

				f = c.getDeclaredField(moduleDir);
				f.setAccessible(true);
				f.set(module, file);
			}
		}
		catch (Throwable e)
		{
			OutputHandler.felog.info("Error populating fields of " + name);
			Throwables.propagate(e);
		}

		// now for the config..
		if (configClass.equals(DummyConfig.class))
		{
			OutputHandler.felog.info("No config specified for " + name);
			configObj = null;
			return;
		}

		try
		{
			configObj = configClass.getConstructor(File.class).newInstance(new File(ForgeEssentials.FEDIR, name + "/config.cfg"));

			if (config != null)
			{
				f = c.getDeclaredField(config);
				f.setAccessible(true);
				f.set(module, configObj);
			}

		}
		catch (Throwable e)
		{
			OutputHandler.felog.info("Error Instantiating or populating config for " + name);
			Throwables.propagate(e);
		}
	}

	// make the methods to run the events now...

	@SuppressWarnings("unchecked")
	public void runPreInit(FMLPreInitializationEvent fmlEvent, CallableMap map)
	{
		if (!isLoadable || preinit == null)
			return;

		try
		{
			Class c = Class.forName(className);
			Method m = c.getDeclaredMethod(preinit, new Class[] { FMLPreInitializationEvent.class });
			m.invoke(module, fmlEvent);
		}
		catch (Throwable e)
		{
			OutputHandler.felog.severe("Error while invoking preInit event for " + name);
			Throwables.propagate(e);
		}
	}

	@SuppressWarnings("unchecked")
	public void runInit(FMLInitializationEvent fmlEvent)
	{
		if (!isLoadable || init == null)
			return;

		try
		{
			Class c = Class.forName(className);
			Method m = c.getDeclaredMethod(init, new Class[] { FMLInitializationEvent.class });
			m.invoke(module, fmlEvent);
		}
		catch (Throwable e)
		{
			OutputHandler.felog.info("Error while invoking Init event for " + name);
			Throwables.propagate(e);
		}
	}

	@SuppressWarnings("unchecked")
	public void runPostInit(FMLPostInitializationEvent fmlEvent)
	{
		if (!isLoadable || postinit == null)
			return;

		try
		{
			Class c = Class.forName(className);
			Method m = c.getDeclaredMethod(postinit, new Class[]
			{ FMLPostInitializationEvent.class });
			m.invoke(module, fmlEvent);
		}
		catch (Throwable e)
		{
			OutputHandler.felog.info("Error while invoking PostInit event for " + name);
			Throwables.propagate(e);
		}
	}

	@SuppressWarnings("unchecked")
	public void runServerInit(FMLServerStartingEvent fmlEvent)
	{
		if (!isLoadable || serverinit == null)
			return;

		try
		{
			Class c = Class.forName(className);
			Method m = c.getDeclaredMethod(serverinit, new Class[]
			{ FMLServerStartingEvent.class });
			m.invoke(module, fmlEvent);
		}
		catch (Throwable e)
		{
			OutputHandler.felog.info("Error while invoking ServerInit event for " + name);
			Throwables.propagate(e);
		}
	}

	@SuppressWarnings("unchecked")
	public void runServerPostInit(FMLServerStartedEvent fmlEvent)
	{
		if (!isLoadable || serverpostinit == null)
			return;

		try
		{
			Class c = Class.forName(className);
			Method m = c.getDeclaredMethod(serverpostinit, new Class[]
			{ FMLServerStartedEvent.class });
			m.invoke(module, fmlEvent);
		}
		catch (Throwable e)
		{
			OutputHandler.felog.info("Error while invoking ServerPostInit event for " + name);
			Throwables.propagate(e);
		}
	}

	@SuppressWarnings("unchecked")
	public void runServerStop(FMLServerStoppingEvent fmlEvent)
	{
		if (!isLoadable || serverstopping == null)
			return;

		try
		{
			Class c = Class.forName(className);
			Method m = c.getDeclaredMethod(serverstopping, new Class[]
			{ FMLServerStoppingEvent.class });
			m.invoke(module, fmlEvent);
		}
		catch (Throwable e)
		{
			OutputHandler.felog.info("Error while invoking ServerStop event for " + name);
			Throwables.propagate(e);
		}
	
	}
	
	@SuppressWarnings("unchecked")
	public void runServerStopped(FMLServerStoppedEvent fmlEvent)
	{
		if (!isLoadable || serverstopping == null)
			return;

		try
		{
			Class c = Class.forName(className);
			Method m = c.getDeclaredMethod(serverstopping, new Class[]
			{ FMLServerStoppedEvent.class });
			m.invoke(module, fmlEvent);
		}
		catch (Throwable e)
		{
			OutputHandler.felog.info("Error while invoking ServerStopped event for " + name);
			Throwables.propagate(e);
		}
	
	}
	

	public File getModuleDir()
	{
		return new File(ForgeEssentials.FEDIR, name);
	}

	/**
	 * May be null if the module has no config
	 * @return
	 */
	public BaseConfig getConfig()
	{
		return configObj;
	}

	@Override
	public int compareTo(Object o)
	{
		ModuleContainer container = (ModuleContainer) o;

		if (equals(container))
			return 0;

		if (isCore && !container.isCore)
			return 1;
		else if (!isCore && container.isCore)
			return -1;

		return name.compareTo(container.name);
	}

	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof ModuleContainer))
			return false;

		ModuleContainer c = (ModuleContainer) o;

		return isCore == c.isCore && name.equals(c.name) && className.equals(c.className);
	}
}