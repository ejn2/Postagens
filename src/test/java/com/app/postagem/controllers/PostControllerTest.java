package com.app.postagem.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
import com.app.postagem.services.PostService;
import com.app.postagem.utils.PostUtils;
import com.app.postagem.utils.UserUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = PostController.class)
public class PostControllerTest {
	
	@MockBean
	private PostRepository postRepository;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private PostService postService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	private final String API_URL = "http://localhost:8080/api/v1/post/";
	
	UserModel userModel = UserUtils.newUser();
	UserDTO userDTO = UserUtils.newDTO();
	SaveUserDTO saveDTO = UserUtils.newSaveDTO();
	PostModel postModel = PostUtils.newPostModel();
	PostDTO postDTO = PostUtils.newPostDTO();
	
	
	// =========================== [ Find all ] ===========================
	
	@Test
	void whenListIsCalled_ThenAlistOfPostsShouldBeReturned() throws Exception {
		
		when(this.postService.findAllPosts())
			.thenReturn(Collections.singletonList(this.postDTO));
		
		this.mockMvc.perform(
				get(this.API_URL)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title", is(this.postDTO.getTitle())))
				.andExpect(jsonPath("$[0].description", is(this.postDTO.getDescription())));
	}
	
	// =========================== [ Find by id ] ===========================
	
	@Test
	void whenFindByidIsCalled_ThenAPostShouldBeReturned() throws Exception {
		
		when(this.postService.findPostByid(any(Long.class)))
			.thenReturn(this.postDTO);
			
		
		this.mockMvc.perform(
				get(this.API_URL + this.postDTO.getId())
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is(this.postDTO.getTitle())))
				.andExpect(jsonPath("$.description", is(this.postDTO.getDescription())));
			
	}
	
	// =========================== [ Save post ] ===========================
	
	@Test
	void whenSaveUserPostIsCalled_ThenAPostShouldBeCreated() throws Exception {
		
		when(this.postService.saveUserPost(any(Long.class), any(PostDTO.class)))
			.thenReturn(this.postDTO);
		
		String body = this.objectMapper.writeValueAsString(this.postDTO);
		
		this.mockMvc.perform(
				post(this.API_URL + this.userDTO.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(body))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.title", is(this.postDTO.getTitle())))
				.andExpect(jsonPath("$.description", is(this.postDTO.getDescription())));
		
	}
	
}
