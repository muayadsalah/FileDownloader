package com.muayadsalah.filedownloader.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muayadsalah.filedownloader.model.FileObject;
import com.muayadsalah.filedownloader.model.FileStatus;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muayad Salah
 */
public class TestsMaterial {
    private static final ObjectMapper mapper = new ObjectMapper();

    protected List<FileObject> fileObjects;

    @Before
    public void testSetup() {
        fileObjects = new ArrayList<FileObject>();
        fileObjects.add(new FileObject("file1"));
        fileObjects.add(new FileObject("file2"));
        fileObjects.add(new FileObject("file3"));
        fileObjects.add(new FileObject("file4"));
        long i = 1L;
        for (FileObject fileObject : fileObjects) {
            fileObject.setFileId(i + "");
        }
        fileObjects.get(0).setStatus(FileStatus.Approved);

        fileObjects.get(3).setStatus(FileStatus.Rejected);
    }

}
