package com.muayadsalah.filedownloader.service;

import com.muayadsalah.filedownloader.base.TestsMaterial;
import com.muayadsalah.filedownloader.model.FileStatus;
import com.muayadsalah.filedownloader.repository.FileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Created by Muayad Salah
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileServiceTests extends TestsMaterial {
    @Autowired
    private FileService fileService;

    @MockBean
    private FileRepository fileRepository;

    @Test
    public void findAllWaitingFilesShouldReturnTwoWaitingFiles() {
        given(fileRepository.findByStatus(FileStatus.Waiting))
                .willReturn(fileObjects.stream().filter(
                        fileObject -> fileObject.getStatus() == FileStatus.Waiting)
                        .collect(Collectors.toList()));

        assertThat(fileService.getFileByStatus(FileStatus.Waiting).size()).isEqualTo(2);
    }
}
