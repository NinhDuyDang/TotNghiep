package com.example.doan.repository;

import com.example.doan.entity.InterativeBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface InterativeBookRepository extends JpaRepository<InterativeBook, Integer> {
    List<InterativeBook> findDistinctByBookId(Integer bookId);
}