package com.forgeessentials.permissions.handlers;

import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

import com.forgeessentials.api.APIRegistry;
import com.forgeessentials.util.FunctionHelper;

public class EventHandler {
	
	@ForgeSubscribe(priority = EventPriority.LOW)
	public void onZoneChange(PlayerChangedZone event)
	{
		PropQueryPlayerZone query1 = new PropQueryPlayerZone(event.entityPlayer, "ForgeEssentials.Permissions.Zone.exit", event.beforeZone, false);
		PropQueryPlayerZone query2 = new PropQueryPlayerZone(event.entityPlayer, "ForgeEssentials.Permissions.Zone.entry", event.afterZone, false);

		APIRegistry.perms.getPermissionProp(query1);
		if (query1.hasValue())
		{
			event.entityPlayer.sendChatToPlayer(FunctionHelper.formatColors(query1.getStringValue()));
		}

		APIRegistry.perms.getPermissionProp(query2);
		if (query2.hasValue())
		{
			event.entityPlayer.sendChatToPlayer(FunctionHelper.formatColors(query2.getStringValue()));
		}

	}

}
