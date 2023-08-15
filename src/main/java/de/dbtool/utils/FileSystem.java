package de.dbtool.utils;

import java.io.File;

public class FileSystem {
    public static boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                boolean deleted = deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    public static boolean deleteFile(File file) {
        return file.delete();
    }
}
