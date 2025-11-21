package com.sresht.booksync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class BookService {

    private final BookRepository bookRepo;

    private final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Value("${pdf_folder.path}")
    private String uploadDir;

    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public Book insertBook(Long userId, MultipartFile file) throws IOException {

        if(Objects.isNull(uploadDir) || uploadDir.isEmpty()){
            logger.error("Upload Directory Path is not found buddy, check config");
        }
        Book b = new Book();
        try{
            Files.createDirectories(Paths.get(uploadDir));

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir, fileName);

            file.transferTo(path.toFile());

            b.setUserId(userId);
            b.setPdfName(file.getOriginalFilename());
            b.setFilePath(path.toString());
            b.setTotalPages(null);
            b.setCurrentPage(1);
        }catch(Exception e){
            logger.error("Error: {}", e.getMessage());
        }

        return bookRepo.save(b);
    }
}
