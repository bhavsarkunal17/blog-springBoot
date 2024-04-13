package com.project.blog.repositories;

import com.project.blog.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepo extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String email);

}
