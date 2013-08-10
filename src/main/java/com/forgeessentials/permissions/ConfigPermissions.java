package com.forgeessentials.permissions;

import java.io.File;

import net.minecraftforge.common.Configuration;

import com.forgeessentials.core.modulelauncher.BaseConfig;
import com.forgeessentials.util.DBConnector;
import com.forgeessentials.util.EnumDBType;
import com.forgeessentials.util.data.api.DataStorageManager;

public class ConfigPermissions extends BaseConfig {
	
	protected Configuration	config;
	protected DBConnector	connector;
	protected boolean		importBool;
	protected String		importDir;
	private static boolean	permDefault	= false;


	public ConfigPermissions(File file) {
		super(file);
		connector = new DBConnector("PermissionsDB", DataStorageManager.getCoreDBConnector(), EnumDBType.H2_FILE, "FEPerms", file.getParent() + "/permissions", false);
	}

	@Override
	public void save() {
		connector.write(config, "database");
		config.save();
	}

	@Override
	public void load() {
		config = new Configuration(file);

		permDefault = config.get("stuff", "permissionDefault", false, "If a permission is not set anywhere, it will return this. True = allow. False = deny").getBoolean(false);

		importBool = config.get("stuff", "import", false, "if permissions should be imported from the specified dir").getBoolean(false);
		importDir = config.get("stuff", "importDir", "import", "file from wich permissions should be imported").getString();

		if (importBool == true)
		{
			config.get("stuff", "import", false).set(false);
		}

		connector.loadOrGenerate(config, "database");

		config.save();
	}
	public PermResult getPermDefault()
	{
		return permDefault ? PermResult.ALLOW : PermResult.DENY;
	}

}
