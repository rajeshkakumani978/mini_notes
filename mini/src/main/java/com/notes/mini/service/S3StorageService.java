package com.notes.mini.service;

import java.io.InputStream;

public interface S3StorageService {

    String upload(InputStream data, long contentLength, String contentType, String filename);

    String getUrl(String s3Key);
}
