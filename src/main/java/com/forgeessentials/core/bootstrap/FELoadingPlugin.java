package com.forgeessentials.core.bootstrap;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;

@MCVersion(value = "1.6.2")
public class FELoadingPlugin implements IFMLLoadingPlugin, IFMLCallHook{

	@Override
	public String[] getLibraryRequestClass() {
		// FML doesnt bother, why should we? USE THE INSTALLER.
		return null;
	}

	@Override
	public String[] getASMTransformerClass() {
		// future asm injector
		return null;
	}

	@Override
	public String getModContainerClass() {
		// TODO Auto-generated method stub
		return "com.forgeessentials.core.ForgeEssentials";
	}

	@Override
	public String getSetupClass() {
		// TODO Auto-generated method stub
		return "com.forgeessentials.core.bootstrap.FEClassLoader";
	}

	@Override
	public void injectData(Map<String, Object> data) {
		// TODO Auto-generated method stub

	}

	@Override
	public Void call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
