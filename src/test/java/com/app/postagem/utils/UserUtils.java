package com.app.postagem.utils;

import org.modelmapper.ModelMapper;

import com.app.postagem.dto.UserDTO;
import com.app.postagem.models.PostModel;
import com.app.postagem.models.UserModel;

public abstract class UserUtils {

	public static UserModel newUser() {
		
		PostModel post = new PostModel();
		post.setId(1L);
		post.setTitle("Post-1");
		post.setDescription("Post Test");
		
		UserModel user = new UserModel();
		user.setId(1L);
		user.setName("User");
		user.setLastname("user");
		user.setEmail("user@user.com");
		user.setPassword("12345678");
		user.getPosts().add(post);
		
		return user;
	}
	
	public static UserDTO newDTO() {
		ModelMapper modelMapper = new ModelMapper();
		
		return modelMapper.map(UserUtils.newUser(), UserDTO.class);
		
	}
}
