package com.app.postagem.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.postagem.dto.UserDTO;
import com.app.postagem.exceptions.EmailIsAlreadyRegisteredException;
import com.app.postagem.models.UserModel;
import com.app.postagem.repository.UserRepository;
import com.app.postagem.utils.UserUtils;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	UserService userService;
	
	UserModel userModel = UserUtils.newUser();
	UserDTO userDTO = UserUtils.newDTO();
	
	
	// ========================== [Test | Save ] ==========================
	
	@Test
	void whenSaveUserIsCalledWithValidEmail_ThenAUserShouldBeCreated() throws EmailIsAlreadyRegisteredException {
		
		when(this.userRepository.findByEmail(this.userModel.getEmail()))
		.thenReturn(null);
		
		when(this.userRepository.save(Mockito.any(UserModel.class)))
			.thenReturn(this.userModel);
		
		UserDTO createdUser = this.userService.saveUser(userDTO); 
		
		assertEquals(this.userDTO.getName(), createdUser.getName());
		assertEquals(this.userDTO.getEmail(), createdUser.getEmail());
	}
	
	
	@Test
	void whenSaveUserIsCalledWithEmailAlreadyRegistered_ThenAnExceptionShouldBeThrown() throws EmailIsAlreadyRegisteredException {
		
		when(this.userRepository.findByEmail(this.userModel.getEmail()))
			.thenReturn(userModel);
	
		assertThrows(EmailIsAlreadyRegisteredException.class, () -> 
				this.userService.saveUser(userDTO)
			);
		
	}
	
}
