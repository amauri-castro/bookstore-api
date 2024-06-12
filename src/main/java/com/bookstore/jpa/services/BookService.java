package com.bookstore.jpa.services;

import com.bookstore.jpa.dtos.BookRecordDTO;
import com.bookstore.jpa.models.Book;
import com.bookstore.jpa.models.Review;
import com.bookstore.jpa.repositories.AuthorRepository;
import com.bookstore.jpa.repositories.BookRepository;
import com.bookstore.jpa.repositories.PublisherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final PublisherRepository publisherRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
    }

    @Transactional
    public Book save(BookRecordDTO bookRecordDTO){
        Book book = new Book();
        book.setTitle(bookRecordDTO.title());
        book.setPublisher(publisherRepository.findById(bookRecordDTO.publisherId()).orElseThrow(() -> new EntityNotFoundException("publisher not found")));
        book.setAuthors(new HashSet<>(authorRepository.findAllById(bookRecordDTO.authorIds())));

        Review review = new Review();
        review.setComment(bookRecordDTO.reviewComment());
        review.setBook(book);
        book.setReview(review);

        return bookRepository.save(book);
    }


}
