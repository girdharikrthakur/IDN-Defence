package com.idn.backend.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idn.backend.Services.GCSService;

@RestController
@RequestMapping("/media")
public class MediaController {

    private final GCSService gcsService;

    public MediaController(GCSService gcsService) {
        this.gcsService = gcsService;
    }

    @PostMapping(consumes = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<String> uploadJpeg(@RequestBody byte[] fileBytes) {
        try {
            String fileName = gcsService.uploadFileBytes(fileBytes, "image.jpg");
            return ResponseEntity.ok("Uploaded: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Upload failed: " + e.getMessage());
        }
    }

}
