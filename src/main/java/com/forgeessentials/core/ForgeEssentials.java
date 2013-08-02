package com.forgeessentials.core;

import java.io.File;

import com.forgeessentials.core.bootstrap.FETweaker;
import com.forgeessentials.core.bootstrap.modulelauncher.FEComponent;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.network.NetworkMod;

/**
 * Main FE class, only triggers module launching.
 * Standalone loading code. DO NOT DO ANY CORE MODULE STUFFS HERE!
 * All other misc features are in CoreModule
 * @author luacs1998
 *
 */

@Mod(modid = "ForgeEssentials", name = "Forge Essentials", version = "2.0pre1")
@NetworkMod(clientSideRequired = false, serverSideRequired = false)
public class ForgeEssentials {
	
	@Instance
	public static ForgeEssentials instance;
	
	public static File FEDIR = FETweaker.getFEDir();

}
