package com.muayadsalah.filedownloader.utils;

import com.muayadsalah.filedownloader.model.FileHolder;

import java.io.File;

/**
 * Created by Muayad Salah
 */
public final class Utilities {
    private static final String HOSTNAME_PREFEX = "://";

    private Utilities() {
    }

    public static int getIndexOfHostNamePrefix(String fileUrlString) {
        return fileUrlString.indexOf(HOSTNAME_PREFEX);
    }

    public static String getHostNameFromURL(String fileUrlString) {
        int indexOfHostNamePrefix = getIndexOfHostNamePrefix(fileUrlString);
        if (indexOfHostNamePrefix == -1)
            return null;

        int hostNameStartIndex = indexOfHostNamePrefix + HOSTNAME_PREFEX.length();
        int hostNameEndIndex = fileUrlString.indexOf('/', hostNameStartIndex);
        if (hostNameEndIndex == -1)
            return null;

        return fileUrlString.substring(hostNameStartIndex, hostNameEndIndex);
    }

    public static boolean createDirectory(String dir, boolean dirTree) {
        File directory = new File(FileHolder.FILES_HOLDER_FOLDER_PATH);
        if (!directory.exists()) {
            if (dirTree)
                return directory.mkdirs();
            else
                return directory.mkdir();
        }
        return true;
    }
}
