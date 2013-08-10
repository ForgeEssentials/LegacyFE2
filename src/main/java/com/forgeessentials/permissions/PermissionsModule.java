package com.forgeessentials.permissions;

import java.beans.EventHandler;
import java.io.File;

import net.minecraftforge.common.MinecraftForge;

import com.forgeessentials.api.APIRegistry;
import com.forgeessentials.api.permissions.RegGroup;
import com.forgeessentials.api.permissions.types.Zone;
import com.forgeessentials.core.modulelauncher.FEComponent;
import com.forgeessentials.core.modulelauncher.FEComponent.FEModuleEventHandler;
import com.forgeessentials.core.modulelauncher.FEComponent.ModuleDir;
import com.forgeessentials.util.data.AbstractDataDriver;
import com.forgeessentials.util.data.api.ClassContainer;
import com.forgeessentials.util.data.api.DataStorageManager;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

@FEComponent(name = "Permissions", version = "2.0pre1", isCore = true, configClass = ConfigPermissions.class)
public class PermissionsModule {
	public static SqlHelper				sql;

	
	public static ConfigPermissions		config;

	@ModuleDir
	public static File					permsFolder;

	protected static AbstractDataDriver	data;

	// permission registrations here...
	protected PermRegLoader				permLoader;
	private AutoPromoteManager			autoPromoteManager;

	@FEModuleEventHandler
	public void preLoad(FMLPreInitializationEvent e)
	{
		APIRegistry.zones = new ZoneHelper();
		APIRegistry.perms = new PermissionsHelper();// new one for new API

		MinecraftForge.EVENT_BUS.register(APIRegistry.zones);
		permLoader = new PermRegLoader(e.getCallableMap().getCallable(PermRegister.class));

		DataStorageManager.registerSaveableType(new ClassContainer(Zone.class));
	}

	@FEModuleEventHandler
	public void load(FMLInitializationEventt e)
	{
		// setup SQL
		sql = new SqlHelper(config);

		DataStorageManager.registerSaveableType(Zone.class);
		DataStorageManager.registerSaveableType(AutoPromote.class);

		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}

	@FEModuleEventHandler
	public void postload(FMLPostInitializationEvent e)
	{
		permLoader.loadAllPerms();
		permLoader.clearMethods();
		sql.putRegistrationPerms(permLoader.registerredPerms);

		PermissionsList list = new PermissionsList();
		if (list.shouldMake())
		{
			list.output(permLoader.perms);
		}
	}

	@FEModuleEventHandler
	public void serverStarting(FMLServerStartingEvent e)
	{
		// load zones...
		data = DataStorageManager.getReccomendedDriver();
		((ZoneHelper) APIRegistry.zones).loadZones();

		if (config.importBool)
		{
			sql.importPerms(config.importDir);
		}

		// init perms and vMC command overrides
		e.registerServerCommand(new CommandZone());
		e.registerServerCommand(new CommandFEPerm());
		e.registerServerCommand(new CommandAutoPromote());
		OverrideManager.regOverrides((FMLServerStartingEvent) e.getFMLEvent());

		autoPromoteManager = new AutoPromoteManager();
	}

