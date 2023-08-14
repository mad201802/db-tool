package de.dbtool;

import de.dbtool.cli.subcommands.options.SupportedDatabases;
import de.dbtool.files.ProfileHandler;

public class UnitTestUtils {
    public static void createDemoProfile() {
        ProfileHandler handler = new ProfileHandler();
        ProfileHandler.setProfilePath("./");
        String path = System.getProperty("user.dir") + "/src/test/resources/chinook.db";
        System.out.println(path);
        handler.createProfile("demo", path, 0, "", "", "", SupportedDatabases.SQLITE, "");
    }

    public static void deleteDemoProfile() {
        ProfileHandler handler = new ProfileHandler();
        ProfileHandler.setProfilePath("./");
        handler.deleteProfile("demo");
    }
}
