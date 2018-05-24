package com.muayadsalah.filedownloader.downloader;

import com.muayadsalah.filedownloader.domain.FileHolder;
import com.muayadsalah.filedownloader.utils.Utilities;

/**
 * Created by Muayad Salah
 */
public abstract class FtpBaseFileDownloader extends BaseFileDownloader {
    private String server;
    private String user;
    private String password;

    public FtpBaseFileDownloader(FileHolder fileHolder, String user, String password) {
        super(fileHolder);
        this.server = Utilities.getHostNameFromURL(fileHolder.getFileUrlString());
        this.user = user;
        this.password = password;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
