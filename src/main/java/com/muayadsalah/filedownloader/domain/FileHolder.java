package com.muayadsalah.filedownloader.domain;

import com.muayadsalah.filedownloader.utils.Utilities;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * Created by Muayad Salah
 */
public class FileHolder {
    public static final String FILES_HOLDER_FOLDER_PATH = System.getProperty("user.dir") + "/downloaded-files/";

    private String fileUrlString;
    private String fileName;
    private String remoteFilePath;
    private String outputFileName;
    private String protocol;

    public FileHolder(String fileUrlString) {
        this.fileUrlString = fileUrlString;
        this.protocol = parseProtocol(fileUrlString);
        this.outputFileName = parseOutputFilePath(fileUrlString);
        this.remoteFilePath = parseRemoteFilePath(fileUrlString, this.protocol);
        this.fileName = getOutputFileName();
    }

    public FileHolder(String fileUrlString, String fileName) {
        this.fileUrlString = fileUrlString;
        this.fileName = fileName;
        this.protocol = parseProtocol(fileUrlString);
        this.outputFileName = parseOutputFilePath(fileUrlString);
        this.remoteFilePath = parseRemoteFilePath(fileUrlString, this.protocol);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrlString() {
        return fileUrlString;
    }

    public void setFileUrlString(String fileUrlString) {
        this.fileUrlString = fileUrlString;
    }

    public String getRemoteFilePath() {
        return remoteFilePath;
    }

    public void setRemoteFilePath(String remoteFilePath) {
        this.remoteFilePath = remoteFilePath;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    private String parseProtocol(String fileUrlString) {
        int indexOfHostNamePrefix = Utilities.getIndexOfHostNamePrefix(fileUrlString);
        if (indexOfHostNamePrefix == -1)
            throw new RuntimeException("ERROR: Protocol is not referenced in the file URL");

        return fileUrlString.substring(0, fileUrlString.indexOf(':'));
    }

    private String parseOutputFilePath(String fileUrlString) {
        String encodedUrl;
        try {
            encodedUrl = java.net.URLEncoder.encode(fileUrlString, StandardCharsets.ISO_8859_1.displayName());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("ERROR: " + e.getLocalizedMessage());
        }

        return FILES_HOLDER_FOLDER_PATH + encodedUrl;
    }

    private String parseRemoteFilePath(String fileUrlString, String protocol) {
        if (protocol == null)
            throw new RuntimeException("ERROR: Protocol is not set");

        if (protocol.equals(Protocols.HTTP.toString()))
            return fileUrlString;
        else if (protocol.equals(Protocols.FTP.toString()) | protocol.equals(Protocols.SFTP.toString())) {
            String hostName = Utilities.getHostNameFromURL(fileUrlString);
            if (hostName == null)
                throw new RuntimeException("ERROR: FTP/SFTP protocol needs hostname to be set!");
            int indexOfHostName = fileUrlString.indexOf(hostName);
            if (indexOfHostName == -1)
                throw new RuntimeException("ERROR: Hostname is not part of the provided URL");
            return fileUrlString.substring(indexOfHostName + hostName.length() + 1); //Plus one for the slash after hostname
        } else {
            throw new RuntimeException("ERROR: Protocol is not supported");
        }
    }
}
