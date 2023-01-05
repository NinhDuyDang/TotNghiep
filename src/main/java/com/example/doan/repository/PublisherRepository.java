package com.example.doan.repository;

import com.example.doan.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, String> {
    @Query(value = "select p.name from publisher p inner join publisher_book pb on p.publisher_id = pb.publisher_id where pb.book_id = ?1", nativeQuery = true)
    String getPublisher(Integer bookId);
}
