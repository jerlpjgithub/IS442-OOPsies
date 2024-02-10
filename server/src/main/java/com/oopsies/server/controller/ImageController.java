package com.oopsies.server.controller;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oopsies.server.entity.Image;
import com.oopsies.server.services.ImageService;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<Image> uploadImage(@RequestParam("name") String name,
            @RequestParam("image") String base64Image) {
        Image storedImage = imageService.saveImage(name, base64Image);
        return ResponseEntity.ok(storedImage);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getImageByName(@PathVariable String name) {
        Image image = imageService.getImageByName(name);
        byte[] encodedImage = imageService.decodeImage(image); // This is actually encoding to Base64

        // Convert byte[] to String for direct use in the frontend
        String base64Image = new String(encodedImage, StandardCharsets.UTF_8);

        Map<String, String> body = new HashMap<>();
        body.put("name", image.getName());
        body.put("imageData", base64Image);

        // Utilise the base 64 by doing <img src={response.imageData} alt="description" />
        return ResponseEntity.ok().body(body);
    }

}
