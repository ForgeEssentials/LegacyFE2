package com.forgeessentials.permissions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

import com.forgeessentials.api.APIRegistry;
import com.forgeessentials.api.areas.WorldPoint;
import com.forgeessentials.api.permissions.IPermissionsHelper;
import com.forgeessentials.api.permissions.RegGroup;
import com.forgeessentials.api.permissions.types.Group;
import com.forgeessentials.api.permissions.types.Zone;
import com.forgeessentials.core.modulelauncher.util.CallableMap.FECallable;
import com.forgeessentials.permissions.handlers.PermissionsBlanketHandler;
import com.forgeessentials.permissions.handlers.PermissionsPlayerHandler;
import com.forgeessentials.permissions.handlers.PermissionsPropHandler;
import com.forgeessentials.util.OutputHandler;
import com.google.common.collect.HashMultimap;

public class PermissionsHelper implements IPermissionsHelper
{
	public final String	EntryPlayer	= "_ENTRY_PLAYER_";
	private String		EPPrefix	= "";
	private String		EPSuffix	= "";
	private Group		DEFAULT		= new Group(RegGroup.ZONE.toString(), " ", " ", null, APIRegistry.zones.getGLOBAL().getZoneName(), 0);

	@Override
	public boolean checkPermAllowed(PermQuery query)
	{
		if (query instanceof PermQueryPlayer)
		{
			PermissionsPlayerHandler.parseQuery((PermQueryPlayer) query);
		}
		else
		{
			PermissionsBlanketHandler.parseQuery(query);
		}

		return query.isAllowed();
	}

	@Override
	public boolean checkPermAllowed(EntityPlayer player, String node){
		return checkPermAllowed(new PermQueryPlayer(player, node));
	}

	@Override
	public PermResult checkPermResult(PermQuery query)
	{
		if (query instanceof PermQueryPlayer)
		{
			PermissionsPlayerHandler.parseQuery((PermQueryPlayer) query);
		}
		else
		{
			PermissionsBlanketHandler.parseQuery(query);
		}

		return query.getResult();
	}

	@Override
	public void getPermissionProp(PropQuery query)
	{
		PermissionsPropHandler.handleQuery(query);
	}

	@Override
	public Group createGroupInZone(String groupName, String zoneName, String prefix, String suffix, String parent, int priority)
	{
		Group g = new Group(groupName, prefix, suffix, parent, zoneName, priority);
		SqlHelper.createGroup(g);
		return g;
	}

	@Override
	public String setPlayerPermission(String username, String permission, boolean allow, String zoneID)
	{
		try
		{
			Zone zone = APIRegistry.zones.getZone(zoneID);
			if (zone == null)
				return Localization.format(Localization.ERROR_ZONE_NOZONE, zoneID);

			Permission perm = new Permission(permission, allow);

			// send out permission string.
			PermissionSetEvent event = new PermissionSetEvent(perm, zone, "p:" + username);
			if (MinecraftForge.EVENT_BUS.post(event))
				return event.getCancelReason();

			SqlHelper.generatePlayer(username);
			boolean worked = SqlHelper.setPermission(username, false, perm, zoneID);

			if (!worked)
				return Localization.get(Localization.ERROR_PERM_SQL);
		}
		catch (Throwable t)
		{
			return t.getLocalizedMessage();
		}

		return null;
	}

	@Override
	public String setPlayerPermissionProp(String username, String permission, String value, String zoneID)
	{
		try
		{
			Zone zone = APIRegistry.zones.getZone(zoneID);
			if (zone == null)
				return Localization.format(Localization.ERROR_ZONE_NOZONE, zoneID);

			PermissionProp perm = new PermissionProp(permission, value);

			// send out permission string.
			PermissionPropSetEvent event = new PermissionPropSetEvent(perm, zone, "p:" + username);
			if (MinecraftForge.EVENT_BUS.post(event))
				return event.getCancelReason();

			SqlHelper.generatePlayer(username);
			boolean worked = SqlHelper.setPermProp(username, false, perm, zoneID);

			if (!worked)
				return Localization.get(Localization.ERROR_PERM_SQL);
		}
		catch (Throwable t)
		{
			return t.getLocalizedMessage();
		}

		return null;
	}

