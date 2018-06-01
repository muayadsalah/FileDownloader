package com.muayadsalah.filedownloader.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.muayadsalah.filedownloader.utils.Utilities;

import javax.validation.constraints.NotBlank;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * Created by Muayad Salah
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileHolder {
    public static final String FILES_HOLDER_FOLDER_PATH = System.getProperty("user.dir") + "/downloaded-files/";

    @NotBlank(message = "FileUrlString cannot be null or empty!")
    private String fileUrlString;

    @JsonIgnore
    private String remoteFilePath;

    @JsonIgnore
    private String outputFileName;

    @JsonIgnore
    private String protocol;

    private String username;

    private String password;

    public FileHolder() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void generateFileHolderProperties() {
        this.protocol = parseProtocol(fileUrlString);
        this.outputFileName = parseOutputFilePath(fileUrlString);
        this.remoteFilePath = parseRemoteFilePath(fileUrlString, this.protocol);
    }

    private String parseProtocol(String fileUrlString) {
        int indexOfHostNamePrefix = Utilities.getIndexOfHostNamePrefix(fileUrlString);
        if (indexOfHostNamePrefix == -1)
            throw new RuntimeException("ERROR: Protocol is not referenced in the item URL");

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
