package com.sresht.booksync;

import com.sresht.booksync.dto.LoginRequest;
import com.sresht.booksync.dto.RegisterRequest;
import com.sresht.booksync.dto.User;
import com.sresht.booksync.securityconfig.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
public class BookController {
    // insert new book
    // set pdf
    // set page number
    // get page number
    //
    private final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @Value("${pdf_folder.path")
    String folderPath;

    BookController(BookService bookService, UserRepository userRepo, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.bookService = bookService;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req){
        if (userRepo.existsByUsername(req.getUsername())) {
            return ResponseEntity.badRequest().body("Username Already Exists!!!!!");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));

        userRepo.save(user);

        return ResponseEntity.ok("You have been registered to this elite club ;)");
    }


    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        User user = userRepo.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid Username hmmmm"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.status(401).body("Invalid password hmmmmmm");
        }

        String token = jwtService.generateToken(user.getUsername());

        return ResponseEntity.ok(Map.of("token", token));
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
