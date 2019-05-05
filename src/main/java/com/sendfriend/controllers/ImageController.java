package com.sendfriend.controllers;

import com.sendfriend.data.ImageDao;
import com.sendfriend.models.Image;
import com.sendfriend.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "image")
public class ImageController {

    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif");
    private Logger logger = LoggerFactory.getLogger(ImageController.class);

    private ImageDao imageDao;
    private UploadService uploadService;

    public ImageController(ImageDao imageDao,
                           UploadService uploadService) {
        this.imageDao = imageDao;
        this.uploadService = uploadService;
    }

    public String displayImageIndex(Model model, HttpServletRequest request) {
        model.addAttribute("title", "Image upload");
        return "image";
    }

    @PostMapping(value = "upload")
    public String processImageUpload(HttpServletRequest request, RedirectAttributes redirectAttributes, MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select an image file for uploading.");
            return "redirect:image";
        }
        if (uploadService.processUpload(file)) {
            redirectAttributes.addFlashAttribute("message", "Upload successful!");
        }

        return "redirect:image";
    }

    @GetMapping(value = "/imageget/{image_id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("image_id") Integer imageId) {

        HttpHeaders httpHeaders = new HttpHeaders();

        Optional<Image> requested = imageDao.findById(imageId);
        if (requested.isPresent()) {
            byte[] imageContent = requested.get().getFileBytes();
            httpHeaders.setCacheControl(CacheControl.noCache().getHeaderValue());
            return new ResponseEntity<>(imageContent, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