	@Override
	public String setGroupPermission(String group, String permission, boolean allow, String zoneID)
	{
		try
		{
			Zone zone = APIRegistry.zones.getZone(zoneID);

			if (zone == null)
				return Localization.format(Localization.ERROR_ZONE_NOZONE, zoneID);

			Group g = SqlHelper.getGroupForName(group);
			if (g == null)
				return Localization.format("message.error.nogroup", group);

			Permission perm = new Permission(permission, allow);

			// send out permission string.
			PermissionSetEvent event = new PermissionSetEvent(perm, zone, "g:" + group);
			if (MinecraftForge.EVENT_BUS.post(event))
				return event.getCancelReason();

			boolean worked = SqlHelper.setPermission(group, true, perm, zoneID);

			if (!worked)
				return Localization.get(Localization.ERROR_PERM_SQL);
		}
		catch (Throwable t)
		{
			return t.getMessage();
		}

		return null;
	}

	@Override
	public String setGroupPermissionProp(String group, String permission, String value, String zoneID)
	{
		try
		{
			Zone zone = APIRegistry.zones.getZone(zoneID);
			if (zone == null)
				return Localization.format(Localization.ERROR_ZONE_NOZONE, zoneID);

			Group g = SqlHelper.getGroupForName(group);
			if (g == null)
				return Localization.format("message.error.nogroup", group);

			PermissionProp perm = new PermissionProp(permission, value);

			// send out permission string.
			PermissionPropSetEvent event = new PermissionPropSetEvent(perm, zone, "g:" + group);
			if (MinecraftForge.EVENT_BUS.post(event))
				return event.getCancelReason();

			boolean worked = SqlHelper.setPermProp(group, true, perm, zoneID);

			if (!worked)
				return Localization.get(Localization.ERROR_PERM_SQL);
		}
		catch (Throwable t)
		{
			return t.getLocalizedMessage();
		}

		return null;
	}

	@Override
	public ArrayList<Group> getApplicableGroups(EntityPlayer player, boolean includeDefaults)
	{
		Zone zone = APIRegistry.zones.getWhichZoneIn(new WorldPoint(player));

		return getApplicableGroups(player.username, includeDefaults, zone.getZoneName());
	}

	@Override
	public ArrayList<Group> getApplicableGroups(String player, boolean includeDefaults, String zoneID)
	{
		ArrayList<Group> list = new ArrayList<Group>();

		Zone zone = APIRegistry.zones.getZone(zoneID);

		while (zone != null)
		{
			list.addAll(SqlHelper.getGroupsForPlayer(player, zone.getZoneName()));

			if (zone == APIRegistry.zones.getGLOBAL())
			{
				zone = null;
			}
			else
			{
				zone = APIRegistry.zones.getZone(zone.parent);
			}
		}

		if (includeDefaults)
		{
			list.add(getDEFAULT());
		}

		return list;
	}

	@Override
	public ArrayList<String> getPlayersInGroup(String group, String zone)
	{
	    return SqlHelper.getPlayersForGroup(group, zone);
	}

	@Override
	public Group getHighestGroup(EntityPlayer player)
	{
		Zone zone = APIRegistry.zones.getWhichZoneIn(new WorldPoint(player));
		TreeSet<Group> list = new TreeSet<Group>();

		ArrayList<Group> temp;
		while (zone != null && list.size() <= 0)
		{
			temp = SqlHelper.getGroupsForPlayer(player.username, zone.getZoneName());

			if (!temp.isEmpty())
			{
				list.addAll(temp);
			}

			zone = APIRegistry.zones.getZone(zone.parent);
		}

		if (list.size() == 0)
			return getDEFAULT();
		else
			return list.pollFirst();
	}

	@Override
	public Group getGroupForName(String name)
	{
		return SqlHelper.getGroupForName(name);
	}

	@Override
	public String setPlayerGroup(String group, String player, String zone)
	{
	    SetPlayerGroupEvent event = new SetPlayerGroupEvent(group, player, zone);
        if (MinecraftForge.EVENT_BUS.post(event))
            return event.getCancelReason();
        
		SqlHelper.generatePlayer(player);
		return SqlHelper.setPlayerGroup(group, player, zone);
	}

	@Override
	public String addPlayerToGroup(String group, String player, String zone)
	{
		SqlHelper.generatePlayer(player);
		if (getApplicableGroups(player, false, zone).contains(getGroupForName(group)))
			return "Player already in group.";
		else
		{
		    AddPlayerGroupEvent event = new AddPlayerGroupEvent(group, player, zone);
		    if (MinecraftForge.EVENT_BUS.post(event))
		        return event.getCancelReason();

			return SqlHelper.addPlayerGroup(group, player, zone);
		}
	}

