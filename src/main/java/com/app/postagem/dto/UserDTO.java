package com.app.postagem.dto;

import java.util.ArrayList;
import java.util.List;

import com.app.postagem.models.PostModel;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {
	
	private Long id;
	private String name;
	private String lastname;
	private String email;
	private String password;
	private List<PostModel> posts = new ArrayList<>();
	
}
