package com.muayadsalah.filedownloader;

import com.muayadsalah.filedownloader.domain.FileHolder;
import com.muayadsalah.filedownloader.domain.Protocols;
import com.muayadsalah.filedownloader.downloader.BaseFileDownloader;
import com.muayadsalah.filedownloader.downloader.FtpFileDownloader;
import com.muayadsalah.filedownloader.downloader.HttpFileDownloader;
import com.muayadsalah.filedownloader.downloader.SftpFileDownloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Muayad Salah
 */
public class MultiFileDownloader {
    private static final Logger LOGGER = LoggerFactory.getLogger(FtpFileDownloader.class);

    private static List<String> urls = new ArrayList<String>(Arrays.asList(
            "http://www.lovethispic.com/uploaded_images/116204-Pretty-Pink-Sunset.jpg",
            "ftp://test.rebex.net/pub/example/WinFormClient.png",
            "sftp://test.rebex.net/pub/example/KeyGenerator.png"));

    private static List<String> ftpServerCredentials = new ArrayList<String>(Arrays.asList(
            "demo", "password"));


    public static void main(String args[]) {
        List<FileHolder> fileHolders = urls.stream().map(FileHolder::new).collect(Collectors.toList());
        List<BaseFileDownloader> baseFileDownloaders = fileHolders.stream().map(fileHolder -> {
            if (fileHolder.getProtocol().equals(Protocols.HTTP.toString()))
                return new HttpFileDownloader(fileHolder);
            else if (fileHolder.getProtocol().equals(Protocols.FTP.toString()))
                return new FtpFileDownloader(fileHolder, ftpServerCredentials.get(0), ftpServerCredentials.get(1));
            else if (fileHolder.getProtocol().equals(Protocols.SFTP.toString()))
                return new SftpFileDownloader(fileHolder, ftpServerCredentials.get(0), ftpServerCredentials.get(1));
            else {
                String error = "Protocol is not supported";
                LOGGER.error("ERROR: {}", error);
                throw new RuntimeException(error);
            }
        }).collect(Collectors.toList());


        baseFileDownloaders.forEach(baseFileDownloader -> {
            try {
                baseFileDownloader.downloadFile();
            } catch (IOException ex) {
                LOGGER.error("ERROR: {}", ex.getLocalizedMessage(), ex);
            }
        });
    }

}
