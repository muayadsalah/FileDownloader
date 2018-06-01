package com.muayadsalah.filedownloader.repository;

import com.muayadsalah.filedownloader.model.FileObject;
import com.muayadsalah.filedownloader.model.FileStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Muayad Salah
 */
@Repository
public interface FileRepository extends CrudRepository<FileObject, String> {
    List<FileObject> findByStatus(FileStatus status);
}
