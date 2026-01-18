package com.sresht.booksync;

import com.sresht.booksync.dto.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByUserId(Long userId);

    Optional<Book> findByIdAndUserId(Long id, Long userId);

}
