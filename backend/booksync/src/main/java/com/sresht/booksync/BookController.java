package com.sresht.booksync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class BookController {
    // insert new book
    // set pdf
    // set page number
    // get page number
    //
    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;


    @Value("${pdf_folder.path")
    String folderPath;

    BookController(BookService bookService){
        this.bookService = bookService;
    }

    @PostMapping("/books/upload")
    public void uploadPdf(@RequestParam("userId") Long userId,
                          @RequestParam("file") MultipartFile file){
        try {
            bookService.insertBook(userId, file);
        }catch(Exception e){
            logger.error("Error in Controller: {}", e.getMessage());
        }
    }
}
