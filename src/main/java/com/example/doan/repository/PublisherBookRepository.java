package com.example.doan.repository;

import com.example.doan.entity.PublisherBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherBookRepository extends JpaRepository<PublisherBook, String> {
}