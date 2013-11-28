package com.forgeessentials.core.classloading;

import java.io.File;
import java.util.List;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class FETweaker implements ITweaker {

	@Override
	public void acceptOptions(List<String> args, File gameDir, File assetsDir,
			String profile) {
		}

	@Override
	public void injectIntoClassLoader(LaunchClassLoader classLoader) {
		classLoader.registerTransformer("com.forgeessentials.core.transformers.CoreAccessTransformer");
		}

	@Override
    public String getLaunchTarget()
    {
        return "";
    }

    @Override
    public String[] getLaunchArguments()
    {
        return new String[0];
    }

}
