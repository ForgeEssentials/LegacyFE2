package com.forgeessentials.core.bootstrap;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Map;

import net.minecraft.launchwrapper.LaunchClassLoader;
import cpw.mods.fml.relauncher.IFMLCallHook;

public class FEClassLoader implements IFMLCallHook {
	
	private LaunchClassLoader classloader;
	public static File FEDIR;

	@Override
	public Void call() throws Exception {
		// add modules and libs to class path.. DON'T TOUCH ME!
				File lib = new File(FEDIR, "lib/");
				if (!lib.exists()){
					lib.mkdirs();
				}
				for (File f : lib.listFiles()){
					if (f != null){
					try {
						classloader.addURL(f.toURI().toURL());
						System.out.println("[ForgeEssentials] Loaded library file " + f.getAbsolutePath());
					} catch (MalformedURLException e) {
						throw new RuntimeException("Could not add library file " + f.getAbsolutePath() + ", there may be a classloading problem.");
					}
					}
				}

				File module = new File(FEDIR, "modules/");
				if (!module.exists()){
					module.mkdirs();
				}
				for (File f : module.listFiles()){
					if (f != null){
					try{
						classloader.addURL(f.toURI().toURL());
					} catch(MalformedURLException e){
						System.err.println("[ForgeEssentials] Could not add module file " + f.getAbsolutePath() + ", there may be a class loading problem.");
					}
					}
				}
				System.out.println("[ForgeEssentials] Loaded " + module.listFiles().length + " modules");

			
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		if (data.containsKey("classLoader") && data.get("classLoader") != null)
        {
            classloader = (LaunchClassLoader) data.get("classLoader");
        }
		if (data.containsKey("mclocation") && data.get("mclocation") != null)
        {
            FEDIR = new File(data.get("mclocation") + "/ForgeEssentials/");
        }
	}

}
