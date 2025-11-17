package com.notes.mini.controller;

import com.notes.mini.dto.NoteRequest;
import com.notes.mini.dto.NoteResponse;
import com.notes.mini.modal.Notes;
import com.notes.mini.service.NoteService;
import com.notes.mini.service.S3StorageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/api/notes")
@Validated
public class NotesController {

    private final NoteService svc;

    public NotesController(NoteService svc) {
        this.svc = svc;
    }


    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<NoteResponse> createJson(@RequestBody @Valid NoteRequest req) {
        NoteResponse resp = svc.create(req, null);
        return ResponseEntity.ok(resp);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<NoteResponse> createNote(
        @RequestPart("note") @Valid NoteRequest req,
        @RequestPart(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
        NoteResponse resp = svc.create(req, file);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteResponse> getById(@PathVariable("id") UUID id) {
        NoteResponse r = svc.getById(id);
        return ResponseEntity.ok(r);
    }

    @GetMapping("/get/notes")
    public ResponseEntity<?> search(@RequestParam(value = "query", required = false) String query,
                                    @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(svc.search(query, pageable));
    }

    @GetMapping
    public ModelAndView get(){
        return new ModelAndView("notes");
    }
}
