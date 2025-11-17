package com.notes.mini.repository;

import com.notes.mini.modal.Notes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotesRepository extends JpaRepository<Notes, UUID> {
    Page<Notes> findByTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(String title, String body, Pageable pageable);
}
