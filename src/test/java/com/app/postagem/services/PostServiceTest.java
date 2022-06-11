package com.app.postagem.services;

import static org.junit.jupiter.api.Assertions.*;
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
import com.app.postagem.exceptions.PostNotFoundException;
import com.app.postagem.models.PostModel;
import com.app.postagem.repository.PostRepository;
import com.app.postagem.utils.PostUtils;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

	@Mock
	PostRepository postRepository;
	
	@InjectMocks
	PostService postService;
	
	PostModel postModel = PostUtils.newPostModel();
	PostDTO postDTO = PostUtils.newPostDTO();
	
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
	
}
