package com.app.postagem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.postagem.models.PostModel;

public interface PostRepository extends JpaRepository<PostModel, Long>{
	
}
