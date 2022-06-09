package com.app.postagem.services;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.postagem.dto.UserDTO;
import com.app.postagem.exceptions.EmailIsAlreadyRegisteredException;
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
	
	@Transactional
	public UserDTO saveUser(UserDTO userDTO) throws EmailIsAlreadyRegisteredException {
		this.verifyIfEmailIsAlreadyRegistered(userDTO.getEmail());
		
		UserModel user = this.userRepository.save(
				this.modelMapper.map(userDTO, UserModel.class)
				);
		
		return this.modelMapper.map(user, UserDTO.class);
	}
	
}
