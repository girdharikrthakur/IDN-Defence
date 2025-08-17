package com.idn.backend.Services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

@Service
public class GCSService {

    @Value("${gcp.bucket.name}")
    private String bucketName;

    private final Storage storage;

    public GCSService(Storage storage) {
        this.storage = storage;
    }

    public String uploadFileBytes(byte[] data, String fileName) throws IOException {
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, data);
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

        // Use @Value bucketName instead of hardcoded
        BlobId blobId = BlobId.of(bucketName, fileName);

        String contentType = file.getContentType() != null ? file.getContentType() : "application/octet-stream";

        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();
        storage.create(blobInfo, file.getBytes());

        // ✅ Return full URL, now using actual bucket name
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }
}
