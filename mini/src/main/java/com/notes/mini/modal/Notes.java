package com.notes.mini.modal;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "notes")
public class Notes {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(length = 140, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(name = "s3_key")
    private String s3Key;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected Notes() {}

    public Notes(UUID id, String title, String body, String s3Key, Instant createdAt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.s3Key = s3Key;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    public String getS3Key() { return s3Key; }
    public void setS3Key(String s3Key) { this.s3Key = s3Key; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
