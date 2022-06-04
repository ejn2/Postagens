package com.app.postagem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.app.postagem.models.PostModel;
import com.app.postagem.models.UserModel;
import com.app.postagem.repository.PostRepository;
import com.app.postagem.repository.UserRepository;

@SpringBootApplication
public class PostagemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostagemApplication.class, args);
	}
	
	@Bean
	CommandLineRunner run(UserRepository userRepository, PostRepository postRepository) {
		return args -> {
			
			PostModel post = new PostModel();
			post.setTitle("Post admin 1");
			post.setDescription("Teste post");
			
			PostModel post2 = new PostModel();
			post2.setTitle("Post admin 2");
			post2.setDescription("Teste post 2");
			
			UserModel user = new UserModel();
			user.setName("Admin");
			user.setLastname("admin");
			user.setEmail("admin@admin.com");
			user.setPassword("admin123");
			
			UserModel createdUser = userRepository.save(user);
			
			if(createdUser != null) {
				PostModel savedPost = postRepository.save(post);
				PostModel savedPost2 = postRepository.save(post2);
				
				createdUser.getPosts().add(savedPost);
				createdUser.getPosts().add(savedPost2);
				
				userRepository.save(createdUser);
			}
			
		};
	}

}
