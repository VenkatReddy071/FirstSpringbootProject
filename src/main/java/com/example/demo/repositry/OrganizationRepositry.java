package com.example.demo.repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.OrganizationEntity;



@Repository
public interface OrganizationRepositry extends JpaRepository<OrganizationEntity, Integer>{

	Optional<OrganizationEntity>findByEmail(String email);
}
