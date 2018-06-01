package com.muayadsalah.filedownloader;

import com.muayadsalah.filedownloader.downloader.FtpFileDownloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


}
