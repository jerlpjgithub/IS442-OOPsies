package com.oopsies.server.controller;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oopsies.server.entity.Image;
import com.oopsies.server.payload.response.MessageResponse;
import com.oopsies.server.services.ImageService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        MessageResponse response = imageService.saveImage(file);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getImageByName(@PathVariable("name") String name) {
        try {
            Image image = imageService.getInfoByImageByName(name);
            byte[] imageData = image.getImageData();
            String contentType = image.getType();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(contentType))
                    .body(imageData);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }

}
