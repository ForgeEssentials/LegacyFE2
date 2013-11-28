package com.forgeessentials.dummy;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraftforge.permissions.api.PermissionsManager;

import com.forgeessentials.core.commands.FECommandBase;

// Example command, to show how to use 1.6 localization and permissions.
public class PermissionsTester extends FECommandBase{

	@Override
	public List addTabCompletionOptions(ICommandSender arg0, String[] arg1) {
		return null;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender arg0) {
		return false;
		
	}

	@Override
	public List getCommandAliases() {
		return null;
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "testperm";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/testperm";
	}

	@Override
	public boolean isUsernameIndex(String[] arg0, int arg1) {
		return false;
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) {
		if (arg0 instanceof EntityPlayer && PermissionsManager.checkPerm((EntityPlayer)arg0, "fe.dummy.test"))
        	arg0.sendChatToPlayer(ChatMessageComponent.createFromText("commands.dummy.permTester.allowed"));
		else arg0.sendChatToPlayer(ChatMessageComponent.createFromText("commands.dummy.permTester.denied"));
        
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCommandPermission() {
		// TODO Auto-generated method stub
		return "fe.dummy.commands." + getCommandName();
	}

}
