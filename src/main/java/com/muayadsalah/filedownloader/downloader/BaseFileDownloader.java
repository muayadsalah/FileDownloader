package com.muayadsalah.filedownloader.downloader;

import com.muayadsalah.filedownloader.model.FileHolder;
import com.muayadsalah.filedownloader.utils.Utilities;

import java.io.IOException;

/**
 * Created by Muayad Salah
 */
public abstract class BaseFileDownloader {

    private FileHolder fileHolder;

    public BaseFileDownloader(FileHolder fileHolder) {
        this.fileHolder = fileHolder;
    }

    public FileHolder getFileHolder() {
        return fileHolder;
    }

    public void setFileHolder(FileHolder fileHolder) {
        this.fileHolder = fileHolder;
    }

    public void downloadFile() throws IOException {
        if (!Utilities.createDirectory(FileHolder.FILES_HOLDER_FOLDER_PATH, false))
            throw new RuntimeException("ERROR: could not create folder to download files into it");
    }
}
