package com.muayadsalah.filedownloader.controller;

import com.muayadsalah.filedownloader.model.FileHolder;
import com.muayadsalah.filedownloader.model.FileObject;
import com.muayadsalah.filedownloader.model.FileStatus;
import com.muayadsalah.filedownloader.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Muayad Salah
 */

@RestController
@RequestMapping("/api/download")
public class FileController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> downloadFiles(@RequestBody List<FileHolder> fileHolders) {
        return fileService.downloadFiles(fileHolders);
    }

    @PutMapping("/files/{fileId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateFileStatus(@PathVariable String fileId, @RequestParam FileStatus status) {
        fileService.updateFileStatus(fileId, status);
    }

    @GetMapping("/files")
    public List<FileObject> getFileByStatus(@RequestParam FileStatus status) {
        return fileService.getFileByStatus(status);
    }
}
