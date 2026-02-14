package com.idn.backend.Config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class GCSConfig {

    @Value("${gcp.credentials.path}")
    private String credentialsPath;

    @Value("${gcp.bucket.name}")
    private String bucketName;

    @Bean
    public Storage storage() throws IOException {
        InputStream credentialsStream;

        if (credentialsPath.startsWith("classpath:")) {
            String resourcePath = credentialsPath.replace("classpath:", "");
            credentialsStream = getClass().getResourceAsStream("/" + resourcePath);
        } else {
            credentialsStream = new FileInputStream(credentialsPath);
        }

        if (credentialsStream == null) {
            throw new IllegalStateException("GCP credentials file not found at: " + credentialsPath);
        }

        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);
        return StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();
    }

    public String getBucketName() {
        return bucketName;
    }
}
