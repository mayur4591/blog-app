package com.example.blog_app.services.impl;

import com.example.blog_app.entities.Comment;
import com.example.blog_app.entities.Post;
import com.example.blog_app.exceptions.ResourceNotFoundException;
import com.example.blog_app.payloads.CommentDto;
import com.example.blog_app.payloads.PostDto;
import com.example.blog_app.repository.CommentRepo;
import com.example.blog_app.repository.PostRepo;
import com.example.blog_app.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CommentDto addComment(CommentDto commentDto, Integer postId) {

        Post post = postRepo.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post","id",postId));

        Comment comment = modelMapper.map(commentDto,Comment.class);
        comment.setPost(post);
        Comment comment1 = commentRepo.save(comment);

        return modelMapper.map(comment1,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
    Comment comment =commentRepo.findById(commentId)
            .orElseThrow(()->new ResourceNotFoundException("Comment","id",commentId));
     commentRepo.delete(comment);
    }
}
