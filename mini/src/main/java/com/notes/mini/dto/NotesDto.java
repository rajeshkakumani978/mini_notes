package com.notes.mini.dto;

import java.util.Objects;

public class NotesDto {
    private Long id;
    private String title;
    private String content;
    private String fileUrl;
    private String s3Key;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getS3Key() {
        return s3Key;
    }

    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        NotesDto notesDto = (NotesDto) object;
        return Objects.equals(id, notesDto.id) && Objects.equals(title, notesDto.title) && Objects.equals(content, notesDto.content) && Objects.equals(fileUrl, notesDto.fileUrl) && Objects.equals(s3Key, notesDto.s3Key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, fileUrl, s3Key);
    }
}
