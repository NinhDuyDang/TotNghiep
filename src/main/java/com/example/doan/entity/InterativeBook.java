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
@Table(name = "interactive_book")
public class InterativeBook {
    @Id
    @Column(name="interactive_book_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer interactiveBookId;
    @Column(name = "book_id")
    private Integer bookId;
    @Column(name = "user_id")
    private Integer userId;
    private Integer rating;

}
