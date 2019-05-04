package com.sendfriend.controllers;

import com.sendfriend.repository.ImageRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class ImageControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ImageRepository imageRepository;

    private MockMvc mockMvc;

    @Before
    void setUp() {
        imageRepository = mock(ImageRepository.class);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void displayImageIndex() throws Exception {
        mockMvc.perform(get("/image"))
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("image"))
                .andExpect(status().isOk());
    }

    @Test
    void processImageUpload() throws Exception {

        MockMultipartFile badFile = new MockMultipartFile("file", ".htaccess", "html/text", new byte[80]);
        MockMultipartFile emptyFile = new MockMultipartFile("file", "HorseShoeCanyon.jpeg", "image/jpeg", new byte[]{});
        MockMultipartFile goodFile = new MockMultipartFile("file", "HorseShoeCanyon.jpeg", "image/jpeg", new byte[80]);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/image")
                .file(badFile))
                    .andExpect(flash().attribute("message", "Please select an image file for the upload."))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/image"));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/image")
                .file(emptyFile))
                    .andExpect(flash().attribute("message", "Please select an image file for the upload."))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/image"));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/image")
                .file(goodFile))
                .andExpect(flash().attribute("message", "Upload successful!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/image"));
    }
}