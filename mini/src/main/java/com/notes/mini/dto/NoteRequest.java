package com.notes.mini.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class NoteRequest {

    @NotBlank
    @Size(min = 1, max = 140)
    private String title;

    private String body;

    private String filename;
    private String s3Url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getS3Url() {
        return s3Url;
    }

    public void setS3Url(String s3Url) {
        this.s3Url = s3Url;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        NoteRequest that = (NoteRequest) object;
        return Objects.equals(title, that.title) && Objects.equals(body, that.body) && Objects.equals(filename, that.filename) && Objects.equals(s3Url, that.s3Url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, body, filename, s3Url);
    }
}
