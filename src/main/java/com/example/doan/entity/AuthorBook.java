package com.example.doan.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "author_book")
public class AuthorBook {
    @Id
    @Column(name = "author_id")
    private String authorId;
    @Column(name = "book_id")
    private Integer bookId;
}
