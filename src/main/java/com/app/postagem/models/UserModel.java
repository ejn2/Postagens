package com.app.postagem.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "User")
@Getter @Setter
public class UserModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 60)
	private String name;
	
	@Column(nullable = false, length = 60)
	private String lastname;
	
	@Column(nullable = false, unique = true, length = 200)
	private String email;
	
	@Column(nullable = false, length = 60)
	private String password;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="user")
	private List<PostModel> posts = new ArrayList<>();
	
}