package com.example.blog_app.services.impl;

import com.example.blog_app.entities.Category;
import com.example.blog_app.entities.Post;
import com.example.blog_app.entities.User;
import com.example.blog_app.exceptions.ResourceNotFoundException;
import com.example.blog_app.payloads.PostDto;
import com.example.blog_app.payloads.PostResponse;
import com.example.blog_app.payloads.UserDto;
import com.example.blog_app.repository.CategoryRepo;
import com.example.blog_app.repository.PostRepo;
import com.example.blog_app.repository.UserRepo;
import com.example.blog_app.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepo postRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    UserRepo userRepo;

    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
        User user = userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","id",userId));

        Post post = new Post();

        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post savedPost = postRepo.save(post);

        return modelMapper.map(savedPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post","id",postId));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatedPost = postRepo.save(post);

        return modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post","id",postId));

        postRepo.delete(post);
    }

    @Override
    public PostDto getPostByPostId(Integer postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post","id",postId));

        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy) {
        Pageable p = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        Page<Post> pagePost = postRepo.findAll(p);

        List<Post> allPost = pagePost.getContent();

        List<PostDto> postDtos =allPost.stream()
                .map((post) -> modelMapper.map(post,PostDto.class))
                .collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        postResponse.setTotalElements(pagePost.getTotalElements());
        return postResponse;
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
        List<PostDto> postDtos = postRepo.findByCategory(category).stream()
                .map((post) -> modelMapper.map(post,PostDto.class))
                .collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        List<PostDto> postDtos = postRepo.findByUser(user).stream()
                .map((post) -> modelMapper.map(post,PostDto.class))
                .collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {

        List<Post> posts = postRepo.findByTitleContaining(keyword);

        List<PostDto> postDtos = posts.stream()
                .map((post)->modelMapper.map(post,PostDto.class))
                .collect(Collectors.toList());

        return postDtos;
    }
}
