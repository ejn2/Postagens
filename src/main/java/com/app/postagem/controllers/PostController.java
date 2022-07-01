package com.app.postagem.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.postagem.dto.PostDTO;
import com.app.postagem.exceptions.PostNotFoundException;
import com.app.postagem.exceptions.UserNotFoundException;
import com.app.postagem.services.PostService;

@RestController
@RequestMapping(path = "/api/v1/post")
public class PostController {
	
	@Autowired
	PostService postService;

	@GetMapping
	public List<PostDTO> list() {
		return this.postService.findAllPosts();
	}
	
	@GetMapping(path = "/{id}")
	public PostDTO findById(@PathVariable Long id) throws PostNotFoundException {
		return this.postService.findPostByid(id);
	}
	
	@PostMapping(path="/{id}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PostDTO saveUserPost(@PathVariable Long id, @RequestBody PostDTO postDTO) throws UserNotFoundException {
		return this.postService.saveUserPost(id, postDTO);
	}
	
}
