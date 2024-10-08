package com.example.doan.service;

import com.example.doan.entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
     List<Book> getListBook(String listBookId);
     void createBook(Book book);
     Book deleteBook(String bookId);
     Book updateBook(Book book);
}
