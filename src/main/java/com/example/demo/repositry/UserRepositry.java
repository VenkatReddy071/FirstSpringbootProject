package com.example.demo.repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.UsersEntity;

@Repository
public interface UserRepositry extends JpaRepository<UsersEntity, Integer>{

	@EntityGraph(attributePaths = {"userRole", "organization"})
	Optional<UsersEntity>findByEmail(String email);

}
