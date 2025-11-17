package com.notes.mini.service;

import com.notes.mini.dto.NoteRequest;
import com.notes.mini.dto.NoteResponse;
import com.notes.mini.modal.Notes;
import com.notes.mini.repository.NotesRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotesServiceImpl implements NoteService {

    private final NotesRepository repo;
    private final S3StorageService s3;

    public NotesServiceImpl(NotesRepository repo, S3StorageService s3) {
        this.repo = repo;
        this.s3 = s3;
    }

    @Override
    public NoteResponse create(NoteRequest req, MultipartFile file) {
        UUID id = UUID.randomUUID();
        String s3Key = null;
        if (file != null && !file.isEmpty()) {
            try {
                s3Key = s3.upload(file.getInputStream(), file.getSize(), file.getContentType(), file.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read upload stream", e);
            }
        }
        Notes note = new Notes(id, req.getTitle(), req.getBody(), s3Key, Instant.now());
        repo.save(note);
        String s3Url = (s3Key != null) ? s3.getUrl(s3Key) : null;
        return new NoteResponse(note.getId(), note.getTitle(), note.getBody(), s3Url, note.getCreatedAt());
    }

    @Override
    public NoteResponse getById(UUID id) {
        Notes n = repo.findById(id).orElseThrow(() -> new NoteNotFoundException(id));
        String s3Url = (n.getS3Key() != null) ? s3.getUrl(n.getS3Key()) : null;
        return new NoteResponse(n.getId(), n.getTitle(), n.getBody(), s3Url, n.getCreatedAt());
    }

    @Override
    public Page<NoteResponse> search(String query, Pageable pageable) {
        Page<Notes> p;
        if (query == null || query.isBlank()) {
            p = repo.findAll(pageable);
        } else {
            p = repo.findByTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(query, query, pageable);
        }
        return p.map(n -> new NoteResponse(n.getId(), n.getTitle(), n.getBody(),
                n.getS3Key() != null ? s3.getUrl(n.getS3Key()) : null, n.getCreatedAt()));
    }

    public static class NoteNotFoundException extends RuntimeException {
        public NoteNotFoundException(java.util.UUID id) {
            super("Note not found: " + id);
        }
    }
}
