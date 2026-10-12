package com.example.demo.repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.CategoriesEntity;

@Repository
public interface CategoriesRepositry extends JpaRepository<CategoriesEntity,Integer>{

	Optional<CategoriesEntity> findByName(String name);
	
}
