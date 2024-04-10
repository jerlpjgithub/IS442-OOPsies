package com.oopsies.server.services;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopsies.server.entity.Image;
import com.oopsies.server.repository.ImageRepository;

/**
 * Service for handling Image entities.
 * This service provides methods for saving, retrieving, and decoding images.
 */
@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    private static final List<String> CONTENT_TYPES = Arrays.asList(
            "image/png", "image/jpeg", "image/gif");

    /**
     * Saves an image to the database.
     *
     * @param name        The name of the image.
     * @param base64Image The image data as a base64 string.
     * @return The saved Image entity.
     * @throws IllegalArgumentException If the content type of the image is not
     *                                  supported.
     */
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

    /**
     * Retrieves an image by its id.
     *
     * @param id The id of the image to retrieve.
     * @return The retrieved Image entity.
     * @throws RuntimeException If the image is not found.
     */
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
    }

    /**
     * Retrieves an image by its name.
     *
     * @param name The name of the image to retrieve.
     * @return The retrieved Image entity.
     * @throws RuntimeException If the image is not found.
     */
    public Image getImageByName(String name) {
        return imageRepository.findByName(name).orElseThrow(() -> new RuntimeException("Image not found"));
    }

    /**
     * Decodes an image's data.
     *
     * @param image The Image entity to decode.
     * @return The decoded image data as a byte array.
     */
    public byte[] decodeImage(Image image) {
        return Base64.getEncoder().encode(image.getImageData());
    }
}
