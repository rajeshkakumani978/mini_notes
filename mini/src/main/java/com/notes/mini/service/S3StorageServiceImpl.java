package com.notes.mini.service;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.InputStream;
import java.util.UUID;

@Service
public class S3StorageServiceImpl implements S3StorageService {
    private final S3Client s3;
    private final String bucket;
    private final String s3UrlPrefix;

    public S3StorageServiceImpl(S3Client s3,
                               @Value("${app.s3.bucket}") String bucket,
                               @Value("${app.s3.url-prefix:}") String s3UrlPrefix) {
        this.s3 = s3;
        this.bucket = bucket;
        this.s3UrlPrefix = s3UrlPrefix;
        ensureBucketExists();
    }

    @PostConstruct
    private void ensureBucketExists() {
        try {
            boolean exists = s3.listBuckets().buckets().stream()
                    .anyMatch(b -> b.name().equals(bucket));
            if (!exists) {
                s3.createBucket(builder -> builder.bucket(bucket));
                System.out.println("Created bucket: " + bucket);
            }
        } catch (S3Exception e) {
            throw new RuntimeException("Failed to create/access bucket: " + bucket, e);
        }
    }

    public String upload(InputStream input, long size, String contentType, String originalFilename) {
        String key =  UUID.randomUUID() + "-" + originalFilename;
        PutObjectRequest req = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .contentLength(size)
                .build();
        s3.putObject(req, RequestBody.fromInputStream(input, size));
        return key;
    }

    @Override
    public String getUrl(String s3Key) {
        if (s3UrlPrefix != null && !s3UrlPrefix.isEmpty()) {
            return s3UrlPrefix + "/" + s3Key;
        }
        return String.format("https://%s.s3.amazonaws.com/%s", bucket, s3Key);
    }
}
