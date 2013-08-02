package com.forgeessentials.core.bootstrap;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Logger;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class FETweaker implements ITweaker {

	private static File FEDIR;
	private Logger log;
	
	@Override
	public void acceptOptions(List<String> arg0, File arg1, File arg2,
			String arg3) {
		this.FEDIR = new File(arg1, "ForgeEssentials/");
		if (!FEDIR.exists()){
			FEDIR.mkdirs();
		}
		log = Logger.getLogger("FEInjector");
		log.info("[ForgeEssentials] Tweaker registered, loading modules");
	}

	@Override
	public String[] getLaunchArguments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLaunchTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void injectIntoClassLoader(LaunchClassLoader classloader) {
		// add modules and libs to class path.. DON'T TOUCH ME!
		File lib = new File(FEDIR, "lib/");
		if (!lib.exists()){
			lib.mkdirs();
		}
		for (File f : lib.listFiles()){
			if (f != null){
			try {
				classloader.addURL(f.toURI().toURL());
				log.info("[ForgeEssentials] Loaded library file " + f.getAbsolutePath());
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
				log.severe("[ForgeEssentials] Could not add module file " + f.getAbsolutePath() + ", there may be a class loading problem.");
			}
			}
		}
		log.info("[ForgeEssentials] Loaded " + module.listFiles().length + " modules");

	}
	public static File getFEDir(){
		return FEDIR;
	}

}
