package com.oopsies.server.services;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopsies.server.entity.Image;
import com.oopsies.server.repository.ImageRepository;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    private static final List<String> CONTENT_TYPES = Arrays.asList(
            "image/png", "image/jpeg", "image/gif");

    public Image saveImage(String name, String base64Image) {
        String contentType = "";
        if (base64Image.contains(":") && base64Image.contains(";")) {
            // Extract the content type from the base64 string
            contentType = base64Image.substring(base64Image.indexOf(":") + 1, base64Image.indexOf(";"));
        }

        // Check if the content type is supported
        if (!contentType.isEmpty() && !CONTENT_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("Unsupported content type: " + contentType);
        }

        // Extract the base64 data
        String base64Data = base64Image.substring(base64Image.indexOf(",") + 1);

        byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
        Image image = new Image();
        image.setName(name);
        image.setImageData(decodedBytes);
        return imageRepository.save(image);
    }

    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
    }

    public Image getImageByName(String name) {
        return imageRepository.findByName(name).orElseThrow(() -> new RuntimeException("Image not found"));
    }

    public byte[] decodeImage(Image image) {
        return Base64.getEncoder().encode(image.getImageData());
    }
}
