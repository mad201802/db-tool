package de.dbtool.utils;

import java.io.File;

public class FileSystem {
    public static boolean deleteDirectory(File directoryToBeDeleted) {
        System.out.println("Deleting directory: " + directoryToBeDeleted.getAbsolutePath());
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                boolean deleted = deleteDirectory(file);
                System.out.println("Deleted: " + file.getAbsolutePath() + " " + deleted);
            }
        }
        return directoryToBeDeleted.delete();
    }

    public static boolean deleteFile(File file) {
        return file.delete();
    }
}
