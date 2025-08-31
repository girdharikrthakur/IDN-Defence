package com.idn.backend.Services;

import java.io.IOException;

<<<<<<< HEAD
=======
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> 4b67cafe63b444f2d2e9ac432da94992e59320f0
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

<<<<<<< HEAD
=======
import com.google.cloud.storage.Acl;
>>>>>>> 4b67cafe63b444f2d2e9ac432da94992e59320f0
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

@Service
public class GCSService {

    @Value("${gcp.bucket.name}")
    private String bucketName;

<<<<<<< HEAD
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

        BlobId blobId = BlobId.of(bucketName, fileName);

        String contentType = file.getContentType() != null ? file.getContentType() : "application/octet-stream";

        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(contentType).build();
        storage.create(blobInfo, file.getBytes());

        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }
=======
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

>>>>>>> 4b67cafe63b444f2d2e9ac432da94992e59320f0
}