	@Override
	public String clearPlayerGroup(String group, String player, String zone)
	{
	    RemovePlayerGroupEvent event = new RemovePlayerGroupEvent(group, player, zone);
        if (MinecraftForge.EVENT_BUS.post(event))
            return event.getCancelReason();
        
		SqlHelper.generatePlayer(player);
		return SqlHelper.removePlayerGroup(group, player, zone);
	}

	@Override
	public String clearPlayerPermission(String player, String node, String zone)
	{
		SqlHelper.generatePlayer(player);
		return SqlHelper.removePermission(player, false, node, zone);
	}

	@Override
	public String clearPlayerPermissionProp(String player, String node, String zone)
	{
		SqlHelper.generatePlayer(player);
		return SqlHelper.removePermissionProp(player, false, node, zone);
	}

	@Override
	public void deleteGroupInZone(String group, String zone)
	{
		SqlHelper.deleteGroupInZone(group, zone);
	}

	@Override
	public boolean updateGroup(Group group)
	{
		return SqlHelper.updateGroup(group);
	}

	@Override
	public String clearGroupPermission(String name, String node, String zone)
	{
		return SqlHelper.removePermission(name, true, node, zone);
	}

	@Override
	public String clearGroupPermissionProp(String name, String node, String zone)
	{
		return SqlHelper.removePermissionProp(name, true, node, zone);
	}

    @Override
	public ArrayList<Group> getGroupsInZone(String zoneName)
	{
		return SqlHelper.getGroupsInZone(zoneName);
	}

	@Override
	public String getPermissionForPlayer(String target, String zone, String perm)
	{
		return SqlHelper.getPermission(target, false, perm, zone);
	}

	@Override
	public String getPermissionPropForPlayer(String target, String zone, String perm)
	{
		return SqlHelper.getPermissionProp(target, false, perm, zone);
	}

	@Override
	public String getPermissionForGroup(String target, String zone, String perm)
	{
		return SqlHelper.getPermission(target, true, perm, zone);
	}

	@Override
	public String getPermissionPropForGroup(String target, String zone, String perm)
	{
		return SqlHelper.getPermissionProp(target, true, perm, zone);
	}

	@Override
	public ArrayList getPlayerPermissions(String target, String zone)
	{
		ArrayList<String> output = new ArrayList<String>();

		if (zone == null)
		{
			output.add(Localization.format(Localization.ERROR_ZONE_NOZONE, zone));
		}
		else
		{
			for (Permission perm : SqlHelper.getAllPermissions(target, zone, false))
				output.add(perm.toString());
		}

		return output;
	}

	@Override
	public ArrayList getPlayerPermissionProps(String target, String zone)
	{
		ArrayList<String> output = new ArrayList<String>();

		if (zone == null)
		{
			output.add(Localization.format(Localization.ERROR_ZONE_NOZONE, zone));
		}
		else
		{
			for (Permission perm : SqlHelper.getAllPermissions(target, zone, true))
				output.add(perm.toString());
		}

		return output;
	}

	@Override
	public ArrayList<String> getGroupPermissions(String target, String zone)
	{
		ArrayList<String> output = new ArrayList<String>();
		Group g = SqlHelper.getGroupForName(target);

		if (zone == null)
		{
			output.add(Localization.format(Localization.ERROR_ZONE_NOZONE, zone));
		}
		else if (g == null)
		{
			output.add(Localization.format("message.error.nogroup", target));
		}
		else
		{
			for (Permission perm : SqlHelper.getAllPermissions(target, zone, true))
				output.add(perm.toString());
		}

		return output;
	}

	@Override
	public ArrayList<String> getGroupPermissionProps(String target, String zone)
	{
		ArrayList<String> output = new ArrayList<String>();
		Group g = SqlHelper.getGroupForName(target);

		if (zone == null)
		{
			output.add(Localization.format(Localization.ERROR_ZONE_NOZONE, zone));
		}
		else if (g == null)
		{
			output.add(Localization.format("message.error.nogroup", target));
		}
		else
		{
			for (Permission perm : SqlHelper.getAllPermissions(target, zone, true))
				output.add(perm.toString());
		}

		return output;
	}

	@Override
	public String getEPPrefix()
	{
		return EPPrefix;
	}


	@Override
	public Group getDEFAULT()
	{
		return DEFAULT;
	}

	@Override
	public void setEPPrefix(String ePPrefix)
	{
		EPPrefix = ePPrefix;
	}

	@Override
	public String getEPSuffix()
	{
		return EPSuffix;
	}

	@Override
	public void setEPSuffix(String ePSuffix)
	{
		EPSuffix = ePSuffix;
	}

