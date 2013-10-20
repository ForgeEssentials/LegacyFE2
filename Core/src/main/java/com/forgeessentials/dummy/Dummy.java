package com.forgeessentials.dummy;

import com.forgeessentials.core.modules.IFEModule;

@IFEModule.LoadMe(name = "Dummy")
public class Dummy implements IFEModule
{

    @Override
    public String getVersion()
    {
        return "0.1";
    }
}
