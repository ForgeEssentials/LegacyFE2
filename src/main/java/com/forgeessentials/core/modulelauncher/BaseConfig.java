package com.forgeessentials.core.modulelauncher;

import java.io.File;

public abstract class BaseConfig {
	
	protected File		file;
	protected boolean	genrate;

	public BaseConfig(File file)
	{
		this.file = file;
	}

	public void setGenerate(boolean generate)
	{
		genrate = generate;
	}

	/**
	 * this should check the generate boolean and do stuff accordingly. it
	 * should either load, or generate.
	 */
	public abstract void save();
	
	public abstract void load();

	public File getFile()
	{
		return file;
	}

}