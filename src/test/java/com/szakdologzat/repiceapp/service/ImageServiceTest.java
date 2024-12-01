package com.szakdologzat.repiceapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;

class ImageServiceTest {

    @Mock
    private MultipartFile mockFile;

    @InjectMocks
    private ImageService imageService;

    private String staticLocations = "uploads";

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        when(mockFile.getInputStream()).thenReturn(getClass().getResourceAsStream("/test-image.jpg"));
        when(mockFile.getOriginalFilename()).thenReturn("test-image.jpg");

        imageService.setStaticLocations(staticLocations);
    }

    @Test
    void testUploadImageSuccess() throws IOException {
        UUID mockUUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        try (MockedStatic<UUID> mockedUUID = mockStatic(UUID.class)) {
            mockedUUID.when(UUID::randomUUID).thenReturn(mockUUID);

            Path uploadPath = Paths.get("uploads");
            Path datePath = uploadPath.resolve(
                ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            );

            try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
                mockedFiles.when(() -> Files.exists(uploadPath)).thenReturn(false);
                mockedFiles.when(() -> Files.exists(datePath)).thenReturn(false);

                mockedFiles.when(() -> Files.createDirectories(uploadPath)).thenReturn(uploadPath);
                mockedFiles.when(() -> Files.createDirectories(datePath)).thenReturn(datePath);

                String randomFilename = mockUUID + ".jpg";
                Path targetPath = datePath.resolve(randomFilename);

                mockedFiles
                    .when(() -> Files.copy((Path) any(), eq(targetPath), eq(StandardCopyOption.REPLACE_EXISTING)))
                    .thenReturn(targetPath);

                String result = imageService.uploadImage(mockFile);

                assertNotNull(result);
                assertTrue(result.contains("uploads"));

                mockedFiles.verify(() -> Files.createDirectories(uploadPath), times(1));
                mockedFiles.verify(() -> Files.createDirectories(datePath), times(1));
            }
        }
    }

    @Test
    void testUploadImageDirectoryExists() throws Exception {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            Path uploadPath = Paths.get("uploads");
            Path datePath = uploadPath.resolve("2024-11-29");

            mockedFiles.when(() -> Files.exists(uploadPath)).thenReturn(true);
            mockedFiles.when(() -> Files.exists(datePath)).thenReturn(true);

            String randomFilename = UUID.randomUUID() + ".jpg";
            Path targetPath = datePath.resolve(randomFilename);

            mockedFiles
                .when(() -> Files.copy((Path) any(), eq(targetPath), eq(StandardCopyOption.REPLACE_EXISTING)))
                .thenReturn(targetPath);

            String result = imageService.uploadImage(mockFile);
            assertNotNull(result);

            mockedFiles.verify(() -> Files.createDirectories(uploadPath), never());
            mockedFiles.verify(() -> Files.createDirectories(datePath), never());
        }
    }
}
