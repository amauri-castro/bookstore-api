package com.bookstore.jpa.repositories;

import com.bookstore.jpa.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    Book findBookByTitle(String title);

}
