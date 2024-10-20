package com.example.blog_app.services;

import com.example.blog_app.payloads.CommentDto;

public interface CommentService {
    CommentDto addComment(CommentDto commentDto,Integer postId);
    void deleteComment(Integer commentId);
}