	@PermRegister
	public static void registerPermissions(IPermRegisterEvent event)
	{
		event.registerPermissionLevel("ForgeEssentials.perm", RegGroup.OWNERS);
		event.registerPermissionLevel("ForgeEssentials.perm._ALL_", RegGroup.OWNERS, true);
		event.registerPermissionLevel("ForgeEssentials.permissions.zone.define", RegGroup.OWNERS);
		event.registerPermissionLevel("ForgeEssentials.permissions.zone.redefine._ALL_", RegGroup.OWNERS);
		event.registerPermissionLevel("ForgeEssentials.permissions.zone.remove._ALL_", RegGroup.OWNERS);
		event.registerPermissionLevel(TeleportCenter.BYPASS_COOLDOWN, RegGroup.OWNERS);
		event.registerPermissionLevel(TeleportCenter.BYPASS_COOLDOWN, RegGroup.OWNERS);

		event.registerPermissionLevel("ForgeEssentials.permissions.zone", RegGroup.ZONE_ADMINS);
		event.registerPermissionLevel("ForgeEssentials.permissions.zone.setparent", RegGroup.ZONE_ADMINS);
		event.registerPermissionLevel("ForgeEssentials.autoPromote", RegGroup.ZONE_ADMINS);

		event.registerPermissionLevel("ForgeEssentials.permissions.zone.info._ALL_", RegGroup.MEMBERS);
		event.registerPermissionLevel("ForgeEssentials.permissions.zone.list", RegGroup.MEMBERS);

		event.registerPermissionLevel("ForgeEssentials.BasicCommands.list", RegGroup.GUESTS);

		// somehow the perms stuff doesn't read this where they were, try here
		event.registerPermissionLevel("Minecraft.commands.ban", RegGroup.ZONE_ADMINS);
		event.registerPermissionLevel("Minecraft.commands.ban-ip", RegGroup.OWNERS);
		event.registerPermissionLevel("Minecraft.commands.debug", RegGroup.ZONE_ADMINS);
		event.registerPermissionLevel("Minecraft.commands.defaultgamemode", RegGroup.OWNERS);
		event.registerPermissionLevel("Minecraft.commands.deop", RegGroup.OWNERS);
		event.registerPermissionLevel("Minecraft.commands.difficulty", RegGroup.OWNERS);
		event.registerPermissionLevel("Minecraft.commands.gamerule", RegGroup.OWNERS);
		event.registerPermissionLevel("Minecraft.commands.kick", RegGroup.ZONE_ADMINS);
		event.registerPermissionLevel("Minecraft.commands.me", RegGroup.GUESTS);
		event.registerPermissionLevel("Minecraft.commands.op", RegGroup.OWNERS);
		event.registerPermissionLevel("Minecraft.commands.pardon", RegGroup.ZONE_ADMINS);
		event.registerPermissionLevel("Minecraft.commands.pardon-ip", RegGroup.ZONE_ADMINS);
		event.registerPermissionLevel("Minecraft.commands.publish", RegGroup.OWNERS);
		event.registerPermissionLevel("Minecraft.commands.save-all", RegGroup.ZONE_ADMINS);
		event.registerPermissionLevel("Minecraft.commands.save-on", RegGroup.ZONE_ADMINS);
		event.registerPermissionLevel("Minecraft.commands.save-off", RegGroup.ZONE_ADMINS);
		event.registerPermissionLevel("Minecraft.commands.say", RegGroup.OWNERS);
		event.registerPermissionLevel("Minecraft.commands.seed", RegGroup.MEMBERS);
		event.registerPermissionLevel("Minecraft.commands.stop", RegGroup.GUESTS);
		event.registerPermissionLevel("Minecraft.commands.whitelist", RegGroup.OWNERS);
		event.registerPermissionLevel("Minecraft.commands.xp", RegGroup.ZONE_ADMINS);
		event.registerPermissionLevel("Minecraft.commands.toggledownfall", RegGroup.ZONE_ADMINS);
		event.registerPermissionLevel("Minecraft.commands.testfor", RegGroup.MEMBERS);
	}

	@FEModuleEventHandler
	public void serverStopping(FMLServerStoppingEvent e)
	{
		// save all the zones
		for (Zone zone : APIRegistry.zones.getZoneList())
		{
			if (zone == null || zone.isGlobalZone() || zone.isWorldZone())
			{
				continue;
			}
			data.saveObject(ZoneHelper.container, zone);
		}

		autoPromoteManager.stop();
	}
	public void sendPermList(Player player){
		PacketDispatcher.sendPacketToPlayer(new PacketPermNodeList(permLoader.perms).getPayload(), player);
	}
}

}
