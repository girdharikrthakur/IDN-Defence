package com.idn.backend.Services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

@Service
public class GCSService {

    @Value("${gcp.bucket.name}")
    private String bucketName;

    @Autowired
    private Storage storage;

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        // Upload the file
        storage.create(blobInfo, file.getBytes());

        // Make the file public
        storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        // Return the public URL
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }

}
