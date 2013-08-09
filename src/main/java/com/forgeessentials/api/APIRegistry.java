package com.forgeessentials.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.forgeessentials.api.permissions.IPermissionsHelper;
import com.forgeessentials.api.permissions.IZoneManager;

public class APIRegistry {
	public static IZoneManager zones;
	public static IPermissionsHelper perms;

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.TYPE })
	public @interface ForgeEssentialsRegistrar {
		String ident();
	}

}
