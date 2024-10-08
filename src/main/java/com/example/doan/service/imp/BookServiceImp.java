package com.example.doan.service.imp;

import com.example.doan.entity.Book;
import com.example.doan.entity.User;
import com.example.doan.repository.BookRepository;
import com.example.doan.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImp implements BookService {
    @Autowired
    private BookRepository bookRepository;
    public List<Book> getListBook(String listBookId) {
        List<String> listBook = Arrays.asList(listBookId.split("-"));
        String str = listBookId.replace("-", ",");

        List<Book> listBookRes = bookRepository.getListBook(listBook, str);
        System.out.println("so luong book chua tuong tac: " + listBook.size());
        System.out.println(str);
        return listBookRes;
    }

    @Override
    public void createBook(Book book) {
        bookRepository.save(book);

    }

    @Override
    public Book deleteBook(String bookId) {
        return null;
    }

    @Override
    public Book updateBook(Book book) {
        return null;
    }


}
