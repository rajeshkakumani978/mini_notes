package com.notes.mini.dto;


import java.time.Instant;
import java.util.UUID;

public class NoteResponse {
    private UUID id;
    private String title;
    private String body;
    private String s3Url;
    private Instant createdAt;

    public NoteResponse() {}

    public NoteResponse(UUID id, String title, String body, String s3Url, Instant createdAt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.s3Url = s3Url;
        this.createdAt = createdAt;
    }

    // getters/setters omitted for brevity
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    public String getS3Url() { return s3Url; }
    public void setS3Url(String s3Url) { this.s3Url = s3Url; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
