package com.muayadsalah.filedownloader.downloader;

import com.muayadsalah.filedownloader.model.FileHolder;
import com.muayadsalah.filedownloader.utils.Utilities;

/**
 * Created by Muayad Salah
 */
public abstract class FtpBaseFileDownloader extends BaseFileDownloader {
    private String server;

    public FtpBaseFileDownloader(FileHolder fileHolder) {
        super(fileHolder);
        this.server = Utilities.getHostNameFromURL(fileHolder.getFileUrlString());
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }
}