	@Override
	public String getEntryPlayer()
	{
		return EntryPlayer;
	}

	protected HashSet<String>							mods;
	protected TreeSet<String>							perms;
	protected HashMultimap<RegGroup, PermissionChecker>	registerredPerms;
	private Set<FECallable>								data;

	public PermRegLoader(Set<FECallable> calls)
	{
		mods = new HashSet<String>();
		data = calls;
	}

	protected void loadAllPerms()
	{
		

		String modid;
		for (FECallable call : data)
		{
			modid = call.getIdent();
			mods.add(modid);

			try
			{
				call.call(event);
			}
			catch (Exception e)
			{
				OutputHandler.felog.severe("Error trying to load permissions from \"" + modid + "\"!");
				e.printStackTrace();
			}
		}

		perms = event.registerred;
		registerredPerms = event.perms;
	}

	protected void clearMethods()
	{
		data = null;
	}
		private HashMultimap<RegGroup, PermissionChecker>	perms;
		private TreeSet<String>								registerred;

		private PermissionRegistrationEvent()
		{
			perms = HashMultimap.create();
			registerred = new TreeSet<String>();
		}

		@Override
		public void registerPermissionLevel(String permission, RegGroup group)
		{
			registerPermissionLevel(permission, group, false);
		}

		@Override
		public void registerPermission(String permission)
		{
			registerred.add(permission);
		}

		@Override
		public void registerPermissionLevel(String permission, RegGroup group, boolean alone)
		{
			Permission deny = new Permission(permission, false);
			Permission allow = new Permission(permission, true);

			if (!deny.isAll)
			{
				registerred.add(permission);
			}

			if (group == null)
			{
				perms.put(RegGroup.ZONE, deny);
			}
			else if (group == RegGroup.ZONE)
			{
				perms.put(RegGroup.ZONE, allow);
			}
			else
			{
				perms.put(group, allow);

				if (alone)
					return;

				for (RegGroup g : getHigherGroups(group))
				{
					perms.put(g, allow);
				}

				for (RegGroup g : getLowerGroups(group))
				{
					perms.put(g, deny);
				}
			}
		}

		private RegGroup[] getHigherGroups(RegGroup g)
		{
			switch (g)
				{
					case GUESTS:
						return new RegGroup[]
						{ RegGroup.MEMBERS, RegGroup.ZONE_ADMINS, RegGroup.OWNERS };
					case MEMBERS:
						return new RegGroup[]
						{ RegGroup.ZONE_ADMINS, RegGroup.OWNERS };
					case ZONE_ADMINS:
						return new RegGroup[]
						{ RegGroup.OWNERS };
					default:
						return new RegGroup[] {};
				}
		}

		private RegGroup[] getLowerGroups(RegGroup g)
		{
			switch (g)
				{
					case MEMBERS:
						return new RegGroup[]
						{ RegGroup.GUESTS };
					case ZONE_ADMINS:
						return new RegGroup[]
						{ RegGroup.MEMBERS, RegGroup.GUESTS };
					case OWNERS:
						return new RegGroup[]
						{ RegGroup.MEMBERS, RegGroup.GUESTS, RegGroup.ZONE_ADMINS };
					default:
						return new RegGroup[] {};
				}
		}

		@Override
		public void registerPermissionProp(String permission, String globalDefault)
		{
			PermissionProp prop = new PermissionProp(permission, globalDefault);
			perms.put(RegGroup.ZONE, prop);
		}

		@Override
		public void registerPermissionProp(String permission, int globalDefault)
		{
			PermissionProp prop = new PermissionProp(permission, "" + globalDefault);
			perms.put(RegGroup.ZONE, prop);
		}

		@Override
		public void registerPermissionProp(String permission, float globalDefault)
		{
			PermissionProp prop = new PermissionProp(permission, "" + globalDefault);
			perms.put(RegGroup.ZONE, prop);
		}

		@Override
		public void registerGroupPermissionprop(String permission, String value, RegGroup group)
		{
			PermissionProp prop = new PermissionProp(permission, "" + value);
			perms.put(group, prop);
		}

		@Override
		public void registerGroupPermissionprop(String permission, int value, RegGroup group)
		{
			PermissionProp prop = new PermissionProp(permission, "" + value);
			perms.put(group, prop);
		}

		@Override
		public void registerGroupPermissionprop(String permission, float value, RegGroup group)
		{
			PermissionProp prop = new PermissionProp(permission, "" + value);
			perms.put(group, prop);
		}
	}
}
}
