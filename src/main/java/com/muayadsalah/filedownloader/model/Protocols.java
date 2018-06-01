package com.muayadsalah.filedownloader.model;

/**
 * Created by Muayad Salah
 */
public enum Protocols {
    HTTP,
    FTP,
    SFTP;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}