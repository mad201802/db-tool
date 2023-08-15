package de.dbtool.utils;

import java.io.File;

/**
 * This class contains methods to work with the file system
 */
public class FileSystem {
    /**
     * Delete a directory and all its contents
     * @param directoryToBeDeleted The directory to be deleted
     * @return True if the directory was deleted successfully
     */
    public static boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                boolean deleted = deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    /**
     * Delete a file
     * @param file The file to be deleted
     * @return True if the file was deleted successfully
     */
    public static boolean deleteFile(File file) {
        return file.delete();
    }
}
