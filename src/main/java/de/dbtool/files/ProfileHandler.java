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

    public Profile createProfile(String name, String hostname, String port, String dbName, String username, String password, String type, String driverPath) {
        Profile profile = new Profile(name, hostname, port, dbName, username, password, type, driverPath);
        try{
            FileWriter fileWriter = new FileWriter(PROFILE_PATH + File.separator + name + ".json");
            System.out.println(PROFILE_PATH + File.separator + name + ".json");
            this.gson.toJson(profile, Profile.class, fileWriter);
            fileWriter.close();
        } catch (Exception e) {
            System.err.println("Error while creating profile file: " + e.getMessage());
        }
        return profile;
    }

    public Boolean deleteProfile(String name) {
        try {
            File file = new File(PROFILE_PATH + File.separator + name + ".json");
            file.delete();
        } catch (Exception e) {
            System.err.println("Error while deleting profile file: " + e.getMessage());
            return false;
        }
        return true;
    }

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

    public boolean profileDirExists() {
        File folder = new File(PROFILE_PATH);
        return folder.exists();
    }

    public boolean profileDirCreate() {
        File folder = new File(PROFILE_PATH);
        return folder.mkdirs();
    }
}
    