package com.sendfriend.controllers;

import com.sendfriend.domain.Image;
import com.sendfriend.repository.ImageRepository;
import com.sendfriend.util.AppConstants;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "image")
public class ImageController {

    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif");
    private Logger logger = LoggerFactory.getLogger(ImageController.class);

    private ImageRepository imageRepository;

    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public String displayImageIndex(Model model, HttpServletRequest request) {
        model.addAttribute("title", "Image upload");
        return "image";
    }

    @PostMapping(value = "upload")
    public String processImageUpload(HttpServletRequest request, RedirectAttributes redirectAttributes, MultipartFile file) throws IOException {
        boolean imageFileTypeCheck = ALLOWED_MIME_TYPES.contains(detectMimeType(file.getInputStream(), file.getOriginalFilename()));

        if ((file.isEmpty() || (!imageFileTypeCheck)) ||
                AppConstants.DISALLOWD_FILE_TYPES.contains(file.getName())) {
            logger.error("Rejected file import from IP address: {}", request.getRemoteAddr());
            redirectAttributes.addFlashAttribute("message", "Please select an image file for uploading.");
            return "redirect:image";
        }

        try {
            Image newImage = new Image();
            newImage.setFileBytes(file.getBytes());
            newImage.setFileName(file.getOriginalFilename());
            imageRepository.save(newImage);
            redirectAttributes.addFlashAttribute("message", "Upload successful!");
        } catch (IOException e)  {
            logger.error("Failed to upload image named : {} : from IP address: {}", file.getOriginalFilename(), request.getRemoteAddr());
        }

        return "redirect:image";
    }

    @GetMapping(value = "/imageget/{image_id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("image_id") Integer imageId) {

        HttpHeaders httpHeaders = new HttpHeaders();

        Optional<Image> requested = imageRepository.findById(imageId);
        if (requested.isPresent()) {
            byte[] imageContent = requested.get().getFileBytes();
            httpHeaders.setCacheControl(CacheControl.noCache().getHeaderValue());
            return new ResponseEntity<>(imageContent, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private static String detectMimeType(InputStream inputStream, String fileName) throws  IOException {
        final Detector detector = new DefaultDetector(MimeTypes.getDefaultMimeTypes());

        TikaInputStream tikaInputStream = TikaInputStream.get(inputStream);
        Metadata metadata = new Metadata();
        metadata.add(Metadata.RESOURCE_NAME_KEY, fileName);

        return detector.detect(tikaInputStream, metadata).toString();
    }
}
