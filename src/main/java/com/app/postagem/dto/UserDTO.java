package com.app.postagem.dto;

import java.util.ArrayList;
import java.util.List;

import com.app.postagem.models.PostModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class UserDTO {
	
	private Long id;
	private String name;
	private String lastname;
	private String email;
	private List<PostModel> posts = new ArrayList<>();
	
}
