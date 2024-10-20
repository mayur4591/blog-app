package com.example.blog_app.controllers;

import com.example.blog_app.payloads.CommentDto;
import com.example.blog_app.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    private ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto,
                                                  @PathVariable Integer postId){
        CommentDto commentDto1 = commentService.addComment(commentDto,postId);

        return new ResponseEntity<>(commentDto1, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    private void deleteComment(@PathVariable Integer commentId){
        commentService.deleteComment(commentId);
    }
}
