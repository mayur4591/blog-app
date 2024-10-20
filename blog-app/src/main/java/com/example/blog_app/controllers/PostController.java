package com.example.blog_app.controllers;

import com.example.blog_app.config.AppConstants;
import com.example.blog_app.payloads.PostDto;
import com.example.blog_app.payloads.PostResponse;
import com.example.blog_app.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/users/{userId}/category/{categoryId}/post/")
    private ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                               @PathVariable Integer userId,
                                               @PathVariable Integer categoryId){
        PostDto createdPost = postService.createPost(postDto,userId,categoryId);

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}")
    private ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto postDto = postService.getPostByPostId(postId);
        return ResponseEntity.ok(postDto);
    }
    @GetMapping("/posts/")
    private ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy
    ){
       PostResponse postResponse= postService.getAllPost(pageNumber,pageSize,sortBy);

       return ResponseEntity.ok(postResponse);
    }
    @GetMapping("/category/{categoryId}/posts")
    private ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
        List<PostDto> response = postService.getPostsByCategory(categoryId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}/posts")
    private ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
        List<PostDto> response = postService.getPostsByUser(userId);

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/posts/{postId}")
    private void deletePostById(@PathVariable Integer postId){
        postService.deletePost(postId);
    }

    @PutMapping("/posts/{postId}")
    private ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
                                               @PathVariable Integer postId){
        PostDto postDto1 = postService.updatePost(postDto,postId);

        return ResponseEntity.ok(postDto1);
    }

    @GetMapping("/posts/search/{keyword}")
    private ResponseEntity<List<PostDto>> searchByKeyWord(@PathVariable String keyword){
        List<PostDto> postDtos = postService.searchPost(keyword);

        return ResponseEntity.ok(postDtos);
    }
}
