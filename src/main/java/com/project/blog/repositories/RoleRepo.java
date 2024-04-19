package com.project.blog.repositories;

import com.project.blog.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RoleRepo extends JpaRepository<Role,Integer> {
}
