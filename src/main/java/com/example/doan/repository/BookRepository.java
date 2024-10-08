package com.example.doan.repository;

import com.example.doan.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = "SELECT * FROM book WHERE book_id IN :x ORDER BY FIND_IN_SET(book_id,:y)", nativeQuery = true)
    List<Book> getListBook(@Param("x") List<String> listBook,
                           @Param("y") String y);



}
