package de.dbtool.files;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import de.dbtool.cli.subcommands.options.SupportedDatabases;
import de.dbtool.console.ConsolePrinter;
import de.dbtool.exceptions.DbToolException;
import de.dbtool.files.schemas.Profile;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Utility class for file handling.
 * Manages the profile files and the file system interaction.
 */
public class ProfileHandler {

    public static String PROFILE_PATH = System.getProperty("user.home") + File.separator + ".dbtool" + File.separator + "profiles";
    public Gson gson;

    public ProfileHandler() {
        gson = new Gson();

        if (!profileDirExists()) {
            profileDirCreate();
        }
    }

    /**
     * Creates a new profile.
     * @param name
     * The name of the profile.
     * @param hostname
     * The hostname of the database.
     * @param port
     * The port of the database.
     * @param dbName
     * The name of the database.
     * @param username
     * The username for the database login.
     * @param password
     * The password for the database login.
     * @param type
     * The database type.
     * @param driverPath
     * The path to the database driver.
     * @return
     * Returns the created profile.
     */
    public Profile createProfile(String name, String hostname, int port, String dbName, String username, String password, SupportedDatabases type, String driverPath) {
        Profile profile = new Profile(name, hostname, port, dbName, username, password, type, driverPath);
        try {
            FileWriter fileWriter = new FileWriter(PROFILE_PATH + File.separator + name + ".json");
            this.gson.toJson(profile, Profile.class, fileWriter);
            fileWriter.close();
        } catch (Exception e) {
            ConsolePrinter.printError("Error while creating profile: " + e.getMessage());
        }
        return profile;
    }

    /**
     * Deletes a profile.
     * @param name
     * The name of the profile that should be deleted.
     * @return
     * Returns true if the profile was deleted successfully.
     */
    public boolean deleteProfile(String name) {
        try {
            File file = new File(PROFILE_PATH + File.separator + name + ".json");
            return file.delete();
        } catch (Exception e) {
            ConsolePrinter.printError("Error while deleting profile: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lists all valid profiles in the profile directory.
     * @return
     * Returns an array of profiles.
     */
    public ArrayList<Profile> listProfiles() {
        try {
            File folder = new File(PROFILE_PATH);
            File[] files = folder.listFiles();
            if (files == null) return new ArrayList<>();

            ArrayList<Profile> profiles = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                String extension = "";
                int feI = files[i].getName().lastIndexOf('.');
                if (i > 0) {
                    extension = files[i].getName().substring(feI+1);
                }
                if(extension.equalsIgnoreCase("json")) {
                    System.out.println(files[i].getName());
                    FileReader fileReader = new FileReader(files[i].getPath());
                    profiles.add(gson.fromJson(fileReader, Profile.class));
                    fileReader.close();
                }
            }
            return profiles;
        } catch (Exception e) {
            ConsolePrinter.printError("Error while reading profiles directory: " + e.getMessage());
        }
        return null;
    }

    /**
     * Gets a profile by name.
     *
     * @param name The name of the profile.
     * @return Returns the profile.
     */
    public Profile getProfile(String name) throws DbToolException {
        Profile profile;
        try {
            File file = new File(PROFILE_PATH + File.separator + name + ".json");
            FileReader fileReader = new FileReader(file.getPath());
            profile = gson.fromJson(fileReader, Profile.class);
            fileReader.close();
        } catch (NullPointerException | IOException ex) {
            return null;
        } catch (JsonIOException | JsonSyntaxException ex) {
            throw new DbToolException("Config file is corrupted. Please delete the profile and create a new one.");
        }

        return profile;
    }

    /**
     * Checks if the profile directory exists.
     * @return
     * Returns true if the profile directory exists.
     */
    public boolean profileDirExists() {
        File folder = new File(PROFILE_PATH);
        return folder.exists();
    }

    /**
     * Creates the profile directory.
     * @return
     * Returns true if the profile directory was created successfully.
     */
    public boolean profileDirCreate() {
        File folder = new File(PROFILE_PATH);
        return folder.mkdirs();
    }

    /**
     * Generates a profile file name with 6 chars (numbers and lowercase letters).
     * @return
     * Returns the generated file name.
     */
    public String generateProfileFileName() {
        StringBuilder fileName = new StringBuilder();
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        for(int i = 0; i < 6; i++) {
            fileName.append(chars.charAt((int) (Math.random() * chars.length())));
        }
        return fileName.toString();
    }

    public static void setProfilePath(String path) {
        PROFILE_PATH = path;
    }

}
    