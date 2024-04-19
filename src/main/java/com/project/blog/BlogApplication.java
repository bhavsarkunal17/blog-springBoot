package com.project.blog;

import com.project.blog.config.Constants;
import com.project.blog.model.Comment;
import com.project.blog.model.Role;
import com.project.blog.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@SpringBootApplication
public class BlogApplication implements CommandLineRunner {



	private RoleRepo roleRepo;

	@Autowired
	public BlogApplication(RoleRepo roleRepo) {
		this.roleRepo = roleRepo;
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);

	}

	@Bean
	ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}


	@Override
	public void run(String... args) throws Exception {
		createRoles();
	}

	private void createRoles() {


		Role adminRole = new Role();
		adminRole.setName(Constants.ROLE_ADMIN);
		adminRole.setId(101);

		Role userRole = new Role();
		userRole.setName(Constants.ROLE_USER);
		userRole.setId(201);

		List<Role> roles = List.of(userRole,adminRole);
		List<Role> rolesList = roleRepo.saveAll(roles);

		rolesList.forEach(role -> {
			System.out.println(role.getName() );
		});
	}
}
