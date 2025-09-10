package com.idn.backend.Services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class GCSService {

    @Value("${gcp.bucket.name}")
    private String bucketName;

    private final Storage storage;

    public GCSService(Storage storage) {
        this.storage = storage;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String extension = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        } else {
            extension = "jpg";
        }

        String fileName = System.currentTimeMillis() + "-" + originalFileName;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(file.getInputStream())
                .size(800, 800)
                .outputQuality(0.7)
                .outputFormat(extension)
                .toOutputStream(outputStream);

        byte[] compressedBytes = outputStream.toByteArray();

        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        storage.create(blobInfo, compressedBytes);

        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }

}
