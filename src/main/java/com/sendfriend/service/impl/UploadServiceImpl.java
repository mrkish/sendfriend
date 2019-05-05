package com.sendfriend.service.impl;

import com.sendfriend.data.ImageDao;
import com.sendfriend.models.Image;
import com.sendfriend.service.UploadService;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static com.sendfriend.util.AppConstants.DISALLOWED_FILE_TYPES;

public class UploadServiceImpl implements UploadService {

    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif");
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadServiceImpl.class);

    private ImageDao imageDao;

    public UploadServiceImpl(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    public boolean processUpload(MultipartFile file) throws IOException {

        boolean result = false;
        boolean imageFileTypeCheck = ALLOWED_MIME_TYPES.contains(detectMimeType(file.getInputStream(), file.getContentType()));
        boolean securityCheck = (!DISALLOWED_FILE_TYPES.contains(file.getName()) ||
                !DISALLOWED_FILE_TYPES.contains(file.getOriginalFilename()));

        if (imageFileTypeCheck && securityCheck) {
            try {
                Image newImage = new Image(file.getBytes());
                newImage.setFileName(file.getName());
                imageDao.save(newImage);

                LOGGER.info("Saving image to database | Size : {} | Filename : {}", file.getSize(), file.getName());
                result = true;
            } catch (IOException e) {
                LOGGER.warn("File upload rejected | Size: {} | Filename : {} | ContentType : {}",
                        file.getSize(),
                        file.getName(),
                        file.getContentType());
            }
        }
        return result;
    }

    private static String detectMimeType(InputStream inputStream, String fileName) throws IOException {
        final Detector detector = new DefaultDetector(MimeTypes.getDefaultMimeTypes());

        TikaInputStream tikaInputStream = TikaInputStream.get(inputStream);
        Metadata metadata = new Metadata();
        metadata.add(Metadata.RESOURCE_NAME_KEY, fileName);

        return detector.detect(tikaInputStream, metadata).toString();
    }
}
