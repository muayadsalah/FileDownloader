package com.muayadsalah.filedownloader.service;

import com.muayadsalah.filedownloader.model.FileHolder;
import com.muayadsalah.filedownloader.model.FileObject;
import com.muayadsalah.filedownloader.model.FileStatus;

import java.util.List;

/**
 * Created by Muayad Salah
 */
public interface FileService {
    List<String> downloadFiles(List<FileHolder> files);

    void updateFileStatus(String fileId, FileStatus status);

    List<FileObject> getFileByStatus(FileStatus status);

}
