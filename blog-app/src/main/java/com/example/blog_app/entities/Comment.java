package com.example.blog_app.entities;

import com.example.blog_app.payloads.PostDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "comments")
@Table
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;

    @ManyToOne
    private Post post;
}
