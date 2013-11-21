package com.forgeessentials.core.modules.events;

import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.server.MinecraftServer;

public interface ModuleEnableEvent extends FEModuleEvent
{
    /**
     * TODO: stuff with this.....
     */
    void registerCommand();

    MinecraftServer getServer();

    /**
     * This method sends a runtime IMC message to the specified recipient.
     * Messages of type NBT are modified to contain information about the module that is sending the message.
     * Messages of all types will be midified to ensure that ForgeEssentials is the official sender, this is to ensure that any replies will be received.
     * @param modid ModID of the recipient mod
     * @param message The message you wish to send.
     * @see cpw.mods.fml.common.event.FMLInterModComms.IMCMessage
     */
    void sendToMod(String modid, FMLInterModComms.IMCMessage message);

    /**
     * This method sends a runtime IMC message to the specified recipient.
     * Messages of type NBT and String are modified to contain information about the module that is sending the message.
     * Messages of all types will be midified to ensure that ForgeEssentials is the official sender, this is to ensure that any replies will be received.
     * @param moduleName The ModuleName or ID of the recipient mod.
     * @param message The message you wish to send.
     * @see cpw.mods.fml.common.event.FMLInterModComms.IMCMessage
     */
    void sendToModule(String moduleName, FMLInterModComms.IMCMessage message);
}
