package com.example.blog_app.payloads;

import com.example.blog_app.entities.Category;
import com.example.blog_app.entities.Comment;
import com.example.blog_app.entities.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

    private int id;

    private String title;

    private String content;

    private Date addedDate;

    private String imageName;

    private CategoryDto category;

    private UserDto user;

    private Set<CommentDto> comments = new HashSet<>();

}
