package com.muayadsalah.filedownloader.service;

import com.muayadsalah.filedownloader.downloader.BaseFileDownloader;
import com.muayadsalah.filedownloader.downloader.FtpFileDownloader;
import com.muayadsalah.filedownloader.downloader.HttpFileDownloader;
import com.muayadsalah.filedownloader.downloader.SftpFileDownloader;
import com.muayadsalah.filedownloader.exception.EntityNotFoundException;
import com.muayadsalah.filedownloader.model.FileHolder;
import com.muayadsalah.filedownloader.model.FileObject;
import com.muayadsalah.filedownloader.model.FileStatus;
import com.muayadsalah.filedownloader.model.Protocols;
import com.muayadsalah.filedownloader.repository.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Muayad Salah
 */

@Service
public class FileServiceImpl implements FileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

    private final FileRepository fileRepository;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public List<String> downloadFiles(List<FileHolder> fileHolders) {
        fileHolders.forEach(FileHolder::generateFileHolderProperties);


        List<BaseFileDownloader> baseFileDownloaders = fileHolders.stream().map(fileHolder -> {
            if (fileHolder.getProtocol().equals(Protocols.HTTP.toString()))
                return new HttpFileDownloader(fileHolder);
            else if (fileHolder.getProtocol().equals(Protocols.FTP.toString()))
                return new FtpFileDownloader(fileHolder);
            else if (fileHolder.getProtocol().equals(Protocols.SFTP.toString()))
                return new SftpFileDownloader(fileHolder);
            else {
                String error = "Protocol is not supported";
                LOGGER.error("ERROR: {}", error);
                throw new RuntimeException(error);
            }
        }).collect(Collectors.toList());


        baseFileDownloaders.forEach(baseFileDownloader -> {
            try {
                baseFileDownloader.downloadFile();
                FileObject fileObject = new FileObject(baseFileDownloader.getFileHolder().getOutputFileName());
                fileRepository.save(fileObject);
            } catch (IOException ex) {
                LOGGER.error("ERROR: {}", ex.getLocalizedMessage(), ex);
            }
        });

        return fileHolders.stream().map(FileHolder::getOutputFileName).collect(Collectors.toList());
    }

    @Override
    public void updateFileStatus(String fileId, FileStatus status) throws EntityNotFoundException {
        Optional<FileObject> fileOptional = fileRepository.findById(fileId);
        if (!fileOptional.isPresent())
            throw new EntityNotFoundException(FileObject.class, fileId);

        FileObject fileObject = fileOptional.get();
        fileObject.setStatus(status);
        fileRepository.save(fileObject);
    }

    @Override
    public List<FileObject> getFileByStatus(FileStatus status) {
        List<FileObject> files = new ArrayList<>();
        fileRepository.findByStatus(status).forEach(files::add);
        return files;
    }
}
