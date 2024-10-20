package com.example.blog_app.services;

import com.example.blog_app.entities.Post;
import com.example.blog_app.payloads.PostDto;
import com.example.blog_app.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
    PostDto updatePost(PostDto postDto,Integer postId);
    void deletePost(Integer postId);
    PostDto getPostByPostId(Integer postId);
    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy);
    List<PostDto> getPostsByCategory(Integer categoryId);
    List<PostDto> getPostsByUser(Integer userId);
    List<PostDto> searchPost(String keyword);

}
