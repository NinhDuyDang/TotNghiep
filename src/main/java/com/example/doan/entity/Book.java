package com.example.doan.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "book_id")
    private Integer bookId;
    @Column(name = "best_book_id")
    private Integer bestBookId;
    @Column(name = "work_id")
    private Integer workId;
    @Column(name = "image_url")
    private String imageUrl;
    private String authors;
    private String title;
    @Column(name = "original_publication_year")
    private String originalPublicationYear;
    @Column(name = "average_rating")
    private Float averageRating;
}
