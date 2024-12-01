package com.szakdologzat.repiceapp.web.rest;

import com.szakdologzat.repiceapp.SzakdolgozatApp;
import com.szakdologzat.repiceapp.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/images")
public class ImageResource {

    private final ImageService imageService;
    private static final Logger LOG = LoggerFactory.getLogger(SzakdolgozatApp.class);
    private static final Integer URL_PORT = 8080;

    public ImageResource(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (file == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            String path = imageService.uploadImage(file);

            // Abszolút URL létrehozása a képhez
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().port(URL_PORT).build().toUriString();
            String imageUrl = baseUrl + "/" + path;
            LOG.info("Success uploading image");
            return new ResponseEntity<>(imageUrl, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Error uploading image", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
