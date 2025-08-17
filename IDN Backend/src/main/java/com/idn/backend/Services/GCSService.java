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
        return fileName;
    }

    public String uploadFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            if (fileName == null || fileName.isEmpty()) {
                throw new IllegalArgumentException("File name cannot be null or empty");
            }

            BlobId blobId = BlobId.of(bucketName, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(file.getContentType())
                    .build();

            storage.create(blobInfo, file.getBytes());
            return fileName;

        } catch (IOException e) {
            throw new RuntimeException("Error uploading file to GCS", e);
        }
    }

}
