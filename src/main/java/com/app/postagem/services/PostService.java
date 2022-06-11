package com.app.postagem.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.postagem.dto.PostDTO;
import com.app.postagem.exceptions.PostNotFoundException;
import com.app.postagem.models.PostModel;
import com.app.postagem.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	// ========================== [ Find All ] ==========================
	
	public List<PostDTO> findAllPosts() {
		
		return this.postRepository.findAll()
				.stream()
				.map(post -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
	}
	
	
	public PostDTO findPostByid(Long id) throws PostNotFoundException {
		PostModel postModel = this.postRepository.findById(id)
				.orElseThrow(() -> new PostNotFoundException("Post not found."));
		
		return this.modelMapper.map(postModel, PostDTO.class);
		
	}
	
}
