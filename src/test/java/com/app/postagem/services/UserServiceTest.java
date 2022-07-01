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
import org.modelmapper.ModelMapper;

import com.app.postagem.dto.PostDTO;
import com.app.postagem.dto.SaveUserDTO;
import com.app.postagem.dto.UserDTO;
import com.app.postagem.exceptions.EmailIsAlreadyRegisteredException;
import com.app.postagem.exceptions.UserNotFoundException;
import com.app.postagem.models.PostModel;
import com.app.postagem.models.UserModel;
import com.app.postagem.repository.PostRepository;
import com.app.postagem.repository.UserRepository;
import com.app.postagem.utils.PostUtils;
import com.app.postagem.utils.UserUtils;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	UserRepository userRepository;
	
	@Mock
	PostRepository postRepository;
	
	@InjectMocks
	UserService userService;
	
	UserModel userModel = UserUtils.newUser();
	UserDTO userDTO = UserUtils.newDTO();
	SaveUserDTO saveDTO = UserUtils.newSaveDTO();
	PostModel postModel = PostUtils.newPostModel();
	PostDTO postDTO = PostUtils.newPostDTO();
	
	ModelMapper modelMapper = new ModelMapper();
	
	// ========================== [Test | Save ] ==========================
	
	@Test
	void whenSaveUserIsCalledWithValidEmail_ThenAUserShouldBeCreated() throws EmailIsAlreadyRegisteredException {
		
		when(this.userRepository.findByEmail(this.userModel.getEmail()))
		.thenReturn(null);
		
		when(this.userRepository.save(Mockito.any(UserModel.class)))
			.thenReturn(this.userModel);
		
		UserDTO createdUser = this.userService.saveUser(this.saveDTO);
		
		assertEquals(this.userDTO.getName(), createdUser.getName());
		assertEquals(this.userDTO.getEmail(), createdUser.getEmail());
	}
	
	
	@Test
	void whenSaveUserIsCalledWithEmailAlreadyRegistered_ThenAnExceptionShouldBeThrown() throws EmailIsAlreadyRegisteredException {
		
		when(this.userRepository.findByEmail(this.userModel.getEmail()))
			.thenReturn(userModel);
	
		assertThrows(EmailIsAlreadyRegisteredException.class, () -> 
				this.userService.saveUser(this.saveDTO)
			);
		
	}
	
	
	// ========================== [Test | find all ] ==========================
	
	@Test
	void whenListOfUsersIsCalled_ThenAListOfUserIsReturned() {
		
		List<UserModel> modelList = Collections.singletonList(this.userModel);
		
		when(this.userRepository.findAll())
			.thenReturn(modelList);
		
		List<UserDTO> dtoList = this.userService.listOfUsers();
		
		assertEquals(modelList.get(0).getName(),
				dtoList.get(0).getName());
	}
	
	// ========================== [Test | find by id ] ==========================
	
	@Test
	void whenFindByIdIsCalledWithValidId_ThenAUserIsReturned() throws UserNotFoundException {
		
		when(this.userRepository.findById(Mockito.any(Long.class)))
			.thenReturn(Optional.of(this.userModel));
		
		UserDTO foundUser = this.userService.findById(this.userDTO.getId());
		
		assertEquals(this.userDTO.getName(), foundUser.getName());
		
	}
	

	@Test
	void whenFindByIdIsCalledWithInvalidId_ThenAnExceptionIsThrown() throws UserNotFoundException {
		when(this.userRepository.findById(Mockito.any(Long.class)))
			.thenReturn(Optional.empty());
		
		assertThrows(UserNotFoundException.class, () ->
			this.userService.findById(1L)
				);
		
	}
	
}
