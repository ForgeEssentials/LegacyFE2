package com.forgeessentials.core.modules.internal.events;

import com.forgeessentials.core.modules.ModuleContainer;
import com.forgeessentials.core.modules.events.ModuleDisableEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

public class ModuleDisableEventImpl extends AbstractModuleEvent implements ModuleDisableEvent
{
    public ModuleDisableEventImpl(ModuleContainer container)
    {
        super(container);
    }
}
