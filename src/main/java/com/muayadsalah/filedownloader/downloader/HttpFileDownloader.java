package com.muayadsalah.filedownloader.downloader;

import com.muayadsalah.filedownloader.domain.FileHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Muayad Salah
 */
public class HttpFileDownloader extends BaseFileDownloader {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpFileDownloader.class);

    public HttpFileDownloader(FileHolder fileHolder) {
        super(fileHolder);
    }

    @Override
    public void downloadFile() throws IOException {
        super.downloadFile();
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;

        try {
            URL url = new URL(getFileHolder().getRemoteFilePath());
            URLConnection urlConn = url.openConnection();//connect

            inputStream = urlConn.getInputStream();               //get connection inputstream
            fileOutputStream = new FileOutputStream(getFileHolder().getOutputFileName());   //open outputstream to local file

            byte[] buffer = new byte[4096];              //declare 4KB buffer
            int len;

            //while we have availble data, continue downloading and storing to local file
            while ((len = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, len);
            }
            LOGGER.debug("File {} has been downloaded successfully.", getFileHolder().getOutputFileName());
        } catch (IOException ex) {
            LOGGER.error("ERROR: {}", ex.getLocalizedMessage(), ex);
            throw ex;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } finally {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            }
        }
    }
}
