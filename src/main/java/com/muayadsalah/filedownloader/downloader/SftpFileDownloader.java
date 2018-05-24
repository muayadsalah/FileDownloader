package com.muayadsalah.filedownloader.downloader;

import com.jcraft.jsch.*;
import com.muayadsalah.filedownloader.domain.FileHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Muayad Salah
 */
public class SftpFileDownloader extends FtpBaseFileDownloader {
    public static final int SFTP_PORT = 22;
    private static final Logger LOGGER = LoggerFactory.getLogger(SftpFileDownloader.class);

    public SftpFileDownloader(FileHolder fileHolder, String user, String pass) {
        super(fileHolder, user, pass);
    }

    @Override
    public void downloadFile() throws IOException {
        super.downloadFile();

        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp channelSftp = null;

        try {
            session = jsch.getSession(getUser(), getServer(), SFTP_PORT);
            session.setPassword(getPassword());
            session.setConfig("StrictHostKeyChecking", "no");
            session.setTimeout(15000);
            session.connect();

            try {
                channelSftp = (ChannelSftp) session.openChannel("sftp");
                channelSftp.connect();

                FileOutputStream outputStream = new FileOutputStream(getFileHolder().getOutputFileName());
                channelSftp.get(getFileHolder().getRemoteFilePath(), outputStream);
                LOGGER.debug("File {} has been downloaded successfully.", getFileHolder().getOutputFileName());
            } catch (SftpException ex) {
                LOGGER.error("ERROR: {}", ex.getLocalizedMessage(), ex);
                throw new RuntimeException("ERROR: " + ex.getLocalizedMessage());
            } finally {
                channelSftp.disconnect();
            }
        } catch (JSchException ex) {
            throw new RuntimeException("ERROR: " + ex.getLocalizedMessage());
        } finally {
            session.disconnect();
        }
    }
}