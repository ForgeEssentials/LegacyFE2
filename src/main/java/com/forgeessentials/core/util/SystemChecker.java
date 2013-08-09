package com.forgeessentials.core.util;

import com.forgeessentials.util.EnumDBType;
import com.forgeessentials.util.OutputHandler;

import cpw.mods.fml.common.Loader;

public class SystemChecker {
	public static void run() {
		// Check for BukkitForge
		if (Loader.isModLoaded("BukkitForge")) {
			OutputHandler.felog
					.severe("Sanity check failed: Detected BukkitForge, bad things may happen, proceed at your own risk.");
		}

		// Check for Fihgu's mods
		else if (Loader.isModLoaded("fihgu's Core Mod")) {
			OutputHandler.felog
					.severe("Sanity check failed: Detected Fihgu's mods, bad things may happen, proceed at your own risk.");

		}

		else {
			// Check for MCPC+ or LavaBukkit
			try {
				Class.forName("org.bukkit.craftbukkit.Main");
				OutputHandler.felog
						.severe("Sanity check failed: Detected a ForgeBukkit server implementation, bad things may happen, proceed at your own risk.");
			} catch (ClassNotFoundException e) {
				// Safe!
			}
		}
		// Now check if libs are present
		try {
			Class.forName("com.sk89q.worldedit.WorldEdit");
			Class.forName("com.ForgeEssentials.lib.mcstats.Metrics");
			Class.forName("org.pircbotx.PircBotX");
			for (EnumDBType db : EnumDBType.values()) {
				db.loadClass();
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(
					"[ForgeEssentials] One or more libraries could not be found. Ensure they are present in the libraries folder.");
		}
	}

}
