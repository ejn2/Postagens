package com.app.postagem.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.postagem.dto.PostDTO;
import com.app.postagem.dto.UserDTO;
import com.app.postagem.exceptions.PostNotFoundException;
import com.app.postagem.exceptions.UserNotFoundException;
import com.app.postagem.models.PostModel;
import com.app.postagem.models.UserModel;
import com.app.postagem.repository.PostRepository;
import com.app.postagem.repository.UserRepository;
import com.app.postagem.utils.PostUtils;
import com.app.postagem.utils.UserUtils;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

	@Mock
	PostRepository postRepository;
	
	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	PostService postService;
	
	PostModel postModel = PostUtils.newPostModel();
	PostDTO postDTO = PostUtils.newPostDTO();
	
	UserModel userModel = UserUtils.newUser();
	UserDTO userDTO = UserUtils.newDTO();
	
	
	// ========================== [Test | save ] ==========================
	
	@Test
	void whenSavePostIsCalledWithValidData_ThenAPostIsSaved() {
		when(this.postRepository.save(Mockito.any(PostModel.class)))
			.thenReturn(this.postModel);
		
		PostDTO createdPost = this.postService.savePost(this.postDTO);
		
		assertEquals(this.postDTO.getTitle(),
			createdPost.getTitle()
				);
		
		assertEquals(this.postDTO.getDescription(),
				createdPost.getDescription());
		
	}
	
	
	// ========================== [Test | find all posts ] ==========================
	
	@Test
	void whenFindAllPostsIsCalled_ThenAListOfPostsIsReturned() {
		
		List<PostModel> postModelList = Collections.singletonList(this.postModel);
		
		when(this.postRepository.findAll())
			.thenReturn(postModelList);
		
		List<PostDTO> postDtoList = this.postService.findAllPosts();
		
		assertEquals(postModelList.get(0).getTitle(),
			postDtoList.get(0).getTitle()
				);		
		
	}
	
	
	// ========================== [Test | find post by id ] ==========================
	
	@Test
	void whenFindPostByIdIsCalledWithValidId_ThenAPostIsReturned() throws PostNotFoundException {
		when(this.postRepository.findById(Mockito.anyLong()))
			.thenReturn(Optional.of(this.postModel));
		
		PostDTO postDto = this.postService.findPostByid(1L);
		
		assertEquals(this.postDTO.getTitle(),
			postDto.getTitle()
				);
	}
	
	@Test
	void whenFindPostByIdIsCalledWithInvalidId_ThenAnExceptionIsReturned() {
		
		when(this.postRepository.findById(Mockito.anyLong()))
			.thenReturn(Optional.empty());
		
		assertThrows(PostNotFoundException.class, () ->
			this.postService.findPostByid(2L)
				);
	}
	
	// ========================== [Test | Save user post] ==========================
	
	@Test
	void WhenSavePostIsCalledWithAValidUserId_ThenAPostIsSaved() throws UserNotFoundException {
		
		when(this.userRepository.findById(Mockito.anyLong()))
			.thenReturn(Optional.of(this.userModel));
		
		when(this.postRepository.save(Mockito.any(PostModel.class)))
			.thenReturn(this.postModel);
		
		PostDTO post2 = new PostDTO(2L, "POST-2_test", "post-teste");
		
		this.postService.saveUserPost(this.userDTO.getId(), post2);
		
		PostDTO createdPost = this.postService.saveUserPost(this.userDTO.getId(), this.postDTO);
		
		assertEquals(this.postDTO.getTitle(), createdPost.getTitle());
	}
	
}
