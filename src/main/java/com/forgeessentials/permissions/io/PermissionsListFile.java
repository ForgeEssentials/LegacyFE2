package com.forgeessentials.permissions.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Set;

import com.forgeessentials.permissions.PermissionsModule;
import com.forgeessentials.util.FunctionHelper;

public class PermissionsListFile {
	private static final String OUTPUT_FILE = "PermissionsList.txt";
	private File output;

	public PermissionsListFile() {
		output = new File(PermissionsModule.permsFolder, OUTPUT_FILE);
	}

	public boolean shouldMake() {
		return !output.exists();
	}

	public void output(Set<String> permissions) {
		int permsize = permissions.size();
		try {
			output.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(output));

			writer.write("#// ------------ PERMISSIONS LIST ------------ \\\\#");
			writer.newLine();
			writer.write("#// --------------- "
					+ FunctionHelper.getCurrentDateString()
					+ " --------------- \\\\#");
			writer.newLine();
			writer.write("#// ------------ Total amount: " + permsize
					+ " ------------ \\\\#");
			writer.newLine();
			writer.write("#// ------------------------------------------ \\\\#");
			writer.newLine();
			writer.newLine();

			for (String perm : permissions) {
				writer.write(perm);
				writer.newLine();
			}

			writer.close();
		} catch (Exception e) {

		}
	}

}
