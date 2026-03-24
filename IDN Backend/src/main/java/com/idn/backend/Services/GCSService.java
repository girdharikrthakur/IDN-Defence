package com.idn.backend.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface GCSService {

    public String uploadFile(MultipartFile file) throws IOException;

    public String uploadImage(MultipartFile file) throws IOException;

}
