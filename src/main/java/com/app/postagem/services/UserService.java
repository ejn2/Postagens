package com.app.postagem.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.postagem.dto.UserDTO;
import com.app.postagem.exceptions.EmailIsAlreadyRegisteredException;
import com.app.postagem.exceptions.UserNotFoundException;
import com.app.postagem.models.UserModel;
import com.app.postagem.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	ModelMapper modelMapper = new ModelMapper();
	
	private void verifyIfEmailIsAlreadyRegistered(String email) throws EmailIsAlreadyRegisteredException {
		
		if(this.userRepository.findByEmail(email) != null) {
			throw new EmailIsAlreadyRegisteredException("Email address is already registered.");
		}

	}
	
	// ========================== [ Save ] ==========================
	
	@Transactional
	public UserDTO saveUser(UserDTO userDTO) throws EmailIsAlreadyRegisteredException {
		this.verifyIfEmailIsAlreadyRegistered(userDTO.getEmail());
		
		UserModel user = this.userRepository.save(
				this.modelMapper.map(userDTO, UserModel.class)
				);
		
		return this.modelMapper.map(user, UserDTO.class);
	}
	
	
	// ========================== [ Find All ] ==========================
	
	public List<UserDTO> listOfUsers() {
		
		return this.userRepository.findAll()
			.stream()
		 	.map(user -> this.modelMapper.map(user, UserDTO.class))
		 	.collect(Collectors.toList());
		
	}
	
	// ========================== [ Find by id ] ==========================
	
	public UserDTO findById(Long id) throws UserNotFoundException {
		
		UserModel user = this.userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not Found with id '"+ id+"'"));
		
		return this.modelMapper.map(user, UserDTO.class);
		
	}
	
}
