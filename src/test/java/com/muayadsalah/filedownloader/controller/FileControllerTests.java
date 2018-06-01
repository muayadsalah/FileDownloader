package com.muayadsalah.filedownloader.controller;

/**
 * Created by Muayad Salah
 */

import com.muayadsalah.filedownloader.base.TestsMaterial;
import com.muayadsalah.filedownloader.model.FileStatus;
import com.muayadsalah.filedownloader.service.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Collectors;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(FileController.class)
public class FileControllerTests extends TestsMaterial {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private FileService fileService;

    @Test
    public void getWaitingFilesShouldReturnTwoFiles() throws Exception {
        given(fileService.getFileByStatus(FileStatus.Waiting))
                .willReturn(fileObjects.stream().filter(
                        fileObject -> fileObject.getStatus() == FileStatus.Waiting)
                        .collect(Collectors.toList()));

        mvc.perform(get("/api/download/files?status=Waiting"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(status().isOk());
    }

    @Test
    public void changeFileStatusShouldChangeAsExpected() throws Exception {
        doNothing().when(fileService).updateFileStatus("1", FileStatus.Approved);

        mvc.perform(put("/api/download/files/1?status=Waiting"))
                .andExpect(status().isNoContent());
    }
}
