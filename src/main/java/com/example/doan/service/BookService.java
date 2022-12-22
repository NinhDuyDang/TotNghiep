package com.example.doan.service;

import com.example.doan.entity.Book;
import com.example.doan.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    public List<Book> getListBook(String listBookId){
        List<String> listBook = Arrays.asList(listBookId.split("-"));
        String str = listBookId.replace("-", ",");

        List<Book> listBookRes = bookRepository.getListBook(listBook, str);
        System.out.println("so luong book chua tuong tac: " + listBook.size());
        System.out.println(str);
        return listBookRes;
    }
}
