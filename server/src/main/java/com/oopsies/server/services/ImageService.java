package com.oopsies.server.services;

import java.util.List;
import java.util.Optional;
import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.oopsies.server.entity.Image;
import com.oopsies.server.payload.response.MessageResponse;
import com.oopsies.server.repository.ImageRepository;
import com.oopsies.server.util.ImageUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    private static final List<String> CONTENT_TYPES = Arrays.asList(
            "image/png", "image/jpeg", "image/gif");

    public MessageResponse saveImage(MultipartFile file) throws IOException {
        // Ensure the file has a supported content type
        String contentType = file.getContentType();
        if (contentType == null || !CONTENT_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("Invalid file type. Only PNG, JPEG, and GIF images are allowed.");
        }

        Image img = new Image();
        img.setName(file.getOriginalFilename());
        img.setType(contentType);
        img.setImageData(ImageUtil.compressImage(file.getBytes()));

        // Save the image entity to the database
        imageRepository.save(img);
        System.out.println("reached");

        // Message
        return new MessageResponse("Image uploaded successfully: " + file.getOriginalFilename());
    }

    @Transactional
    public Image getInfoByImageByName(String name) {
        Optional<Image> dbImage = imageRepository.findByName(name);

        if (dbImage.isPresent()) {
            Image image = dbImage.get();
            image.setImageData(ImageUtil.decompressImage(image.getImageData()));
            return image;
        } else {
            throw new EntityNotFoundException("Image with name " + name + " not found");
        }
    }

    @Transactional
    public byte[] getImage(String name) {
        Optional<Image> dbImage = imageRepository.findByName(name);

        if (dbImage.isPresent()) {
            return ImageUtil.decompressImage(dbImage.get().getImageData());
        } else {
            throw new EntityNotFoundException("Image with name " + name + " not found");
        }
    }

}
