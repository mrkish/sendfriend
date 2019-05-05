package com.sendfriend.service;

import com.sendfriend.data.ImageDao;
import com.sendfriend.service.impl.UploadServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
class UploadServiceTest {

    private ImageDao imageDao;

    public UploadServiceImpl cut;

    @BeforeEach
    void setUp() {
        imageDao = mock(ImageDao.class);
        cut = new UploadServiceImpl(imageDao);
    }

    @Test
    void processUploadTest_ExpectEmptyFileFailure() throws IOException {

        MockMultipartFile file = new MockMultipartFile("name", "name", "image/jpeg", new byte[]{});
        boolean result = cut.processUpload(file);

        assertThat(result).as("Empty files are not accepted")
                .isFalse();
    }

    @Test
    void processUploadTest_ExpectSecurityFailure() throws IOException {
        MockMultipartFile file = new MockMultipartFile(".htacceess", ".htacacess", "text/plain", new byte[80]);
        boolean result = cut.processUpload(file);

        assertThat(result).as("Security failure due to file name")
                .isFalse();
    }

    @Test
    void processUploadTest_ExpectContentTypeFailure() throws IOException {
        MockMultipartFile file = new MockMultipartFile("image.jpg", "image.jpg", "text/plain", new byte[80]);
        boolean result = cut.processUpload(file);

        assertThat(result).as("Disallowed file types are rejected")
                .isFalse();
    }

    @Test
    void processUploadTest_ExpectSuccess() throws IOException {
        MockMultipartFile file = new MockMultipartFile("image.jpg", "image.jpg", "image/jpeg", new byte[80]);
        boolean result = cut.processUpload(file);

        assertThat(result).as("Disallowed file types are rejected")
                .isFalse();
    }

}