package com.muayadsalah.filedownloader.downloader;

import com.muayadsalah.filedownloader.model.FileHolder;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by Muayad Salah
 */
public class FtpFileDownloader extends FtpBaseFileDownloader {
    private static final int FTP_PORT = 21;
    private static final Logger LOGGER = LoggerFactory.getLogger(FtpFileDownloader.class);

    public FtpFileDownloader(FileHolder fileHolder) {
        super(fileHolder);
    }

    @Override
    public void downloadFile() throws IOException {
        super.downloadFile();
        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient.connect(getServer(), FTP_PORT);
            ftpClient.login(getFileHolder().getUsername(), getFileHolder().getPassword());

            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(getFileHolder().getOutputFileName()));
            InputStream inputStream = ftpClient.retrieveFileStream(getFileHolder().getRemoteFilePath());

            byte[] bytesArray = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(bytesArray)) != -1) {
                outputStream.write(bytesArray, 0, bytesRead);
            }

            boolean success = ftpClient.completePendingCommand();
            if (success) {
                LOGGER.debug("File {} has been downloaded successfully.", getFileHolder().getOutputFileName());
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException ex) {
            LOGGER.error("ERROR: {}", ex.getLocalizedMessage(), ex);
            throw ex;
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        }
    }
}
