package com.app.postagem.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.postagem.dto.SaveUserDTO;
import com.app.postagem.dto.UserDTO;
import com.app.postagem.exceptions.EmailIsAlreadyRegisteredException;
import com.app.postagem.exceptions.UserNotFoundException;
import com.app.postagem.services.UserService;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping
	public List<UserDTO> list() {
		return this.userService.listOfUsers();
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public UserDTO register(@RequestBody SaveUserDTO saveUserDTO) throws EmailIsAlreadyRegisteredException {
		return this.userService.saveUser(saveUserDTO);
	}
	
	@GetMapping(path = "/{id}")
	public UserDTO findUserById(@PathVariable Long id) throws UserNotFoundException {
		return this.userService.findById(id);
	}
	
}
