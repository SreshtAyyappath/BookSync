package com.sresht.booksync;

import com.sresht.booksync.dto.Book;
import com.sresht.booksync.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepo;

    private final UserRepository userRepo;

    private final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Value("${pdf_folder.path}")
    private String uploadDir;

    public BookService(BookRepository bookRepo, UserRepository userRepo) {
        this.bookRepo = bookRepo;
        this.userRepo = userRepo;
    }

    public Book insertBook(String userName, MultipartFile file) throws IOException {

        if(Objects.isNull(uploadDir) || uploadDir.isEmpty()){
            logger.error("Upload Directory Path is not found buddy, check config");
        }
        Book b = new Book();
        try{
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

            Files.createDirectories(uploadPath);

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path path = uploadPath.resolve(fileName);

            file.transferTo(path.toFile());


            User user = userRepo.findByUsername(userName)
                    .orElseThrow(() -> new RuntimeException("Invalid Username hmmmm"));
            b.setUserId(user.getId());
            b.setPdfName(file.getOriginalFilename());
            b.setFilePath(path.toString());
            b.setTotalPages(null);
        }catch(Exception e){
            logger.error("Error: {}", e.getMessage());
        }

        return bookRepo.save(b);
    }

    public Long getUserIdByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();
    }

    public List<Book> getBooks(String userName){
        User user  = userRepo.findByUsername(userName)
                .orElseThrow();

        return bookRepo.findByUserId(user.getId());
    }

    public Book getBookByIdAndUser(Long id, String username){
        Long userID = getUserIdByUsername(username);
        Book book = bookRepo.findByIdAndUserId(id, userID).orElseThrow(() -> new RuntimeException("Book not found"));
        return book;
    }
}
