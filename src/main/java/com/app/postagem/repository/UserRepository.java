package com.app.postagem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.postagem.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long>{
	
}
