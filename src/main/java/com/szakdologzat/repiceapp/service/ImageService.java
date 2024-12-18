package com.szakdologzat.repiceapp.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    @Value("${photo.storage.location}")
    private String staticLocations;

    public String getStaticLocations() {
        return staticLocations;
    }

    public void setStaticLocations(String staticLocations) {
        this.staticLocations = staticLocations;
    }

    public String uploadImage(MultipartFile file) {
        try {
            Path uploadPath = Path.of(staticLocations);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = dateFormat.format(new Date());

            Path datePath = uploadPath.resolve(currentDate);

            if (!Files.exists(datePath)) {
                Files.createDirectories(datePath);
            }
            String filename = UUID.randomUUID() + ".jpg";
            Path targetPath = datePath.resolve(filename);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            return targetPath.toString().replace("\\", "/");
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }
}
