package com.example.demo.repositry;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.RoleEntity;
import java.util.List;
import com.example.demo.constants.UserRoleEnum;


@Repository
public interface RoleRepositry extends JpaRepository<RoleEntity, Integer>{

	Optional<RoleEntity> findByRoleName(UserRoleEnum roleName);
}
