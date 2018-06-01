package com.muayadsalah.filedownloader.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

/**
 * Created by Muayad Salah
 */
@Document(collection = "file")
public class FileObject {
    @Id
    private String fileId;

    @NotBlank(message = "File url cannot be null or empty")
    private String fileName;

    private FileStatus status = FileStatus.Waiting;

    public FileObject(String fileName) {
        this.fileName = fileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileStatus getStatus() {
        return status;
    }

    public void setStatus(FileStatus status) {
        this.status = status;
    }
}
