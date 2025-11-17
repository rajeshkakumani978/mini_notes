package com.notes.mini.service;

import com.notes.mini.dto.NoteRequest;
import com.notes.mini.dto.NoteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface NoteService {
    NoteResponse create(NoteRequest req, MultipartFile file);
    NoteResponse getById(UUID id);
    Page<NoteResponse> search(String query, Pageable pageable);
}
