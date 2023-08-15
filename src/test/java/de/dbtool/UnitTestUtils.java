package de.dbtool;

import de.dbtool.cli.subcommands.options.SupportedDatabases;
import de.dbtool.files.ProfileHandler;
import de.dbtool.utils.FileSystem;

import java.io.File;

public class UnitTestUtils {
    private static String PATH = "tmp";

    public static void createDemoProfile() {
        ProfileHandler.setProfilePath(PATH);
        ProfileHandler handler = new ProfileHandler();
        String path = System.getProperty("user.dir") + "/src/test/resources/chinook.db";
        handler.createProfile("demo", path, 0, "", "", "", SupportedDatabases.SQLITE, "");
    }

    public static void deleteDemoProfile() {
        ProfileHandler handler = new ProfileHandler();
        ProfileHandler.setProfilePath(PATH);
        handler.deleteProfile("demo");
        FileSystem.deleteDirectory(new File(PATH));
    }
}
