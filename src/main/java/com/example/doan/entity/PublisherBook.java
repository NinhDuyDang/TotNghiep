package com.example.doan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "publisher_book")
public class PublisherBook {
    @Id
    @Column(name = "publisher_id")
    private String publisherId;
    @Column(name = "book_id")
    private Integer bookId;
}
