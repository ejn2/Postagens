package com.app.postagem.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.app.postagem.dto.PostDTO;
import com.app.postagem.dto.SaveUserDTO;
import com.app.postagem.dto.UserDTO;
import com.app.postagem.models.PostModel;
import com.app.postagem.models.UserModel;
import com.app.postagem.repository.PostRepository;
import com.app.postagem.repository.UserRepository;
import com.app.postagem.services.UserService;
import com.app.postagem.utils.PostUtils;
import com.app.postagem.utils.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private PostRepository postRepository;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private final String API_URL = "http://localhost:8080/api/v1/user/";
	
	UserModel userModel = UserUtils.newUser();
	UserDTO userDTO = UserUtils.newDTO();
	SaveUserDTO saveDTO = UserUtils.newSaveDTO();
	PostModel postModel = PostUtils.newPostModel();
	PostDTO postDTO = PostUtils.newPostDTO();
	
	
	// =========================== [ Find all ] ===========================
	
	@Test
	void whenListIsCalled_ThenAListOfUsersShouldBeReturned() throws Exception {
		
		when(this.userService.listOfUsers())
			.thenReturn(Collections.singletonList(this.userDTO));

		this.mockMvc.perform(
				get(this.API_URL)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name", is(this.userModel.getName())))
				.andExpect(jsonPath("$[0].email", is(this.userDTO.getEmail())));
		
	}
	
	
	// =========================== [ Save user ] ===========================
	
	@Test
	void whenRegisterIsCalled_ThenAUserShouldBeCreadted() throws Exception {
		
		when(this.userService.saveUser(any(SaveUserDTO.class)))
			.thenReturn(this.userDTO);
		
		String body = this.objectMapper.writeValueAsString(this.saveDTO);
		
		this.mockMvc.perform(
				post(this.API_URL)
					.contentType(MediaType.APPLICATION_JSON)
					.content(body))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", is(this.userModel.getName())))
				.andExpect(jsonPath("$.email", is(this.userDTO.getEmail())));
		
	}
	
	// =========================== [ Find user by id ] ===========================
	
	@Test
	void whenFindUserByIdIsCalled_ThenAUserShouldBeReturned() throws Exception {
		
		when(this.userService.findById(any(Long.class)))
			.thenReturn(this.userDTO);
		
		this.mockMvc.perform(
				get(this.API_URL + this.userDTO.getId())
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(this.userModel.getName())))
				.andExpect(jsonPath("$.email", is(this.userModel.getEmail())));
		
	}
	
}
