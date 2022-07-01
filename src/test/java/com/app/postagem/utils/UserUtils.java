package com.app.postagem.utils;

import org.modelmapper.ModelMapper;

import com.app.postagem.dto.SaveUserDTO;
import com.app.postagem.dto.UserDTO;
import com.app.postagem.models.PostModel;
import com.app.postagem.models.UserModel;

public abstract class UserUtils {

	public static UserModel newUser() {
		
		PostModel post = PostUtils.newPostModel();
		
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
	
	public static SaveUserDTO newSaveDTO() {
		
		SaveUserDTO user = new SaveUserDTO();
		user.setName("User");
		user.setLastname("user");
		user.setEmail("user@user.com");
		user.setPassword("12345678");
		
		return user;
	}
}
