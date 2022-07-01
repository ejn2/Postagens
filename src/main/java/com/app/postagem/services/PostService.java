package com.app.postagem.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.postagem.dto.PostDTO;
import com.app.postagem.exceptions.PostNotFoundException;
import com.app.postagem.exceptions.UserNotFoundException;
import com.app.postagem.models.PostModel;
import com.app.postagem.models.UserModel;
import com.app.postagem.repository.PostRepository;
import com.app.postagem.repository.UserRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	
	// ========================== [ Save ] ==========================
	
	public PostDTO savePost(PostDTO postDTO) {
		
		PostModel post = this.postRepository.save(
			this.modelMapper.map(postDTO, PostModel.class)
				);
		
		return this.modelMapper.map(post, PostDTO.class);
	}
	
	// ========================== [ Find All ] ==========================
	
	public List<PostDTO> findAllPosts() {
		
		return this.postRepository.findAll()
				.stream()
				.map(post -> this.modelMapper.map(post, PostDTO.class))
				.collect(Collectors.toList());
	}
	
	
	// ========================== [ Find by id ] ==========================
	
	public PostDTO findPostByid(Long id) throws PostNotFoundException {
		PostModel postModel = this.postRepository.findById(id)
				.orElseThrow(() -> new PostNotFoundException("Post not found."));
		
		return this.modelMapper.map(postModel, PostDTO.class);
		
	}	
	// ========================== [ Save user post ] ==========================
	
	public PostDTO saveUserPost(Long userId, PostDTO postDTO) throws UserNotFoundException {
		
		UserModel user = this.userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not Found"));
		
		PostModel createdPost = this.postRepository.save(
			this.modelMapper.map(postDTO, PostModel.class)
				);
		
		user.getPosts().add(createdPost);
		
		this.userRepository.save(
			this.modelMapper.map(user, UserModel.class)
				 );
		  
		  return this.modelMapper.map(createdPost, PostDTO.class);
	}
	
}
