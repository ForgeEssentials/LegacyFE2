package com.forgeessentials.util.data.api;

import com.forgeessentials.util.data.AbstractDataDriver;
import com.forgeessentials.util.data.DBConnector;
import com.forgeessentials.util.data.api.IDataDriver.EnumDriverType;

public abstract class DataStorageManager
{

	public static IStorageManager	manager;

	/**
	 * Should only be done before the server starts. May override existing
	 * Driver types.
	 * @param name Name to be used in configs
	 * @param c
	 */
	public static void registerDriver(String name, Class<? extends AbstractDataDriver> c)
	{
		manager.registerDriver(name, c);
	}

	public static AbstractDataDriver getReccomendedDriver()
	{
		return manager.getReccomendedDriver();
	}

	public static AbstractDataDriver getDriverOfType(EnumDriverType type)
	{
		return manager.getDriverOfType(type);
	}

	public static void registerSaveableType(Class<? extends ITypeInfo> infoType, ClassContainer type)
	{
		manager.registerSaveableClass(infoType, type);
	}

	public static void registerSaveableType(ClassContainer type)
	{
		manager.registerSaveableClass(type);
	}

	/**
	 * Alias that constructs a ClassContainer with no parameters.
	 */
	public static void registerSaveableType(Class<?> type)
	{
		manager.registerSaveableClass(new ClassContainer(type));
	}

	public static ITypeInfo<?> getInfoForType(ClassContainer type)
	{
		return manager.getInfoForType(type);
	}

	public static TypeData getDataForType(ClassContainer type)
	{
		return manager.getDataForType(type);
	}

	public static TypeData getDataForObject(ClassContainer container, Object obj)
	{
		return manager.getDataForObject(container, obj);
	}

	public static DBConnector getCoreDBConnector()
	{
		return manager.getCoreDBConnector();
	}
}
