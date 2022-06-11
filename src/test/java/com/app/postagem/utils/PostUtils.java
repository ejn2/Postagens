package com.app.postagem.utils;

import org.modelmapper.ModelMapper;

import com.app.postagem.dto.PostDTO;
import com.app.postagem.models.PostModel;

public abstract class PostUtils {

	public static PostModel newPostModel() {
		PostModel postModel = new PostModel();
		
		postModel.setId(1L);
		postModel.setTitle("Post-1");
		postModel.setDescription("Post- 1 description");
		
		return postModel;
	}
	
	public static PostDTO newPostDTO() {
		ModelMapper modelMapper = new ModelMapper();
		
		return modelMapper.map(PostUtils.newPostModel(), PostDTO.class);
		
	}
	
}
