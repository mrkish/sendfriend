package com.sendfriend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface UploadService {

    boolean processUpload(MultipartFile file) throws IOException;

}
