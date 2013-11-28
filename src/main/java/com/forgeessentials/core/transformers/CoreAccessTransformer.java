package com.forgeessentials.core.transformers;

import java.io.IOException;

import cpw.mods.fml.common.asm.transformers.AccessTransformer;

public class CoreAccessTransformer extends AccessTransformer{

	public CoreAccessTransformer() throws IOException {
		super("feCore.at");
	}

}
