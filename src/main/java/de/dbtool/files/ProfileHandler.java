package de.dbtool.files;

import com.google.gson.Gson;
import de.dbtool.files.schemas.Profile;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Utility class for file handling.
 * Manages the profile files and the file system interaction.
 */
public class ProfileHandler {

    private static final String PROFILE_PATH = System.getProperty("user.home") + File.separator + ".dbtool" + File.separator + "profiles";
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
    public Profile createProfile(String name, String hostname, String port, String dbName, String username, String password, String type, String driverPath) {
        Profile profile = new Profile(name, hostname, port, dbName, username, password, type, driverPath);
        try{
            FileWriter fileWriter = new FileWriter(PROFILE_PATH + File.separator + name + ".json");
            System.out.println(PROFILE_PATH + File.separator + name + ".json");
            this.gson.toJson(profile, Profile.class, fileWriter);
            fileWriter.close();
        } catch (Exception e) {
            System.err.println("Error while creating profile: " + e.getMessage());
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
    public Boolean deleteProfile(String name) {
        try {
            File file = new File(PROFILE_PATH + File.separator + name + ".json");
            file.delete();
        } catch (Exception e) {
            System.err.println("Error while deleting profile: " + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Lists all valid profiles in the profile directory.
     * @return
     * Returns an array of profiles.
     */
    public Profile[] listProfiles() {
        try {
            File folder = new File(PROFILE_PATH);
            File[] files = folder.listFiles();
            Profile[] profiles = new Profile[files.length];
            for(int i = 0; i < files.length; i++) {
                FileReader fileReader = new FileReader(files[i].getPath());
                profiles[i] = gson.fromJson(fileReader, Profile.class);
                fileReader.close();
            }
            return profiles;
        } catch (Exception e) {
            System.err.println("Error while listing profile files: " + e.getMessage());
        }
        return null;
    }

    /**
     * Gets a profile by name.
     * @param name
     * The name of the profile.
     * @return
     * Returns the profile.
     */
    public Profile getProfile(String name) {
        try {
            File file = new File(PROFILE_PATH + File.separator + name + ".json");
            FileReader fileReader = new FileReader(file.getPath());
            Profile profile = gson.fromJson(fileReader, Profile.class);
            return profile;
        } catch (Exception e) {
            System.err.println("Error while getting profile: " + e.getMessage());
        }
        return null;
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
}
    