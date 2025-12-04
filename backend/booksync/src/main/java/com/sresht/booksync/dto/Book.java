package com.sresht.booksync.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {

    // getters & setters ↓↓↓
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(name = "user_id")
    private Long userId;

    @Setter
    @Column(name = "pdf_name", nullable = false)
    private String pdfName;

    @Setter
    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "total_pages")
    private Integer totalPages;

    @Setter
    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt = LocalDateTime.now();
}
